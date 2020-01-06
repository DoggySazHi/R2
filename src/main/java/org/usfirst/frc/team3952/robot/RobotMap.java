package org.usfirst.frc.team3952.robot;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.util.Color;
import com.ctre.phoenix.motorcontrol.can.*;

//It's sparks, not talons lucas, ya dumbie

public class RobotMap {
    //PWM
    public static final int FRONT_LEFT_WHEEL_PORT = 0;
    public static final int FRONT_RIGHT_WHEEL_PORT = 3;
    public static final int REAR_LEFT_WHEEL_PORT = 2;
    public static final int REAR_RIGHT_WHEEL_PORT = 1;
    public static final int INTAKE_LEFT_PORT = 5;
    public static final int INTAKE_RIGHT_PORT = 6;

    //CAN
    public static final int PROJECTILE_BARREL_PORT = 1;
    public static final int CONTROL_PANEL_SPINNER = 0;

    //PNEUMATIC BOARD (PCM)
    public static final int CLAW_SOLENOID_PORT_1 = 0;
    public static final int CLAW_SOLENOID_PORT_2 = 1;

    //ANALOG IN

    //AUTO ALIGN
    public static final int MIN_DISTANCE_FROM_TARGET = 10;
    public static final int PERIOD = 30;
    public static final int STEP = 5;
    public static final double STEPPING_SPEED = 0.7;

    //Control Panel TODO
    public static final Color CP_RED = ColorMatch.makeColor(0.5, 0.1, 0.1);
    public static final Color CP_GREEN = ColorMatch.makeColor(0.1, 0.5, 0.1);
    public static final Color CP_BLUE = ColorMatch.makeColor(0.1, 0.1, 0.5);
    public static final Color CP_YELLOW = ColorMatch.makeColor(0.4, 0.4, 0.1);
    public static final Color[] WHEEL = new Color[] {CP_RED, CP_GREEN, CP_BLUE, CP_YELLOW};
    public static final int MIN_COUNT = 24;

    //OBJECTS
    public static Spark frontLeftWheel;
    public static Spark frontRightWheel;
    public static Spark rearLeftWheel;
    public static Spark rearRightWheel;
    
    public static VictorSPX projectileBarrel;
    public static VictorSPX controlPanelSpinner;

    public static Talon intakeLeft;
    public static Talon intakeRight;

    public static DifferentialDrive drive;

    public static DoubleSolenoid claw;

    public static ColorSensorV3 colorSensor;

    public static void init() {
        frontLeftWheel = new Spark(FRONT_LEFT_WHEEL_PORT);
        frontRightWheel = new Spark(FRONT_RIGHT_WHEEL_PORT);
        rearLeftWheel = new Spark(REAR_LEFT_WHEEL_PORT);
        rearRightWheel = new Spark(REAR_RIGHT_WHEEL_PORT);
        projectileBarrel = new VictorSPX(PROJECTILE_BARREL_PORT);
        controlPanelSpinner = new VictorSPX(CONTROL_PANEL_SPINNER);
        intakeLeft = new Talon(INTAKE_LEFT_PORT);
        intakeRight = new Talon(INTAKE_RIGHT_PORT);

        SpeedControllerGroup left = new SpeedControllerGroup(frontLeftWheel, rearLeftWheel);
        SpeedControllerGroup right = new SpeedControllerGroup(frontRightWheel, rearRightWheel);

        drive = new DifferentialDrive(left, right);

        // No PCM installed
        //claw = new DoubleSolenoid(CLAW_SOLENOID_PORT_1, CLAW_SOLENOID_PORT_2);

        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    }
}
