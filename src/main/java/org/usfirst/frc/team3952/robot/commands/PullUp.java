package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.Climber;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;
import static org.usfirst.frc.team3952.robot.RobotMap.ACTIVATION_TIMER;
import static org.usfirst.frc.team3952.robot.RobotMap.CLIMB_MOTOR_SPEED;

import java.time.Duration;
import java.time.Instant;

public class PullUp extends CommandBase {
    private RobotSubsystems subsystems;
    private Instant startTime;

    public PullUp(RobotSubsystems subsystems)
    {
        this.subsystems = subsystems;
        addRequirements(subsystems.getClimber());
    }

    @Override
    public void initialize() {
        Climber climber = subsystems.getClimber();
        climber.deploy();
        startTime = Instant.now();
    }

    @Override
    public void execute() {
        Climber climber = subsystems.getClimber();
        if(Duration.between(startTime, Instant.now()).toMillis() < ACTIVATION_TIMER) return;
        climber.lift(CLIMB_MOTOR_SPEED);
    }

    @Override
    public boolean isFinished() {
        Climber climber = subsystems.getClimber();
        if(climber.hasHitTop())
        {
            climber.postDeploy();
            return true;
        }
        return false;
    }
}
