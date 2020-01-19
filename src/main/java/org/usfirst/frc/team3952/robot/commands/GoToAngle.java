package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class GoToAngle extends CommandBase
{
    private IntakeShooter intakeShooter;
    private double angle;

    public GoToAngle(RobotSubsystems subsystems, double angle) {
        intakeShooter = subsystems.getIntakeShooter();
        this.angle = angle;
        addRequirements(intakeShooter);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (intakeShooter.getPositionRaw() > angle)
            intakeShooter.setAngleMotor(-0.5);
        else 
            intakeShooter.setAngleMotor(0.5);
    }

    public boolean isFinished() {
        return Math.abs(intakeShooter.getPositionRaw()-angle)<2;
    }

    @Override
    public void end(boolean interrupted) {
    	intakeShooter.stop();
    }
}
