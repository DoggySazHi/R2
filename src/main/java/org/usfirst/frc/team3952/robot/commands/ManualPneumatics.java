package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3952.robot.Robot;

public class ManualPneumatics extends Command {
    public ManualPneumatics() {
        requires(Robot.pneumaticClaw);
        setTimeout(2);
        setInterruptible(true);
    }

    @Override
    protected void initialize() {}

    boolean isFinished = false;

    @Override
    protected void execute() {
        if(!Robot.pneumaticClaw.isExtended())
            Robot.pneumaticClaw.shoot();
        else
            Robot.pneumaticClaw.retract();
        isFinished = true;
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }

    @Override
    protected void end() {
    	Robot.pneumaticClaw.stop();
    }

    @Override
    protected void interrupted() {
    	Robot.pneumaticClaw.stop();
    }
}
