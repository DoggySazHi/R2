package org.usfirst.frc.team3952.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class TimedShoot extends CommandBase {
    private RobotSubsystems subsystems;
    private long timeStarted;
    private long primeMillis;
    private long shootMillis;
    private boolean disableStop;

    public TimedShoot(RobotSubsystems subsystems, long primeMillis, long shootMillis, boolean disableStop) {
        this.subsystems = subsystems;
        addRequirements(subsystems.getIntakeShooter());
        this.primeMillis = primeMillis;
        this.shootMillis = shootMillis;
        this.disableStop = disableStop;
    }

    @Override
    public void initialize() {
        timeStarted = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        if (System.currentTimeMillis() - timeStarted >= primeMillis)
            shooter.reject(false, true);
        else
            shooter.reject(false, false);
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - timeStarted >= primeMillis + shootMillis;
    }

    @Override
    public void end(boolean interrupted) {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        if (!disableStop)
            shooter.stop();
        shooter.retract();
    }
}