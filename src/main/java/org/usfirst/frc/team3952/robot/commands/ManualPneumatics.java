package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class ManualPneumatics extends CommandBase {
    private IntakeShooter intakeShooter;
    private SecondaryController secondaryController;

    public ManualPneumatics(RobotSubsystems subsystems) {
        intakeShooter = subsystems.getIntakeShooter();
        secondaryController = subsystems.getSecondaryController();

        //addRequirements(intakeShooter);
    }

    @Override
    public void initialize() {
        intakeShooter.retract();
    }

    boolean isFinished = false;

    @Override
    public void execute() {
        if(secondaryController.getRawButton(3))
            intakeShooter.retract();
        else if(secondaryController.getRawButton(4))
            intakeShooter.extend();
        else
            intakeShooter.stop();
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
