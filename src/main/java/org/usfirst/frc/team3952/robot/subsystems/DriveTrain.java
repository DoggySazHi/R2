package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.*;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;

import static org.usfirst.frc.team3952.robot.RobotMap.ARCADE_OR_CURVATURE;
import static org.usfirst.frc.team3952.robot.RobotMap.CONTROLLER_DRIVE_MODE;

public class DriveTrain extends SubsystemBase {
    private CANPWMFallback leftDrive = RobotMap.leftDriveFront;
    private CANPWMFallback rightDrive = RobotMap.rightDriveFront;
    private DifferentialDrive drive = RobotMap.drive;

    //code accounts for reversing of motors no need for mechanical solution

    /**
    * It is the method that moves the robot, it takes in 4 parameters. There are deadzones in each parameter. Quickturn amplifies the rot value.
    * This method changes its drive method based on RobotMap settings. See wpilib documentation for what each drive methods takes.
    */
    public void drive(double hor, double lat, double rot, boolean quickTurn) {
        // System.out.println("Hor: " + hor + " Lat: " + lat + " Rot: " + rot);
        if(CONTROLLER_DRIVE_MODE)
            if(ARCADE_OR_CURVATURE)
                drive.curvatureDrive(lat, rot, quickTurn);
            else
                drive.arcadeDrive(lat, rot, quickTurn);
        else
            if(ARCADE_OR_CURVATURE)
                drive.curvatureDrive(lat, hor, quickTurn);
            else
                drive.arcadeDrive(lat, hor, quickTurn);
        /*
    	if (quickTurn){
            leftDrive.set(rot);
            rightDrive.set(rot);
        } else {
    	    double leftPwr = lat + rot;
    	    double rightPwr = lat - rot;
            leftDrive.set(Math.sqrt(Math.abs(leftPwr)) * Math.signum(leftPwr));
            rightDrive.set(-Math.sqrt(Math.abs(rightPwr)) * Math.signum(rightPwr));
        }
         */
    }
    /**
    * It stops the drivetrain
    */
    public void stop() {
        drive.stopMotor();
    }
}