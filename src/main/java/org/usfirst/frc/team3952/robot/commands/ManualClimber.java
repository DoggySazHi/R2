package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.NetworkTableMap;
import org.usfirst.frc.team3952.robot.devices.MainController;
import org.usfirst.frc.team3952.robot.devices.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.Climber;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

/**
 * Handle the climber from the <code>MainController</code>, where the climber can be activated and the robot moving.
 */
public class ManualClimber extends CommandBase
{
    private final RobotSubsystems subsystems;

    public ManualClimber(RobotSubsystems subsystems) {
        this.subsystems = subsystems;

        Climber climber = subsystems.getClimber();

        addRequirements(climber);
    }

    @Override
    public void initialize() {}

    /**
     * Check for controller input and climb based on it.
     */
    @Override
    public void execute() {
        Climber climber = subsystems.getClimber();
        MainController mainController = subsystems.getMainController();
        SecondaryController secondaryController = subsystems.getSecondaryController();

        if(!NetworkTableMap.manualClimber.getBoolean(false))
        {
            if(mainController.getPOV() == 0)
                climber.lift(1.0);
            else if(mainController.getPOV() == 1 || mainController.getPOV() == 7)
                climber.lift(0.6);
            else if(mainController.getPOV() == 2 || mainController.getPOV() == 6 || mainController.getPOV() == -1)
                climber.lift(0.0);
            else if(mainController.getPOV() == 3 || mainController.getPOV() == 5)
                climber.lift(-0.6);
            else if(mainController.getPOV() == 4)
                climber.lift(-1.0);
        }
        else
        {
            climber.liftL(secondaryController.getLateralMovement());
            climber.liftR(mainController.getLateralMovement());
        }
    }

    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Climber climber = subsystems.getClimber();

        climber.stop();
    }
}
