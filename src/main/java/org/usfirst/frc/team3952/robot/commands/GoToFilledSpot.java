package org.usfirst.frc.team3952.robot.commands;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class GoToFilledSpot extends GoToEmptySpot {
    private RobotSubsystems subsystems;

    public GoToFilledSpot(RobotSubsystems subsystems) {
        super(subsystems);
        this.subsystems = subsystems;
    }

    @Override
    public boolean isFinished() {
        return subsystems.getIntakeShooter().ballInPosition();
    }
}