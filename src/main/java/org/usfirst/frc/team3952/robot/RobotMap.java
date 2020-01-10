package org.usfirst.frc.team3952.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.util.Color;

//It's sparks, not talons lucas, ya dumbie

public class RobotMap {

    //PWM
    public static final int RIGHT_DRIVE_PORT = 0;
    public static final int LEFT_DRIVE_PORT = 1;
    public static final int PROJECTILE_EJECTION_PORT = 2;
    public static final int PROJECTILE_AIM_SERVO_PORT = 3;
    public static final int PROJECTILE_STORAGE_PORT = 4;
    public static final int TILT_LINEAR_ACTUATOR_PORT = 8;
    public static final int CONTROL_PANEL_SPINNER_PORT = 6;
    public static final int LIFT_MOTOR_PORT = 7;

    //CAN
    public static final int INTAKE_PORT = 0;
    public static final int INTAKE_TWO_PORT = 1;

    //ANALOG IN
    public static final int TILT_LINEAR_ACTUATOR_ENCODER = 1;

    //PNEUMATIC BOARD (PCM)

    // *chirp* *chirp*

    // Robot Values

    // Milliseconds before re-checking for controllers.
    public static final long CONTROLLER_CHECK_DELAY = 5000;

    // Control Panel Values
    public static final Color CP_RED = ColorMatch.makeColor(0.475, 0.370, 0.150);
    public static final Color CP_GREEN = ColorMatch.makeColor(0.190, 0.545, 0.260);
    public static final Color CP_BLUE = ColorMatch.makeColor(0.150, 0.455, 0.385);
    public static final Color CP_YELLOW = ColorMatch.makeColor(0.300, 0.550, 0.160);
    public static final Color[] WHEEL = new Color[]{CP_RED, CP_GREEN, CP_BLUE, CP_YELLOW};
    public static final int MIN_COUNT = 24;

    // Drive Train
    public static Spark leftDrive;
    public static Spark rightDrive;

    // Shooter Superstructure
    public static Talon projectileEjector;
    public static Servo projectileAimer;
    public static Talon projectileStorage;
    public static Talon projectileTilt;
    public static Talon controlPanelSpinner;

    // Misc
    public static Talon linearActuator;
    public static AnalogEncoder linearActuatorEncoder;
    public static VictorSPX intake;
    public static VictorSPX intake2;

    // Vision
    public static ColorSensorV3 colorSensor;

    public static void init() {
        leftDrive = new Spark(RIGHT_DRIVE_PORT);
        rightDrive = new Spark(LEFT_DRIVE_PORT);
        projectileTilt = new Talon(TILT_LINEAR_ACTUATOR_PORT);
        projectileAimer = new Servo(PROJECTILE_AIM_SERVO_PORT);
        projectileStorage = new Talon(PROJECTILE_STORAGE_PORT);
        projectileEjector = new Talon(PROJECTILE_EJECTION_PORT);
        controlPanelSpinner = new Talon(CONTROL_PANEL_SPINNER_PORT);

        linearActuator = new Talon(LIFT_MOTOR_PORT);
        linearActuatorEncoder = new AnalogEncoder(new AnalogInput(TILT_LINEAR_ACTUATOR_ENCODER));

        intake = new VictorSPX(INTAKE_PORT);
        intake2 = new VictorSPX(INTAKE_TWO_PORT);

        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    }
}
