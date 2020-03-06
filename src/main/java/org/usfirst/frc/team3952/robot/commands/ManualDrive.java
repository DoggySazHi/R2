package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.NetworkTableMap;
import org.usfirst.frc.team3952.robot.devices.MainController;
import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;
import org.usfirst.frc.team3952.robot.NetworkTableMap.CameraMode;
import static org.usfirst.frc.team3952.robot.NetworkTableMap.cameraMode;

public class ManualDrive extends CommandBase
{
    private final RobotSubsystems subsystems;
    private final CameraMode[] modes;

    public ManualDrive(RobotSubsystems subsystems) {
        this.subsystems = subsystems;
        modes = CameraMode.values();
        addRequirements(subsystems.getDriveTrain());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        MainController mainController = subsystems.getMainController();
        CameraMode currentMode = modes[(int) cameraMode.getDouble(2)];
        mainController.setInverted(currentMode == CameraMode.Rear);
        DriveTrain driveTrain = subsystems.getDriveTrain();

        double hor = mainController.getHorizontalMovement();
        double lat = mainController.getLateralMovement();
        double rot = mainController.getRotation();
        //System.out.println("Hor: " + hor + " Lat: " + lat + " Rot: " + rot);

        driveTrain.drive(hor, lat, rot, mainController.getQuickTurn());
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
