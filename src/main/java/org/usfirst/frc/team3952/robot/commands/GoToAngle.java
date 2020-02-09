package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class GoToAngle extends CommandBase {
    private IntakeShooter intakeShooter;
    private double angle;

    private double minSpeed;

    public GoToAngle(RobotSubsystems subsystems, double angle) {
        intakeShooter = subsystems.getIntakeShooter();
        this.angle = angle;
        addRequirements(intakeShooter);
    }

    @Override
    public void initialize() {
        minSpeed = angle - intakeShooter.getAngle() > 0 ? 0.1 : -0.1;
    }

    @Override
    public void execute() {
        //getPositionRaw() doesnt return an angle!
        //min speed to ensure it doesnt stop shot or take waayyy too long

        // Keep the number in bounds between -1.0 and 1.0.
        intakeShooter.setAngleMotor(Math.min(1.0, Math.max(-1.0, (angle - intakeShooter.getAngle()) / 90.0 + minSpeed)));
    }

    public boolean isFinished() {
        return Math.abs(intakeShooter.getPositionRaw()-angle)<2;
    }

    @Override
    public void end(boolean interrupted) {
    	intakeShooter.stop();
    }
}
