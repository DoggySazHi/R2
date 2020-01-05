package org.usfirst.frc.team3952.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

//It's sparks, not talons lucas, ya dumbie

public class RobotMap {
    //PWM
    public static final int FRONT_LEFT_WHEEL_PORT = 0;
    public static final int FRONT_RIGHT_WHEEL_PORT = 3;
    public static final int REAR_LEFT_WHEEL_PORT = 2;
    public static final int REAR_RIGHT_WHEEL_PORT = 1;
    public static final int PROJECTILE_BARREL_PORT = 5;
    public static final int CONTROL_PANEL_SPINNER = 6;

    //PNEUMATIC BOARD (PCM)
    public static final int CLAW_SOLENOID_PORT_1 = 0;
    public static final int CLAW_SOLENOID_PORT_2 = 1;

    //ANALOG IN
    public static final int QTI_SENSOR_PORT = 0;
    public static final int ULTRASONIC_PORT = 1;

    //AUTO ALIGN
    public static final int MIN_DISTANCE_FROM_TARGET = 10;
    public static final int PERIOD = 30;
    public static final int STEP = 5;
    public static final double STEPPING_SPEED = 0.7;

    //OBJECTS
    public static Spark frontLeftWheel;
    public static Spark frontRightWheel;
    public static Spark rearLeftWheel;
    public static Spark rearRightWheel;
    
    public static PWMVictorSPX projectileBarrel;
    public static PWMVictorSPX controlPanelSpinner;

    public static DifferentialDrive drive;

    public static DigitalInput ladderTopLimit;
    public static DigitalInput ladderBottomLimit;
    public static DigitalInput clawOpeningLimit;
    public static DigitalInput clawClosingLimit;
    public static DoubleSolenoid claw;

    public static void init() {
        frontLeftWheel = new Spark(FRONT_LEFT_WHEEL_PORT);
        frontRightWheel = new Spark(FRONT_RIGHT_WHEEL_PORT);
        rearLeftWheel = new Spark(REAR_LEFT_WHEEL_PORT);
        rearRightWheel = new Spark(REAR_RIGHT_WHEEL_PORT);

        projectileBarrel = new PWMVictorSPX(PROJECTILE_BARREL_PORT);
        controlPanelSpinner = new PWMVictorSPX(CONTROL_PANEL_SPINNER);

        SpeedControllerGroup left = new SpeedControllerGroup(frontLeftWheel, rearLeftWheel);
        SpeedControllerGroup right = new SpeedControllerGroup(frontRightWheel, rearRightWheel);

        drive = new DifferentialDrive(left, right);

        // No PCM installed
        //claw = new DoubleSolenoid(CLAW_SOLENOID_PORT_1, CLAW_SOLENOID_PORT_2);
    }
}
