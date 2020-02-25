package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

/**
 * Tilt the shooter to the lowest position available.
 */
public class IntakeBall extends CommandBase {

    private RobotSubsystems subsystems;

    public IntakeBall(RobotSubsystems subsystems) {
        this.subsystems = subsystems;
        IntakeShooter shooter = subsystems.getIntakeShooter();

        addRequirements(shooter);
    }

    @Override
    public void initialize() {

    }

    /**
     * Sets the motor to the fastest speed downwards. NOTE: Might be inverted.
     */
    @Override
    public void execute() {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        shooter.setAngleMotor(1.0);
    }

    @Override
    public boolean isFinished() {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        return shooter.hitBottom();
    }

    @Override
    public void end(boolean interrupted)
    {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        shooter.stop();
    }
}
