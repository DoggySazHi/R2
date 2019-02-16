package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ManualPneumatics extends Command {
    boolean extend;

    public ManualPneumatics(boolean extend) {
        this.extend = extend;
        requires(Robot.pneumaticClaw);
        setTimeout(2);
        setInterruptible(true);
    }

    @Override
    protected void initialize() {}

    @Override
    protected void execute() {
        if(extend)
            Robot.pneumaticClaw.shoot();
        else
            Robot.pneumaticClaw.retract();
    }

    @Override
    protected boolean isFinished() {
        return false;
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
