package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class ManualLadder extends Command
{
    public ManualLadder() {
        requires(Robot.ladder);
        setInterruptible(true);
    }

    protected void initialize() {}
    
    //literally a "manual ladder"

    protected void execute() {
    	if(Math.abs(Robot.ladderController.getLateralMovement()) > 0)
			RobotMap.ladder.set(Robot.ladderController.getLateralMovement());
        else
            Robot.ladder.stop();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.ladder.stop();
    }
    
    protected void interrupted() {
    	Robot.ladder.stop();
    }
}
