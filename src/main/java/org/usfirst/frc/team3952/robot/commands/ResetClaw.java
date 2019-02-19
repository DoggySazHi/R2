package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3952.robot.RobotMap;

public class ResetClaw extends Command
{
    public static final double THRESHOLD = 2.5;

    Servo servo = RobotMap.clawActivator;

    public ResetClaw() {
        setInterruptible(false);
    }

    @Override
    protected void initialize() { }

    @Override
    protected void execute() {
        servo.setAngle(0);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(servo.getAngle()) < THRESHOLD;
    }

    @Override
    protected void end() {}

    @Override
    protected void interrupted() {}
}
