package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ManualLadder extends Command
{
    boolean extending;

    public ManualLadder(boolean extending) {
        requires(Robot.ladder);
        setInterruptible(true);
        this.extending = extending;
    }

    protected void initialize() {}
    
    protected void execute() {
    	if(extending)
			Robot.ladder.extend();
    	else
			Robot.ladder.retract();
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
