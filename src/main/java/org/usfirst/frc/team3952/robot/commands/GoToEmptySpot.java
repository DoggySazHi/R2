package org.usfirst.frc.team3952.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static org.usfirst.frc.team3952.robot.RobotMap.STORAGE_MOTOR_SPEED;

import org.usfirst.frc.team3952.robot.devices.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class GoToEmptySpot extends CommandBase {
    private RobotSubsystems subsystems;
    private boolean wasLocked;
    public GoToEmptySpot(RobotSubsystems subsystems) {
        this.subsystems = subsystems;
        IntakeShooter shooter = subsystems.getIntakeShooter();
        addRequirements(shooter);
    }
    @Override
    public void initialize() {
    }

    public boolean off = true;

    @Override
    public void execute() {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        SecondaryController secondaryController = subsystems.getSecondaryController();

        shooter.setRotateMotor(0.3);   
             
    }

    @Override
    public void end(boolean interrupted) {
        IntakeShooter shooter = subsystems.getIntakeShooter();

        shooter.stop();
    }

    @Override
    public boolean isFinished() {
        IntakeShooter shooter = subsystems.getIntakeShooter();

        return !shooter.isLocked();
    }
}