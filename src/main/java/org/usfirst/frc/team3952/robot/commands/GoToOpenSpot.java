package org.usfirst.frc.team3952.robot.commands;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class GoToOpenSpot extends GoToEmptySpot {
    private RobotSubsystems subsystems;

    public GoToOpenSpot(RobotSubsystems subsystems) {
        super(subsystems);
        this.subsystems = subsystems;
    }

    @Override
    public boolean isFinished() {
        return subsystems.getIntakeShooter().ballInPosition();
    }
}