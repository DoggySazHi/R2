package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveLadderExactly extends Command
{
    public static final double TIMEOUT = 8.0;
    int goTo;

    public MoveLadderExactly(int units)
    {
        requires(Robot.ladder);
        setTimeout(TIMEOUT);
        setInterruptible(false);
        goTo = units;
    }

    protected void execute()
    {
        if((int)Robot.ladder.encoder.getDistance() < goTo)
            Robot.ladder.extend();
        else
            Robot.ladder.retract();
    }

    protected boolean isFinished()
    {
        if(Math.abs(goTo - (int)Robot.ladder.encoder.getDistance()) < 100 || Robot.ladderController.override())
        {
            Robot.ladder.stop();
            return true;
        }
        return false;
    }
}