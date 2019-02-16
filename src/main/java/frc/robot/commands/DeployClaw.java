package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.command.*;

import org.usfirst.frc.team3952.robot.*;

public class DeployClaw extends Command {
    public static final double DELTA = 2.5;

    public boolean finished;

    public DeployClaw() {
        setInterruptible(false);
    }

    @Override
    protected void initialize() {
        finished = false;
    }

    @Override
    protected void execute() {
        RobotMap.servo.setAngle(90.0);
        if(Math.abs(RobotMap.servo.getAngle() - 90.0) < DELTA){
            finished = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    @Override
    protected void end() {}

    @Override
    protected void interrupted() {}
}