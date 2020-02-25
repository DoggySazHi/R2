package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.ControlWheel;
import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import static org.usfirst.frc.team3952.robot.RobotMap.DISTANCE_TO_WALL;
import static org.usfirst.frc.team3952.robot.RobotMap.DRIVE_CONTROL_WHEEL_SPEED;

public class GoToControlPanel extends CommandBase {
    private RobotSubsystems subsystems;

    public GoToControlPanel(RobotSubsystems subsystems) {
        this.subsystems = subsystems;
        DriveTrain driveTrain = subsystems.getDriveTrain();
        ControlWheel controlWheel = subsystems.getControlWheel();
        addRequirements(driveTrain, controlWheel);
    }
    @Override
    public void initialize() {
       
    }

    @Override
    public void execute() {
        DriveTrain driveTrain = subsystems.getDriveTrain();
        driveTrain.drive(0, DRIVE_CONTROL_WHEEL_SPEED, 0, false);
    }

    @Override
    public boolean isFinished() { 
        ControlWheel controlWheel = subsystems.getControlWheel();
        if(controlWheel.getDistance() < DISTANCE_TO_WALL)
        {
            controlWheel.enable();
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        DriveTrain driveTrain = subsystems.getDriveTrain();

        shooter.stop();
        driveTrain.stop();
    }
}