package org.usfirst.frc.team3952.robot.commands;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

/**
 * Go to the next filled spot, assuming there are balls in the shooter.
 */
public class GoToFilledSpot extends GoToEmptySpot {
    private final RobotSubsystems subsystems;

    public GoToFilledSpot(RobotSubsystems subsystems) {
        super(subsystems);
        this.subsystems = subsystems;
    }

    /**
     * Ends whenever it detects that the current position is filled, or if the shooter is empty.
     * @return If the command is finished.
     */
    @Override
    public boolean isFinished() {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        return shooter.ballInPosition() || shooter.getBalls() == 0;
    }
}