package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import static org.usfirst.frc.team3952.robot.RobotMap.MAX_BALL_STORAGE;
import static org.usfirst.frc.team3952.robot.RobotMap.STORAGE_MOTOR_SPEED;

public class GoToEmptySpot extends CommandBase {
    private final RobotSubsystems subsystems;
    private boolean wasLocked;

    /**
     * rotates magazine to next spot for loading
     *
     * @param subsystems gets subsystem
     */
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
        shooter.setRotateMotor(STORAGE_MOTOR_SPEED);
        if(shooter.isLocked() && !wasLocked)  
        {
            shooter.advance();
            wasLocked = true;
        }
        else if(!shooter.isLocked() && wasLocked)
        {
            wasLocked = false;   
        }
    }

    /**
     * Ends whenever it detects that the current position is empty, or if the shooter is full.
     * @return If the command is finished.
     */
    @Override
    public boolean isFinished() {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        return !shooter.ballInPosition() || shooter.getBalls() == MAX_BALL_STORAGE;
    }

    @Override
    public void end(boolean interrupted) {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        shooter.stop();
    }
}