package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.NetworkTableMap;
import org.usfirst.frc.team3952.robot.devices.MainController;
import org.usfirst.frc.team3952.robot.devices.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

/**
 * Allows the drivers to control the IntakeShooter, including the aimer, tilt, and piston/storage.
 */
public class ManualIntakeShooter extends CommandBase {

    private final RobotSubsystems subsystems;
    private double lastValue;

    public ManualIntakeShooter(RobotSubsystems subsystems) {
        this.subsystems = subsystems;
        addRequirements(subsystems.getIntakeShooter());
    }

    @Override
    public void initialize() {
        subsystems.getIntakeShooter().retract();
    }

    @Override
    public void execute() {
        IntakeShooter intakeShooter = subsystems.getIntakeShooter();
        MainController mainController = subsystems.getMainController();
        SecondaryController secondaryController = subsystems.getSecondaryController();

        if (mainController.getRawButton(1))
        {
            intakeShooter.setRotateMotor(-0.5 /* (secondaryController.getThrottle() + 1.0) / 2.0 */);
        }

        if (secondaryController.getRawButton(4))
        {
            intakeShooter.intake(secondaryController.getRawButton(3), (secondaryController.getThrottle() + 1.0) / 2.0);
        }
        else if (secondaryController.getRawButton(5))
        {
            intakeShooter.reject(secondaryController.getRawButton(3), secondaryController.getRawButton(1));
        }
        else
            intakeShooter.stop();

        if(!NetworkTableMap.manualClimber.getBoolean(false)) {
            // Already compensated. Sets the tilt servo.
            if (!secondaryController.getRawButton(11)){
                intakeShooter.setTiltServos(secondaryController.getHorizontalMovement());
                lastValue = secondaryController.getHorizontalMovement();
            } else {
                intakeShooter.setTiltServos(lastValue);
            }


            // Sets the up/down movement.
            intakeShooter.setAngleMotor(-secondaryController.getLateralMovement());
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted)
    {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        shooter.stop();
    }
}
