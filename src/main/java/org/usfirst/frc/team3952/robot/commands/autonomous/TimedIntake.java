package org.usfirst.frc.team3952.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class TimedIntake extends CommandBase {
    private RobotSubsystems subsystems;
    private long timeStarted;
    private long time;

    public TimedIntake(RobotSubsystems subsystems, long time) {
        this.subsystems = subsystems;
        addRequirements(subsystems.getIntakeShooter());
        this.time = time;
    }

    @Override
    public void initialize() {
        timeStarted = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        shooter.intake(false);
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - timeStarted >= time;
    }

    @Override
    public void end(boolean interrupted) {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        shooter.stop();
    }
}