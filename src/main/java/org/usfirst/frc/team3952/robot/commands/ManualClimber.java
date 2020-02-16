package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.devices.MainController;
import org.usfirst.frc.team3952.robot.devices.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.Climber;
import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class ManualClimber extends CommandBase
{
    private RobotSubsystems subsystems;

    public ManualClimber(RobotSubsystems subsystems) {
        this.subsystems = subsystems;
        addRequirements(subsystems.getClimber());
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Climber climber = subsystems.getClimber();
        MainController mainController = subsystems.getMainController();
        SecondaryController secondaryController = subsystems.getSecondaryController();

        if(mainController.getRawButton(5))
            climber.deploy();
        if(mainController.getRawButton(6))
            climber.retract();

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

    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        DriveTrain driveTrain = subsystems.getDriveTrain();
        driveTrain.stop();
    }
}
