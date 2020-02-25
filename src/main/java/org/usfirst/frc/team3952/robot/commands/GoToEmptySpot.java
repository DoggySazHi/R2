package org.usfirst.frc.team3952.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static org.usfirst.frc.team3952.robot.RobotMap.STORAGE_MOTOR_SPEED;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class GoToEmptySpot extends CommandBase {
    private RobotSubsystems subsystems;
    private boolean wasLocked;
    /**
    * rotates magazine to next spot for loading
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

    @Override
    public boolean isFinished() {
        return !subsystems.getIntakeShooter().ballInPosition();
    }

    @Override
    public void end(boolean interrupted) {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        shooter.stop();
    }
}