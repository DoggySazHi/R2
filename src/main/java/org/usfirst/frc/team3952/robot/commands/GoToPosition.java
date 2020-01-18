package org.usfirst.frc.team3952.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.RobotMap.Position;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class GoToPosition extends CommandBase
{
    private IntakeShooter intakeShooter;
    private Position position;
    private int deadZone;
    private double speed;

    public GoToPosition(RobotSubsystems subsystems, Position pos) {
        intakeShooter = subsystems.getIntakeShooter();
        position = pos;
        addRequirements(intakeShooter);
    }

    @Override
    public void initialize() {
        deadZone = 5;
        speed = 0.5;
    }

    @Override
    //could be reversed based on situation
    public void execute() {
        if(intakeShooter.getAngle() < position.getDistance()){
            intakeShooter.setAngleMotor(speed);
        }
        else{
            intakeShooter.setAngleMotor(-speed);
        }
    }

    public boolean isFinished() {
        return intakeShooter.getAngle() - position.getDistance() <= deadZone;
    }

    @Override
    public void end(boolean interrupted) {
    	
    }
}
