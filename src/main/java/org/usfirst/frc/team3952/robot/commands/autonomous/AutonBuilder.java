package org.usfirst.frc.team3952.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.*;
import org.usfirst.frc.team3952.robot.devices.Path;
import org.usfirst.frc.team3952.robot.subsystems.*;

import java.util.Optional;
import java.util.Stack;

import static org.usfirst.frc.team3952.robot.RobotMap.AUTONOMOUS_SCRIPT;
import static org.usfirst.frc.team3952.robot.devices.Path.PathStatus.*;

/**
 * A way to read path directions from a file, it should simplify operations on a fake autonomous without encoders.
 */
public class AutonBuilder extends CommandBase {
    private RobotSubsystems subsystems;
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
            try {
                // Wait for it, so we spin on a bool (shouldn't take that long)
                while(list.getStatus() == Unloaded || list.getStatus() == Loading) {}

                if(list.getStatus() != Invalid) {
                    System.out.println("ERROR: AutonBuilder was incapable of converting a Path to a command script!");
                    end = true;
                    return;
                } else if(list.getStatus() != Ready)
                    return;

                for(String line : list.getInstructions()) {
                    String[] cmd = line.split(" ");
                    Command c = null;

                    if (cmd[0].equalsIgnoreCase("MOVE") && cmd.length == 6)
                        c = drive(cmd);
                    else if (cmd[0].equalsIgnoreCase("LIFT") && cmd.length == 2)
                        c = lift(cmd);
                    else if (cmd[0].equalsIgnoreCase("TILT") && cmd.length == 2)
                        end = true; //TODO
                    else if (cmd[0].equalsIgnoreCase("TURN") && cmd.length == 3)
                        end = true; //TODO
                    else if (cmd[0].equalsIgnoreCase("AUTOALIGN") && cmd.length == 1)
                        c = new AutoAlign(subsystems);
                    else if (cmd[0].equalsIgnoreCase("PARALLEL") && cmd.length == 2)
                        c = handleParallel(cmd);
                    else if (cmd[0].equalsIgnoreCase("DELAY") && cmd.length == 2)
                        end = true;
                    else if (cmd[0].equalsIgnoreCase("END") && cmd.length == 1)
                        return;
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
            return new EjectBall(subsystems);
        else if(cmd[1].equalsIgnoreCase("BOTTOM"))
            return new IntakeBall(subsystems);
        else
        {
            System.out.println("Error: invalid LIFT command! Must be in the form of \"LIFT [TOP/BOTTOM]\".");
            return null;
        }
    }

    private ParallelCommandGroup handleParallel(String[] cmd)
    {
        if(cmd[1].equalsIgnoreCase("START")) {
            parallelCommands.push(new ParallelCommandGroup());
            return null;
        }
        else if(cmd[1].equalsIgnoreCase("END"))
        {
            if(parallelCommands.size() == 0)
            {
                System.out.println("Error: Unmatched open parallel starts to ends!");
            }
            return parallelCommands.pop();
        }
        else
        {
            System.out.println("Error: invalid LIFT command! Must be in the form of \"LIFT [TOP/BOTTOM]\".");
            return null;
        }
    }

    public Optional<Double> tryParseDouble(String input)
    {
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
    }
}