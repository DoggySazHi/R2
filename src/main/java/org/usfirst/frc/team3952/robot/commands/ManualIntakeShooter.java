package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.RobotMap;
import org.usfirst.frc.team3952.robot.devices.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

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

    private boolean isIntaking = false;

    @Override
    public void execute() {
        IntakeShooter intakeShooter = subsystems.getIntakeShooter();
        SecondaryController secondaryController = subsystems.getSecondaryController();

        if (secondaryController.getRawButton(4))
            intakeShooter.intake(secondaryController.getRawButton(3), (secondaryController.getThrottle() + 1.0)/2.0);
        else if (secondaryController.getRawButton(5))
            intakeShooter.reject(secondaryController.getRawButton(3), secondaryController.getRawButton(1));
        else
            intakeShooter.stop();

        // Already compensated.
        intakeShooter.setTiltServos(secondaryController.getHorizontalMovement());
        intakeShooter.setAngleMotor(secondaryController.getLateralMovement());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
