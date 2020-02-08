package org.usfirst.frc.team3952.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.util.Color;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;

import static org.usfirst.frc.team3952.robot.devices.CANPWMFallback.Mode.None;
import static org.usfirst.frc.team3952.robot.devices.CANPWMFallback.Mode.CAN;
import static org.usfirst.frc.team3952.robot.devices.CANPWMFallback.Mode.PWM;

//It's sparks, not talons lucas, ya dumbie

/**
 * A listing of core devices and values that are used on the robot.
 * All of these are set up in the init() method.
 */
public class RobotMap {
    // ---------------
    // Robot Values
    // ---------------

    // Milliseconds before re-checking for controllers.
    public static final long CONTROLLER_CHECK_DELAY = 5000;

    // ---------------
    // Control Panel Values
    // ---------------

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
    public static final double CW_SPEED_FAST = 0.3;
    public static final double CW_SPEED_SLOW = 0.05;

    // Tiles to start slowing down, if not using a linear ramping function.
    public static final int SLOWDOWN_TILES = 5;

    // Holds the possible directions to rotate the storage mechanism. May be used for other motor functions.
    public enum Direction { Left, Right }

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

    // The time to wait after activating the control panel before spinning it.
    public static final long CP_ACTIVATION_TIMER = 1000;

    // ---------------
    // Shooter Values
    // ---------------

    // The amount of balls that can be stored in the shooter.
    public static final int MAX_BALL_STORAGE = 5;

    // The speed to run the storage motor at.
    public static final double STORAGE_MOTOR_SPEED = 0.4;

    // ---------------
    // Climber Values
    // ---------------

    // The speed of how fast to climb.
    public static final double CLIMB_MOTOR_SPEED = 0.75;

    // The speed of how fast to descend. Should be opposite in polarity to the climbing speed.
    public static final double DESCEND_MOTOR_SPEED = -0.50;

    // The time to wait after activating the climber to actually climbing (in millseconds).
    public static final long CLIMBER_ACTIVATION_TIMER = 2000;

    // The time to wait after climbing down to deactivate the climber (in millseconds).
    public static final long CLIMBER_DEACTIVATION_TIMER = 3000;

    // The server

    // ---------------
    // DriveTrain Superstructure
    // ---------------

    // Drive Train (wheels are bundled together on the same PWM)
    public static Spark leftDrive;
    public static Spark rightDrive;

    // ---------------
    // Control Panel Superstructure
    // ---------------

    // A color sensor to detect the current position of the Control Panel.
    public static ColorSensorV3 colorSensor;

    // A solenoid to push the mechanism up and down, so that we can travel under the control panel or operate it.
    public static DoubleSolenoid controlPanelSolenoid;

    // A distance sensor to calculate the distance between the control panel and the wall.
    public static Ultrasonic controlPanelUltraSonic;

    // Spin the control panel using a wheel attached to this motor, powered by friction.
    public static VictorSPX controlPanelSpinner;

    // ---------------
    // IntakeShooter Superstructure
    // ---------------

    // Used to move balls in and out of the ball holder.
    public static Talon projectileEjector;

    // Rotate the ball holder to switch to a different holder.
    public static Talon projectileStorage;

    // Tilt the shooter up or down for shooting, or to operate the control panel spinner. Operated via a linear actuator.
    public static Talon projectileTilt;

    // Get the current position/angle of the shooter
    public static AnalogEncoder linearActuatorEncoder;

    // Aim the shooter a bit left or right to shoot balls at a slight angle.
    public static Servo projectileAimer;

    // The two motors used to actually grab and shoot the balls from the outside.
    public static VictorSPX intake;
    public static VictorSPX intake2;

    // Check if the spinner is on a valid position.
    public static DigitalInput spinnerLocked;

    // A solenoid to push the ball out.
    public static DoubleSolenoid ballShooter;

    // ---------------
    // Climber Superstructure
    // ---------------

    // The two motors used to climb the rope to the hanger (activation switch).
    public static VictorSPX liftMotor;
    public static VictorSPX liftMotor2;

    // The solenoid to activate the climber.
    public static Servo climberActivator;
    public static Servo climberActivator2;

    // The button to check if it has hit the top.
    public static DigitalInput hitTop;

    public static void init() {
        CANPWMFallback.defaultMode = PWM;
        // PWM (Motors and Servos)
        leftDrive = new Spark(1);
        rightDrive = new Spark(0);
        projectileEjector = new Talon(2);
        projectileAimer = new Servo(3);
        projectileStorage = new Talon(4);
        projectileTilt = new Talon(5);
        climberActivator = new Servo(6);
        climberActivator2 = new Servo(7);

        // DIO (Limit switches, Ultrasonic)
        spinnerLocked = new DigitalInput(0);
        controlPanelUltraSonic = new Ultrasonic(1, 2);
        hitTop = new DigitalInput(3);

        // AI (Encoders, Potentiometers, Photo Resistors)
        if(RobotBase.isReal()) {
            linearActuatorEncoder = new AnalogEncoder(new AnalogInput(0));
        }

        CANPWMFallback.defaultMode = CAN;
        // CAN (Motors)
        intake = new VictorSPX(0);
        intake2 = new VictorSPX(1);
        liftMotor = new VictorSPX(2);
        liftMotor2 = new VictorSPX(3);
        controlPanelSpinner = new VictorSPX(4);

        // PCM (Pneumatic Pistons)
        ballShooter = new DoubleSolenoid(0, 1);
        controlPanelSolenoid = new DoubleSolenoid(2, 3);

        // Other sensors on I2C or SPI (Gyro, Color Sensor)
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    }
}
