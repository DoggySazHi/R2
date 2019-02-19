package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3952.robot.Robot;

public class MoveForward extends Command
{
	public double initialDistance;
	public double distance;
	public boolean finished;
	
	public MoveForward(double distance) {
		requires(Robot.driveTrain);
		setInterruptible(false);
		this.distance = distance;
	}

    protected void initialize() {
    	initialDistance = (Robot.driveTrain.left.getDistance() + Robot.driveTrain.right.getDistance()) / 2;
    }
    
    protected void execute() {
    	double currentDistance = (Robot.driveTrain.left.getDistance() + Robot.driveTrain.right.getDistance()) / 2;
    	if(currentDistance >= initialDistance + distance - 0.1) {
    		Robot.driveTrain.stop();
    		finished = true;
    	} else {
    		Robot.driveTrain.drive(0, 0.65, 0);
    	}
    }
    
    protected boolean isFinished() {
        return finished || Robot.ladderController.override();
    }
    
    protected void end() {
    	Robot.driveTrain.stop();
    }
    
    protected void interrupted() {
    	Robot.driveTrain.stop();
    }
}
