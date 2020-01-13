package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import org.usfirst.frc.team3952.robot.MainController;
import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class Actuator extends CommandBase
{
    private DriveTrain driveTrain;
    private MainController mainController;

    public Actuator(RobotSubsystems subsystems) {
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