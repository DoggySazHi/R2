package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import static org.usfirst.frc.team3952.robot.RobotMap.STORAGE_MOTOR_SPEED;

public class GoToNextPosition extends CommandBase {
    private boolean nextLevel;
    private final RobotSubsystems subsystems;
    private boolean stageTwo;
    private boolean wasLocked;

    public GoToNextPosition(RobotSubsystems subsystems) {
        this.subsystems = subsystems;
        IntakeShooter shooter = subsystems.getIntakeShooter();
        //addRequirements(shooter);
    }
    
    @Override
    public void initialize() {
        System.out.println("Started nextPos!");
        IntakeShooter shooter = subsystems.getIntakeShooter();

        if(!shooter.isLocked())
            stageTwo = true;
    }

    @Override
    public void execute() {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        shooter.setRotateMotor(STORAGE_MOTOR_SPEED);   
        if(shooter.isLocked() && stageTwo)  
        {
            shooter.advance();
            nextLevel = true;
        }
        else if(shooter.isLocked() && !stageTwo && !wasLocked)
        {
            wasLocked = true;
        }
        else if(!shooter.isLocked() && !stageTwo && wasLocked)
        {
            stageTwo = true;
        }
    }

    @Override
    public boolean isFinished() {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        System.out.println(nextLevel + " - " + shooter.isLocked());
        return nextLevel && shooter.isLocked();
    }
    
    @Override
    public void end(boolean interrupted) {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        //shooter.stop();
        wasLocked = false;
        stageTwo = false;
        nextLevel = false;
    }
}