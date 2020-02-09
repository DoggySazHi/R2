package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.RobotMap.Position;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class GoToPosition extends CommandBase {
    private RobotSubsystems subsystems;

    private Position position;
    private int deadZone;
    private double speed;

    private double minSpeed;

    public GoToPosition(RobotSubsystems subsystems, Position pos) {
        this.subsystems = subsystems;
        position = pos;
        addRequirements(subsystems.getIntakeShooter());
    }

    @Override
    public void initialize() {
        deadZone = 5;
        speed = 0.5;
    }

    @Override
    //could be reversed based on situation
    public void execute() {
        IntakeShooter intakeShooter = subsystems.getIntakeShooter();
        minSpeed = position.getDistance() - intakeShooter.getAngle() > 0 ? 0.1 : -0.1;
        intakeShooter.setAngleMotor(speed * (position.getDistance() - intakeShooter.getAngle()) / 90 + minSpeed);
    }

    public boolean isFinished() {
        IntakeShooter intakeShooter = subsystems.getIntakeShooter();
        return intakeShooter.getAngle() - position.getDistance() <= deadZone;
    }

    @Override
    public void end(boolean interrupted) {
    	
    }
}
