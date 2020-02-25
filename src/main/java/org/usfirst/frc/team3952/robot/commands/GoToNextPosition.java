package org.usfirst.frc.team3952.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static org.usfirst.frc.team3952.robot.RobotMap.STORAGE_MOTOR_SPEED;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class GoToNextPosition extends CommandBase {
    private boolean nextLevel;
    private RobotSubsystems subsystems;
    private boolean wasLocked;
    public GoToNextPosition(RobotSubsystems subsystems) {
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
            nextLevel = true;   
        }
    }

    @Override
    public boolean isFinished() {
        return nextLevel;
    }
    
    @Override
    public void end(boolean interruptable) {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        shooter.stop();
    }
}