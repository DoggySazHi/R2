package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ManualLadder extends Command
{
    public ManualLadder() {
        requires(Robot.ladder);
        setInterruptible(true);
    }

    protected void initialize() {}
    
    protected void execute() {
    	if(Robot.ladderController.getLateralMovement() > 0)
			Robot.ladder.extend();
    	else if(Robot.ladderController.getLateralMovement() < 0)
            Robot.ladder.retract();
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
