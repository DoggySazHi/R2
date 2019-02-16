package frc.robot.commands;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class DeployClaw extends Command {
    public static final double THRESHOLD = 2.5;

    Servo servo = RobotMap.clawActivator;

    public DeployClaw() {
        setInterruptible(false);
    }

    @Override
    protected void initialize() { }

    @Override
    protected void execute() {
        servo.setAngle(90.0);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(servo.getAngle() - 90.0) < THRESHOLD;
    }

    @Override
    protected void end() {}

    @Override
    protected void interrupted() {}
}