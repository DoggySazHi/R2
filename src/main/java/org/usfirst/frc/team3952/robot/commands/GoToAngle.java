package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class GoToAngle extends CommandBase
{
    private IntakeShooter intakeShooter;
    private double angle;
    private double minSpeed;

    public GoToAngle(RobotSubsystems subsystems, double angle) {
        intakeShooter = subsystems.getIntakeShooter();
        this.angle = angle;
        addRequirements(intakeShooter);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        //getpositionraw doesnt return an angle!
        //min speed to ensure it doesnt stop shot or take waayyy to long
        minSpeed = angle - intakeShooter.getAngle() > 0 ? 0.1 : -0.1;
        intakeShooter.setAngleMotor( (angle - intakeShooter.getAngle())/90 + minSpeed);
      
    }

    public boolean isFinished() {
        return Math.abs(intakeShooter.getPositionRaw()-angle)<2;
    }

    @Override
    public void end(boolean interrupted) {
    	intakeShooter.stop();
    }
}
