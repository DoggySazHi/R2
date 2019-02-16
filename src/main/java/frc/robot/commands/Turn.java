package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.command.*;

import org.usfirst.frc.team3952.robot.*;

public class Turn extends Command {
	public static final double SPEED = 0.5;

	public double initialAngle;
	public double degrees;
	public long lastMillis;
	public boolean finished;

    public Turn(double degrees) {
        requires(Robot.drive);
        setInterruptible(false);
        this.degrees = degrees;
    }
    
	@Override
    protected void initialize() {
    	initialAngle = Robot.drive.gyro.getAngle();
    	lastMillis = System.currentTimeMillis();
    }
    
	@Override
    protected void execute() {
    	long nowMillis = System.currentTimeMillis();
    	if(differenceAngle(Robot.drive.gyro.getAngle() + Robot.drive.gyro.getRate() * (nowMillis - lastMillis) / 1000, initialAngle + degrees) < 7.0) {
    		Robot.drive.stop();
    		finished = true;
    	} else if(degrees < 0) {
    		Robot.drive.drive(0,  0,  -SPEED);
    	} else if(degrees > 0) {
    		Robot.drive.drive(0, 0, SPEED);
    	}
    	lastMillis = nowMillis;
    }
    
	@Override
    protected boolean isFinished() {
        return finished;
    }
    
	@Override
    protected void end() {
    	Robot.drive.stop();
    }
	
	@Override
    protected void interrupted() {
    	Robot.drive.stop();
    }
    
    private static double differenceAngle(double a1, double a2) {
    	return Math.abs(a1 % 360 - a2 % 360);
    }
}
