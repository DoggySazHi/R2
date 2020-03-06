package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.devices.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

/**
 * Allows the drivers to control the IntakeShooter, including the aimer, tilt, and piston/storage.
 */
public class ManualIntakeShooter extends CommandBase {

    private RobotSubsystems subsystems;

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
        SecondaryController secondaryController = subsystems.getSecondaryController();

        if (secondaryController.getRawButton(4))
            intakeShooter.intake(secondaryController.getRawButton(3), (secondaryController.getThrottle() + 1.0) / 2.0);
        else if (secondaryController.getRawButton(5))
            intakeShooter.reject(secondaryController.getRawButton(3), secondaryController.getRawButton(1));
        else
            intakeShooter.stop();

        // Already compensated. Sets the tilt servo.
        intakeShooter.setTiltServos(secondaryController.getHorizontalMovement());

        // Sets the up/down movement.
        intakeShooter.setAngleMotor(-secondaryController.getLateralMovement());

        if (secondaryController.getRawButton(10))
            intakeShooter.setRotateMotor(0.25);
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
