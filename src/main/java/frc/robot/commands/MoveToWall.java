package org.usfirst.frc.team3952.robot.commands;

import org.usfirst.frc.team3952.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.*;

import org.usfirst.frc.team3952.robot.*;

// Ultrasonic data tolerable within 11.6347742 in. - 16.5 in.
public class MoveToWall extends Command { 
    //TODO: edit
    public static final double STOPPING_DISTANCE = 12.0;
    public static final double SPEED = 0.5; 

    public double v2in = 41.552765;
    public boolean finished = false;

    public AnalogInput sensor = RobotMap.ultrasonicSensor;
	
	public MoveToWall() {
        requires(Robot.drive);
		setInterruptible(false);
	}

    @Override
    protected void initialize() {}
    
    @Override
    protected void execute() {
        if (sensor.getVoltage() * v2in > STOPPING_DISTANCE) {
            Robot.drive.drive(0, SPEED, 0);
        } else {
            Robot.drive.stop();
            finished = true;
        }
        
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
    
}
