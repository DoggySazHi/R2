package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.devices.Path;
import org.usfirst.frc.team3952.robot.subsystems.*;

import java.util.Optional;

import static org.usfirst.frc.team3952.robot.RobotMap.AUTON_SCRIPT;

/**
 * A way to read path directions from a file, it should simplify operations on a fake autonomous without encoders.
 */
public class PseudoAutonomous extends CommandBase {
    private RobotSubsystems subsystems;
    private Path list;
    private boolean ready;
    private boolean end;

    public PseudoAutonomous(RobotSubsystems subsystems) {
        this.subsystems = subsystems;

        IntakeShooter shooter = subsystems.getIntakeShooter();
        DriveTrain driveTrain = subsystems.getDriveTrain();
        Climber climber = subsystems.getClimber();
        ControlWheel controlWheel = subsystems.getControlWheel();

        addRequirements(shooter, driveTrain, climber, controlWheel);
    }

    @Override
    public void initialize() {
        list = new Path(AUTON_SCRIPT);
    }

    @Override
    public void execute() {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        Climber climber = subsystems.getClimber();
        ControlWheel controlWheel = subsystems.getControlWheel();

        if(list.getStatus() != Path.PathStatus.Ready) return;
        else if(list.getStatus() == Path.PathStatus.Ready && !ready)
        {
            list.start();
            ready = true;
        }

        String[] cmd = list.getCurrentInstruction().split(" ");

        if(cmd[0].equalsIgnoreCase("MOVE") && cmd.length == 5)
            drive(cmd);
        else if(cmd[0].equalsIgnoreCase("LIFT") && cmd.length == 5)
            drive(cmd);
        else if(cmd[0].equalsIgnoreCase("END"))
            end = true;
    }

    @Override
    public void end(boolean interrupted) {
        DriveTrain driveTrain = subsystems.getDriveTrain();
        IntakeShooter shooter = subsystems.getIntakeShooter();
        Climber climber = subsystems.getClimber();
        ControlWheel controlWheel = subsystems.getControlWheel();

        shooter.stop();
        driveTrain.stop();
        climber.stop();
        controlWheel.stop();
    }

    private void drive(String[] cmd) {
        DriveTrain driveTrain = subsystems.getDriveTrain();

        var x = tryParseDouble(cmd[1]);
        var y = tryParseDouble(cmd[2]);
        var z = tryParseDouble(cmd[3]);
        var quickTurn = tryParseDouble(cmd[4]);

        if (x.isEmpty() || y.isEmpty() || z.isEmpty() || quickTurn.isEmpty()) {
            System.out.println("Error: invalid MOVE command! Must be in the form of \"MOVE [-1.0, 1.0] [-1.0, 1.0] [-1.0, 1.0] [0/1]\".");
            return;
        }

        var xNum = x.get();
        var yNum = y.get();
        var zNum = z.get();
        var quickTurnBool = quickTurn.get() == 1;

        driveTrain.drive(xNum, yNum, zNum, quickTurnBool);
    }

    public Optional<Double> tryParseDouble(String input) {
        try {
            return Optional.of(Double.parseDouble(input));
        } catch (NumberFormatException ignore) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isFinished() {
        return end;
    }
}