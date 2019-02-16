package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.ManualLadder;

public class Ladder extends Subsystem
{
	public static final int SWITCH_ENC = 2000;
	public static final double LADDER_EXTENDING_SPEED = 0.65;
	public static final double LADDER_RETRACTING_SPEED = 0.4;
	
	private Spark motor = RobotMap.ladder;
	
	public Encoder encoder = RobotMap.ladderEncoder;
	public DigitalInput topLimit = RobotMap.ladderTopLimit;
	public DigitalInput bottomLimit = RobotMap.ladderBottomLimit;
	
	public int pos = 0;

	int linearSpeed = 0;

    public void initDefaultCommand() { setDefaultCommand(new ManualLadder()); }

    public void extend() {
    	if(!topLimit.get()) {
    		motor.set(LADDER_EXTENDING_SPEED);
    	} else {
    		motor.set(0);
    	}
    }
    
    public void retract() {
    	if(!bottomLimit.get()) {
    		motor.set(-LADDER_RETRACTING_SPEED);   
    	} else {
    		motor.set(0);
    		encoder.reset();
    	}
    }

    public void goTo(double from, double to)
	{
		double speed;
		if(encoder.get() <= (to+from)/2)
		{
			//ladder is behind midpoint; speed up
			speed = speedRampUp() * encoder.get() < to ? 1 : -1;
			motor.set(speed);
		}
		else
		{
			//ladder is past midpoint; speed down
			speed = speedRampDown() * encoder.get() < to ? 1 : -1;
			motor.set(speed);
		}
	}

	//d referring to distance to
	private double speedRampUp()
	{
		linearSpeed++;
		return Math.min(1.0/75.0 * linearSpeed, 0.6);
	}

	private double speedRampDown()
	{
		linearSpeed--;
		return Math.max(Math.min(1.0/75.0 * linearSpeed, 0.6), 0.35);
	}

	public void resetGoto()
	{
		linearSpeed = 0;
	}

    public void stop() {
    	motor.set(0);
    }
}

