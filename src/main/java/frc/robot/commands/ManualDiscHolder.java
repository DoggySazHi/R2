package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.command.*;

import org.usfirst.frc.team3952.robot.*;

public class ManualDiscHolder extends Command {
    public ManualDiscHolder() {
        requires(Robot.discHolder);
        setInterruptible(true);
    }

    @Override
    protected void initialize() {}

    @Override
    protected void execute() {
        if(Robot.subController.releaseDisc()) {
            Robot.discHolder.shoot();
        } else if(Robot.subController.grabDisc()) {
            Robot.discHolder.retract();
        } else {
            Robot.discHolder.stop();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    	Robot.discHolder.stop();
    }

    @Override
    protected void interrupted() {
    	Robot.discHolder.stop();
    }
}
