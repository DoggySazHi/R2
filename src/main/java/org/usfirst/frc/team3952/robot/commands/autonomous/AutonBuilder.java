package org.usfirst.frc.team3952.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.*;
import org.usfirst.frc.team3952.robot.commands.GoToNextPosition;
import org.usfirst.frc.team3952.robot.devices.Path;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import java.util.Optional;
import java.util.Stack;

import static org.usfirst.frc.team3952.robot.RobotMap.AUTONOMOUS_SCRIPT;
import static org.usfirst.frc.team3952.robot.devices.Path.PathStatus.*;

/**
 * A way to read path directions from a file, it should simplify operations on a fake autonomous without encoders.
 */
public class AutonBuilder extends CommandBase {
    private final RobotSubsystems subsystems;
    private Path list;
    private boolean end;
    private SequentialCommandGroup commandGroup;
    private Stack<ParallelCommandGroup> parallelCommands;

    public AutonBuilder(RobotSubsystems subsystems) {
        this.subsystems = subsystems;
    }

    @Override
    public void initialize() {
        list = new Path(AUTONOMOUS_SCRIPT);
        commandGroup = new SequentialCommandGroup();
        parallelCommands = new Stack<>();

        new Thread(() -> {
            System.out.println("Starting builder...");
            try {
                // Wait for it, so we spin on a bool (shouldn't take that long)
                while (list.getStatus() == Unloaded || list.getStatus() == Loading) {
                    System.out.println("Waiting...");
                }
                System.out.println("Loaded!");

                if (list.getStatus() != Ready) {
                    System.out.println("ERROR: AutonBuilder was incapable of converting a Path to a command script!");
                    end = true;
                    return;
                }
                System.out.println("Found instructions.");

                for (String line : list.getInstructions()) {
                    System.out.println("Now loading \"" + line + "\"");
                    String[] cmd = line.split(" ");
                    Command c = null;

                    if (cmd[0].equalsIgnoreCase("MOVE") && cmd.length == 6)
                        c = drive(cmd);
                    else if (cmd[0].equalsIgnoreCase("LIFT") && cmd.length == 2)
                        c = lift(cmd);
                    else if (cmd[0].equalsIgnoreCase("TILT") && (cmd.length == 2 || cmd.length == 3))
                        c = tilt(cmd);
                    else if (cmd[0].equalsIgnoreCase("TURN") && cmd.length == 3)
                        c = turn(cmd);
                    else if (cmd[0].equalsIgnoreCase("SHOOT") && cmd.length == 4)
                        c = shoot(cmd);
                    else if (cmd[0].equalsIgnoreCase("NEXT") && cmd.length == 1)
                        c = new GoToNextPosition(subsystems);
                    else if (cmd[0].equalsIgnoreCase("INTAKE") && cmd.length == 2)
                        c = intake(cmd);
                    else if (cmd[0].equalsIgnoreCase("CLIMBER") && cmd.length == 3)
                        c = climber(cmd);
                    else if (cmd[0].equalsIgnoreCase("AUTOALIGN") && cmd.length == 1)
                        c = new AutoAlign(subsystems);
                    else if (cmd[0].equalsIgnoreCase("PARALLEL") && cmd.length == 2)
                        c = handleParallel(cmd);
                    else if (cmd[0].equalsIgnoreCase("DELAY") && cmd.length == 2)
                        c = delay(cmd);
                    else if (cmd[0].equalsIgnoreCase("PRINT") && cmd.length >= 2)
                        c = print(cmd);
                    else if (cmd[0].equalsIgnoreCase("END") && cmd.length == 1)
                        break;
                    if (c != null) {
                        if (parallelCommands.size() != 0)
                            parallelCommands.peek().addCommands(c);
                        else
                            commandGroup.addCommands(c);
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                end = true;
            }
            System.out.println("Finished building!");
        }).start();
    }

    @Override
    public void execute() {

    }

    private Command drive(String[] cmd) {
        var time = tryParseLong(cmd[1]);
        var x = tryParseDouble(cmd[2]);
        var y = tryParseDouble(cmd[3]);
        var z = tryParseDouble(cmd[4]);
        var quickTurn = tryParseDouble(cmd[5]);

        if (time.isEmpty() || x.isEmpty() || y.isEmpty() || z.isEmpty() || quickTurn.isEmpty()) {
            System.out.println("Error: invalid MOVE command! Must be in the form of \"MOVE [-1.0, 1.0] [-1.0, 1.0] [-1.0, 1.0] [0/1]\".");
            return null;
        }

        var timeNum = time.get();
        var xNum = x.get();
        var yNum = y.get();
        var zNum = z.get();
        var quickTurnBool = quickTurn.get() == 1;

        return new DriveTime(subsystems, timeNum, xNum, yNum, zNum, quickTurnBool);
    }

    private Command lift(String[] cmd)
    {
        if(cmd[1].equalsIgnoreCase("TOP"))
            return new TiltShooterUp(subsystems);
        else if(cmd[1].equalsIgnoreCase("BOTTOM"))
            return new TiltShooterDown(subsystems);
        else
        {
            if(cmd.length == 3) {
                var time = tryParseLong(cmd[1]);
                var speed = tryParseDouble(cmd[2]);
                if(time.isPresent() && speed.isPresent())
                    return new TiltShooterVerticalTimed(subsystems, time.get(), speed.get());
            }
            System.out.println("Error: invalid LIFT command! Must be in the form of \"LIFT [TOP/BOTTOM]\".");
            return null;
        }
    }

    private Command handleParallel(String[] cmd) {
        if (cmd[1].equalsIgnoreCase("START")) {
            parallelCommands.push(new ParallelCommandGroup());
            return null;
        } else if (cmd[1].equalsIgnoreCase("END")) {
            if (parallelCommands.size() == 0) {
                System.out.println("Error: Unmatched open parallel starts to ends!");
            }
            return parallelCommands.pop();
        } else {
            System.out.println("Error: invalid LIFT command! Must be in the form of \"LIFT [TOP/BOTTOM]\".");
            return null;
        }
    }

    private Command tilt(String[] cmd) {
        var position = tryParseDouble(cmd[1]);

        if (position.isPresent()) {
            var positionNum = position.get();
            return new TiltShooterHorizontal(subsystems, positionNum);
        }
        else {
            System.out.println("Error: invalid TILT command! Must be in the form of \"TILT [-1.0 - 1.0]\".");
            return null;
        }
    }

    private Command shoot(String[] cmd) {
        var timePrime = tryParseLong(cmd[1]);
        var timeShoot = tryParseLong(cmd[2]);
        var disableStop = tryParseLong(cmd[3]);

        if (timePrime.isPresent() && timeShoot.isPresent() && disableStop.isPresent()) {
            var timePrimeNum = timePrime.get();
            var timeShootNum = timeShoot.get();
            var disableStopBool = disableStop.get() == 1;
            return new TimedShoot(subsystems, timePrimeNum, timeShootNum, disableStopBool);
        }
        else {
            System.out.println("Error: invalid SHOOT command! Must be in the form of \"SHOOT [0-???] [0-???] [0/1]\".");
            return null;
        }
    }

    private Command intake(String[] cmd) {
        var time = tryParseLong(cmd[1]);

        if (time.isPresent()) {
            var timeNum = time.get();
            return new TimedIntake(subsystems, timeNum);
        }
        else {
            System.out.println("Error: invalid SHOOT command! Must be in the form of \"INTAKE [0-???]\".");
            return null;
        }
    }

    private Command turn(String[] cmd) {
        var angle = tryParseDouble(cmd[1]);
        var speed = tryParseDouble(cmd[2]);
        if (angle.isPresent() && speed.isPresent()) {
            return new TurnAngle(subsystems, angle.get(), speed.get());
        } else {
            System.out.println("Error: invalid TURN command! Must be in the form of \"TURN [DEG] [-1.0 - 1.0]\".");
            return null;
        }
    }

    private Command climber(String[] cmd) {
        var time = tryParseLong(cmd[1]);
        var speed = tryParseDouble(cmd[2]);
        if (time.isPresent() && speed.isPresent()) {
            return new TimedClimber(subsystems, time.get(), speed.get());
        } else {
            System.out.println("Error: invalid CLIMBER command! Must be in the form of \"CLIMBER [TIME] [-1.0 - 1.0]\".");
            return null;
        }
    }

    private Command delay(String[] cmd) {
        var delay = tryParseLong(cmd[1]);
        if (delay.isPresent()) {
            return new Delay(subsystems, delay.get());
        } else {
            System.out.println("Error: invalid DELAY command! Must be in the form of \"DELAY [0-???]\".");
            return null;
        }
    }

    private Command print(String[] cmd) {
        StringBuilder sc = new StringBuilder();
        for (int i = 1; i < cmd.length; i++) {
            sc.append(cmd[i]);
            sc.append(" ");
        }
        return new Logger(subsystems, sc.toString());
    }

    public Optional<Double> tryParseDouble(String input) {
        try {
            return Optional.of(Double.parseDouble(input));
        } catch (NumberFormatException ignore) {
            return Optional.empty();
        }
    }

    public Optional<Long> tryParseLong(String input)
    {
        try {
            return Optional.of(Long.parseLong(input));
        } catch (NumberFormatException ignore) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isFinished() {
        return end;
    }

    @Override
    public void end(boolean interrupted) {
        if(parallelCommands.size() != 0)
        {
            System.out.println("Warning: Some parallels were not closed!");
            while(parallelCommands.size() != 0)
                commandGroup.addCommands(parallelCommands.pop());
        }
        CommandScheduler.getInstance().schedule(commandGroup);
        System.out.println("Installed commands.");
    }
}