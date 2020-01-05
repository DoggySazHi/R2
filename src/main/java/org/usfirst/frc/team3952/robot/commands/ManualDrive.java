package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import org.usfirst.frc.team3952.robot.MainController;
import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class ManualDrive extends CommandBase
{
    private DriveTrain driveTrain;
    private MainController mainController;

    public ManualDrive(RobotSubsystems subsystems) {
        driveTrain = subsystems.getDriveTrain();
        mainController = subsystems.getMainController();

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        double hor = mainController.getHorizontalMovement();
        double lat = mainController.getLateralMovement();
        double rot = mainController.getRotation();
        int pov = mainController.getPOV();
        if (pov == 1 || pov == 3)
            hor = 0.4;
        else if (pov == 2)
            hor = 0.8;
        else if (pov == 5 || pov == 7)
            hor = -0.4;
        else if (pov == 6)
            hor = -0.8;
    	driveTrain.drive(hor, lat, rot, mainController.getQuickTurn());
    }

    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    	driveTrain.stop();
    }
}
