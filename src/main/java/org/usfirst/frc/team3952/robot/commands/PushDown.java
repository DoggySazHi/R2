package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.Climber;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import java.time.Duration;
import java.time.Instant;

import static org.usfirst.frc.team3952.robot.RobotMap.*;

public class PushDown extends CommandBase {
    private RobotSubsystems subsystems;
    private Instant startTime;

    public PushDown(RobotSubsystems subsystems)
    {
        this.subsystems = subsystems;
        addRequirements(subsystems.getClimber());
    }

    @Override
    public void initialize() {
        startTime = Instant.now();
    }

    @Override
    public void execute() {
        Climber climber = subsystems.getClimber();
        climber.lift(DESCEND_MOTOR_SPEED);
    }

    @Override
    public boolean isFinished() {
        Climber climber = subsystems.getClimber();
        if(Duration.between(startTime, Instant.now()).toMillis() >= CLIMBER_DEACTIVATION_TIMER)
        {
            climber.retract();
            return true;
        }
        return false;
    }
}