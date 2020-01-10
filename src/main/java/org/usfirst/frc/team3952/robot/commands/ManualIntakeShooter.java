package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class ManualIntakeShooter extends CommandBase {

    private IntakeShooter intakeShooter;
    private SecondaryController secondaryController;

    public ManualIntakeShooter(RobotSubsystems subsystems) {
        intakeShooter = subsystems.getIntakeShooter();
        secondaryController = subsystems.getSecondaryController();

        addRequirements(intakeShooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (secondaryController.getRawButton(3))
            intakeShooter.reject();
        else if (secondaryController.getRawButton(4))
            intakeShooter.intake();
        else
            intakeShooter.stop();

        intakeShooter.setAngleMotor(secondaryController.getLateralMovement());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
