package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.NetworkTableMap;
import org.usfirst.frc.team3952.robot.devices.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

/**
 * Allows the drivers to control the IntakeShooter, including the aimer, tilt, and piston/storage.
 */
public class ManualIntakeShooter extends CommandBase {

    private final RobotSubsystems subsystems;
<<<<<<< HEAD
    private double lastValue;
=======
    public double lastValue;
>>>>>>> 77e12673e2b0166f8a7d3c32e06bcc4378de6e3a

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

        System.out.println("Running...");
        if (secondaryController.getRawButton(4))
        {
            System.out.println("Intaking...");
            intakeShooter.intake(secondaryController.getRawButton(3), (secondaryController.getThrottle() + 1.0) / 2.0);
        }
        else if (secondaryController.getRawButton(5))
        {
            System.out.println("Shooting...");
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
