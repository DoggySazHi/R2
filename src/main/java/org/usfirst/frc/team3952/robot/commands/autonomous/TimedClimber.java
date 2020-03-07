package org.usfirst.frc.team3952.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.Climber;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class TimedClimber extends CommandBase {
    private RobotSubsystems subsystems;
    private long timeStarted;
    private long time;
    private double speed;

    public TimedClimber(RobotSubsystems subsystems, long time, double speed) {
        this.subsystems = subsystems;
        addRequirements(subsystems.getClimber());
        this.time = time;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        timeStarted = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        Climber climber = subsystems.getClimber();
        climber.lift(speed);
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - timeStarted >= time;
    }

    @Override
    public void end(boolean interrupted) {
        Climber climber = subsystems.getClimber();
        climber.stop();
    }
}