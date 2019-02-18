/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

//It's sparks, not talons lucas, ya dumbie

public class RobotMap {
    //PWM
    public static final int FRONT_LEFT_WHEEL_PORT = 0;
    public static final int FRONT_RIGHT_WHEEL_PORT = 3;
    public static final int REAR_LEFT_WHEEL_PORT = 2;
    public static final int REAR_RIGHT_WHEEL_PORT = 1;
    public static final int LADDER_PORT = 4;
    public static final int ACTIVATE_CLAW_SERVO = 5;

    //DIGITAL IO

    // these are the rear encoder ports, wtf mech
    public static final int LEFT_ENCODER_PORT_1 = 2;
    public static final int LEFT_ENCODER_PORT_2 = 3;
    public static final int RIGHT_ENCODER_PORT_1 = 0;
    public static final int RIGHT_ENCODER_PORT_2 = 1;

    public static final int LADDER_ENCODER_PORT_1 = 4;
    public static final int LADDER_ENCODER_PORT_2 = 5;
    public static final int LADDER_TOP_LIMIT_PORT = 6;
    public static final int LADDER_BOTTOM_LIMIT_PORT = 7;

    //PNEUMATIC BOARD (PCM)
    public static final int CLAW_SOLENOID_PORT_1 = 0;
    public static final int CLAW_SOLENOID_PORT_2 = 1;

    //ANALOG IN
    public static final int QTI_SENSOR_PORT = 0;
    public static final int ULTRASONIC_PORT = 1;

    public static Spark frontLeftWheel;
    public static Spark frontRightWheel;
    public static Spark rearLeftWheel;
    public static Spark rearRightWheel;
    public static Servo clawActivator;
    public static MecanumDrive drive;
    public static Encoder leftEncoder;
    public static Encoder rightEncoder;
    public static ADXRS450_Gyro gyro;
    public static Spark ladder;
    public static Encoder ladderEncoder;
    public static DigitalInput ladderTopLimit;
    public static DigitalInput ladderBottomLimit;
    public static DigitalInput clawOpeningLimit;
    public static DigitalInput clawClosingLimit;
    public static DoubleSolenoid claw;
    public static AnalogInput qtiSensor;
    public static AnalogInput ultrasonicSensor;

    public static void init() {
        frontLeftWheel = new Spark(FRONT_LEFT_WHEEL_PORT);
        frontRightWheel = new Spark(FRONT_RIGHT_WHEEL_PORT);
        rearLeftWheel = new Spark(REAR_LEFT_WHEEL_PORT);
        rearRightWheel = new Spark(REAR_RIGHT_WHEEL_PORT);

        drive = new MecanumDrive(frontLeftWheel, frontRightWheel, rearLeftWheel, rearRightWheel);

        leftEncoder = new Encoder(LEFT_ENCODER_PORT_1, LEFT_ENCODER_PORT_2, false, Encoder.EncodingType.k1X);
        leftEncoder.setDistancePerPulse(-0.007266115676069);
        rightEncoder = new Encoder(RIGHT_ENCODER_PORT_1, RIGHT_ENCODER_PORT_2, false, Encoder.EncodingType.k1X);
        rightEncoder.setDistancePerPulse(-0.007604813285879);

        //gyro = new ADXRS450_Gyro();

        ladder = new Spark(LADDER_PORT);
        ladderEncoder = new Encoder(LADDER_ENCODER_PORT_1, LADDER_ENCODER_PORT_2, false, Encoder.EncodingType.k2X);
        ladderTopLimit = new DigitalInput(LADDER_TOP_LIMIT_PORT);
        ladderBottomLimit = new DigitalInput(LADDER_BOTTOM_LIMIT_PORT);

        claw = new DoubleSolenoid(CLAW_SOLENOID_PORT_1, CLAW_SOLENOID_PORT_2);
        clawActivator = new Servo(ACTIVATE_CLAW_SERVO);
        qtiSensor = new AnalogInput(QTI_SENSOR_PORT);
        ultrasonicSensor = new AnalogInput(ULTRASONIC_PORT);
    }
}
