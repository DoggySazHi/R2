package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3952.robot.Robot;

public class Turn extends Command {
	public static final double SPEED = 0.5;

	public double initialAngle;
	public double degrees;
	public long lastMillis;
	public boolean finished;

    public Turn(double degrees) {
        requires(Robot.driveTrain);
        setInterruptible(false);
        this.degrees = degrees;
    }
    
	@Override
    protected void initialize() {
    	initialAngle = Robot.driveTrain.gyro.getAngle();
    	lastMillis = System.currentTimeMillis();
    }
    
	@Override
    protected void execute() {
    	long nowMillis = System.currentTimeMillis();
    	if(differenceAngle(Robot.driveTrain.gyro.getAngle() + Robot.driveTrain.gyro.getRate() * (nowMillis - lastMillis) / 1000, initialAngle + degrees) < 7.0) {
    		Robot.driveTrain.stop();
    		finished = true;
    	} else if(degrees < 0) {
    		Robot.driveTrain.drive(0,  0,  -SPEED);
    	} else if(degrees > 0) {
    		Robot.driveTrain.drive(0, 0, SPEED);
    	}
    	lastMillis = nowMillis;
    }
    
	@Override
    protected boolean isFinished() {
        return finished;
    }
    
	@Override
    protected void end() {
    	Robot.driveTrain.stop();
    }
	
	@Override
    protected void interrupted() {
    	Robot.driveTrain.stop();
    }
    
    private static double differenceAngle(double a1, double a2) {
    	return Math.abs(a1 % 360 - a2 % 360);
    }
}
