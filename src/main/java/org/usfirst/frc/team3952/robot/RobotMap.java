package org.usfirst.frc.team3952.robot;


import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.util.Color;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;

import static org.usfirst.frc.team3952.robot.devices.CANPWMFallback.Mode.CAN;
import static org.usfirst.frc.team3952.robot.devices.CANPWMFallback.Mode.PWM;

//It's sparks, not CANPWMFallbacks lucas, ya dumbie

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

    // How "close" the color sensor should read to make sure it's "sure".
    public static final double MIN_COLOR_CONFIDENCE = 0.8;

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

    // The speed to suck the balls in.
    public static final double INTAKE_SPEED = 1.0;

    // The speed to shoot the balls out.
    public static final double REJECT_SPEED = 1.0;

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

    // Whether to invert the servos (are they facing opposite directions?)
    public static final boolean INVERT_CLIMBER_SERVOS = false;

    // Whether to invert the starting positions of the servos (should they start from the left or right?)
    public static final boolean FLIP_SERVO_START_POS = false;

    // Set the max (absolute) for the servos, as usually at 1.0, they pull so much current since they're fighting against themselves.
    public static final double SERVO_MAXPOWER = 0.8;

    // ---------------
    // DriveTrain Superstructure
    // ---------------

    // Drive Train (wheels are bundled together on the same PWM)
    public static CANPWMFallback leftDrive;
    public static CANPWMFallback rightDrive;

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
    public static CANPWMFallback controlPanelSpinner;

    // ---------------
    // IntakeShooter Superstructure
    // ---------------

    // Used to move balls in and out of the ball holder.
    public static CANPWMFallback projectileEjector;

    // Rotate the ball holder to switch to a different holder.
    public static CANPWMFallback projectileStorage;

    // Tilt the shooter up or down for shooting, or to operate the control panel spinner. Operated via a linear actuator.
    public static CANPWMFallback projectileTilt;

    // Get the current position/angle of the shooter
    public static AnalogEncoder linearActuatorEncoder;

    // Aim the shooter a bit left or right to shoot balls at a slight angle.
    public static Servo projectileAimer;

    // The two motors used to actually grab and shoot the balls from the outside.
    public static CANPWMFallback intake;
    public static CANPWMFallback intake2;

    // Check if the spinner is on a valid position.
    public static DigitalInput spinnerLocked;

    // A solenoid to push the ball out.
    public static DoubleSolenoid ballShooter;

    // ---------------
    // Climber Superstructure
    // ---------------

    // The two motors used to climb the rope to the hanger (activation switch).
    public static CANPWMFallback liftMotor;
    public static CANPWMFallback liftMotor2;

    // The solenoid to activate the climber.
    public static Servo climberActivator;
    public static Servo climberActivator2;

    // The button to check if it has hit the top.
    public static DigitalInput hitTop;

    public static void init() {
        CANPWMFallback.defaultMode = PWM;
        // PWM (Motors and Servos)
        leftDrive = new CANPWMFallback(1, -1, "Left Drive");
        rightDrive = new CANPWMFallback(0, -1, "Right Drive"); 
        projectileEjector = new CANPWMFallback(2, -1, "Projectile Ejector"); 
        projectileAimer = new Servo(3);
        projectileStorage = new CANPWMFallback(4, -1, "Projectile Storage");  
        projectileTilt = new CANPWMFallback(5, -1, "Projectile Tilt"); 
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
        intake = new CANPWMFallback(-1, 0, "Intake Left").withRamping(0.5);
        intake2 = new CANPWMFallback(-1, 1, "Intake Right").withRamping(0.5); 
        liftMotor = new CANPWMFallback(-1, 2, "Lift Motor Left").withRamping(1);
        liftMotor2 = new CANPWMFallback(-1, 3, "Lift Motor Right").withRamping(1); 
        controlPanelSpinner = new CANPWMFallback(-1, 4, "Control Panel Spinner").withRamping(0.5); 


        // PCM (Pneumatic Pistons)
        ballShooter = new DoubleSolenoid(0, 1);
        controlPanelSolenoid = new DoubleSolenoid(2, 3);

        // Other sensors on I2C or SPI (Gyro, Color Sensor)
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    }
}
