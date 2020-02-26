package org.usfirst.frc.team3952.robot.commands;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.devices.Path;
import org.usfirst.frc.team3952.robot.subsystems.*;

import static org.usfirst.frc.team3952.robot.RobotMap.AUTON_SCRIPT;

/**
 * A way to read path directions from a file, it should simplify operations on a fake autonomous without encoders.
 */
public class PseudoAutonomous extends CommandBase {
    private RobotSubsystems subsystems;
    private Path list;

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
        if(list.getStatus() != Path.PathStatus.Ready) return;
        
    }

    @Override
    public void end(boolean interrupted) {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        DriveTrain driveTrain = subsystems.getDriveTrain();
        Climber climber = subsystems.getClimber();
        ControlWheel controlWheel = subsystems.getControlWheel();

        shooter.stop();
        driveTrain.stop();
        climber.stop();
        controlWheel.stop();
    }
}