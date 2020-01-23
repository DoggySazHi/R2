package org.usfirst.frc.team3952.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.util.Color;

//It's sparks, not talons lucas, ya dumbie

public class RobotMap {
    // Robot Values

    // Milliseconds before re-checking for controllers.
    public static final long CONTROLLER_CHECK_DELAY = 5000;

    // Control Panel Values

    // Colors represented by the color sensor.
    public static final Color CP_RED = ColorMatch.makeColor(0.475, 0.370, 0.150);
    public static final Color CP_GREEN = ColorMatch.makeColor(0.190, 0.545, 0.260);
    public static final Color CP_BLUE = ColorMatch.makeColor(0.150, 0.455, 0.385);
    public static final Color CP_YELLOW = ColorMatch.makeColor(0.300, 0.550, 0.160);

    // The color pattern of the Control Panel wheel.
    public static final Color[] WHEEL = new Color[]{CP_RED, CP_GREEN, CP_BLUE, CP_YELLOW};

    // The amount of times to spin before actually finding the right color.
    public static final int MIN_COUNT = 24;

    // Speeds to set the motors when running, if not using a linear ramping function.
    public static final double CW_SPEED_FAST = 0.5;
    public static final double CW_SPEED_SLOW = 0.2;

    // Tiles to start slowing down, if not using a linear ramping function.
    public static final int SLOWDOWN_TILES = 5;

    // Holds the possible positions for the shooter's angle with given names.
    public enum Position {
        Intake (0.0),
        Shooting (40.0),
        ControlPanel (100.0);

        private double distance;

        Position(double distance)
        {
            this.distance = distance;
        }

        public double getDistance()
        {
            return distance;
        }
    }

    // Shooter Values

    // The amount of balls that can be stored in the shooter.
    public static final int MAX_BALL_STORAGE = 5;

    // Drive Train (wheels are bundled together on the same PWM)
    public static Spark leftDrive;
    public static Spark rightDrive;

    // Shooter Superstructure

    // Used to move balls in and out of the ball holder.
    public static Talon projectileEjector;

    // Rotate the ball holder to switch to a different holder.
    public static Talon projectileStorage;
    public static Talon projectileTilt;
    public static Servo projectileAimer;
    public static Talon controlPanelSpinner;

    public static DigitalInput enableShooter;
    public static DigitalInput disableShooter;
    public static DigitalInput spinnerLocked;

    // Misc
    public static AnalogEncoder linearActuatorEncoder;
    public static VictorSPX intake;
    public static VictorSPX intake2;

    // Vision
    public static ColorSensorV3 colorSensor;

    public static void init() {
        // PWM (Motors and Servos)

        leftDrive = new Spark(1);
        rightDrive = new Spark(0);
        projectileEjector = new Talon(2);
        projectileAimer = new Servo(3);
        projectileStorage = new Talon(4);
        projectileTilt = new Talon(5);
        controlPanelSpinner = new Talon(6);

        // DIO (Limit switches)

        enableShooter = new DigitalInput(0);
        disableShooter = new DigitalInput(1);
        spinnerLocked = new DigitalInput(2);


        linearActuatorEncoder = new AnalogEncoder(new AnalogInput(0));

        // CAN

        intake = new VictorSPX(0);
        intake2 = new VictorSPX(1);

        // Other sensors

        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    }
}
