package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.NetworkTableMap;
import org.usfirst.frc.team3952.robot.RobotMap;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;

import static org.usfirst.frc.team3952.robot.RobotMap.ACCELEROMETER_THRESHOLD_ACCELERATION;

public class DriveTrain extends SubsystemBase {
    private CANPWMFallback leftDrive = RobotMap.leftDriveFront;
    private CANPWMFallback rightDrive = RobotMap.rightDriveFront;
    private DifferentialDrive drive = RobotMap.drive;
    private double xPos;
    private double yPos;
    private static final double GRAVITY = 9.80665;
    private long lastCheck;
    private double xVel;
    private double yVel;
    //code accounts for reversing of motors no need for mechanical solution

    /**
     * It is the method that moves the robot, it takes in 4 parameters. There are deadzones in each parameter. Quickturn amplifies the rot value.
     * This method changes its drive method based on RobotMap settings. See wpilib documentation for what each drive methods takes.
     */
    public void drive(double hor, double lat, double rot, boolean quickTurn) {
        if(quickTurn)
            System.out.print("mukyu");
        if(quickTurn)
            rot = Math.min(1.0, Math.max(-1.0, Math.pow(rot, 2)));
        drive.arcadeDrive(lat, rot, quickTurn);
        BuiltInAccelerometer accelerometer = RobotMap.accelerometer;

        long deltaTime = System.currentTimeMillis() - lastCheck;
        lastCheck = System.currentTimeMillis();
        if (deltaTime >= 1000) return;
        double xAccel = accelerometer.getX() < ACCELEROMETER_THRESHOLD_ACCELERATION ? 0 : accelerometer.getX();
        double yAccel = accelerometer.getY() < ACCELEROMETER_THRESHOLD_ACCELERATION ? 0 : accelerometer.getY();

        xVel += xAccel * deltaTime / 1000.0 * GRAVITY;
        yVel += yAccel * deltaTime / 1000.0 * GRAVITY;
        xPos += xVel * deltaTime / 1000.0;
        yPos += yVel * deltaTime / 1000.0;

        NetworkTableMap.xPosition.setDouble(xPos);
        NetworkTableMap.yPosition.setDouble(yPos);
        NetworkTableMap.xVelocity.setDouble(xVel);
        NetworkTableMap.yVelocity.setDouble(yVel);
    }

    /**
     * It stops the drivetrain
     */
    public void stop() {
        drive.stopMotor();
    }
}