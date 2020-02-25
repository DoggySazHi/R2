package org.usfirst.frc.team3952.robot.commands;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class GoToFilledSpot extends GoToEmptySpot {
    private RobotSubsystems subsystems;
/**
* Rotates magazine to filled spot
* @param subsystems
*/
    public GoToFilledSpot(RobotSubsystems subsystems) {
        super(subsystems);
        this.subsystems = subsystems;
    }

    @Override
    public void execute()
    {
        
    }

    @Override
    public boolean isFinished() {
        return !subsystems.getIntakeShooter().ballInPosition();
    }
}