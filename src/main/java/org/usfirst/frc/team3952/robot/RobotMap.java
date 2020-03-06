package org.usfirst.frc.team3952.robot;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.util.Color;
import org.usfirst.frc.team3952.robot.devices.AnalogUltrasonic;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.usfirst.frc.team3952.robot.devices.CANPWMFallback.Mode.CAN;
import static org.usfirst.frc.team3952.robot.devices.CANPWMFallback.Mode.PWM;

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

    // Milliseconds before re-checking for motors.
    public static final long MOTOR_CHECK_DELAY = 1000;

    // Which file to load for the PseudoAutonomous.
    public static final String AUTONOMOUS_SCRIPT = "left.json";

    // Threshold for the gyro
    public static final double GYRO_THRESHOLD = 3;

    // Rotation speed when turning in TurnAngle.
    public static final double GYRO_ROTATION = 0.25;

    // Minimum acceleration required before counting it towards velocity/position.
    public static final double ACCELEROMETER_THRESHOLD_ACCELERATION = 0.02;

    // ---------------
    // Control Panel Values
    // ---------------

    // Colors represented by the color sensor.
    public static final Color CP_RED = ColorMatch.makeColor(0.475, 0.370, 0.150);
    public static final Color CP_GREEN = ColorMatch.makeColor(0.190, 0.545, 0.260);
    public static final Color CP_BLUE = ColorMatch.makeColor(0.150, 0.455, 0.385);
    public static final Color CP_YELLOW = ColorMatch.makeColor(0.300, 0.550, 0.160);

    //Sets the speed of the robot as it drives to the control wheel
    public static final double DRIVE_CONTROL_WHEEL_SPEED = 0.5;

    //Sets the distance from the wall that the robot should stop
    public static final double DISTANCE_TO_WALL = 50;

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

    // The time to wait after activating the control panel before spinning it.
    public static final long CP_ACTIVATION_TIMER = 1000;

    // ---------------
    // Shooter Values
    // ---------------

    // The amount of balls that can be stored in the shooter.
    public static final int MAX_BALL_STORAGE = 5;

    // The speed to run the storage motor at.
    public static final double STORAGE_MOTOR_SPEED = 0.5;

    // The speed to suck the balls in.
    public static final double INTAKE_SPEED = 0.80;

    // The speed for sucking using the roller.
    public static final double INTAKE_ROLLER_SPEED = 0.2;

    // The speed to shoot the balls out.
    public static final double REJECT_SPEED = 1.0;

    // The value to "trim" the servos at (center).
    public static final double TILT_SERVO_TRIM = -0.05;

    // ---------------
    // Climber Values
    // ---------------

    // Whether to invert the starting positions of the servos (should they start from the left or right?)
    public static final boolean FLIP_SERVO_START_POS = false;

    // Set the max (absolute) for the servos, as usually at 1.0, they pull so much current since they're fighting against themselves.
    public static final double SERVO_MAXPOWER = 0.8;

    // ---------------
    // DriveTrain Superstructure
    // ---------------

    // Drive Train (wheels are bundled together on the same PWM)
    public static CANPWMFallback leftDriveFront;
    public static CANPWMFallback rightDriveFront;
    public static CANPWMFallback leftDriveRear;
    public static CANPWMFallback rightDriveRear;

    // Actually the DriveTrain
    public static DifferentialDrive drive;

    // Sensors used for the DriveTrain, however are not used always.
    public static ADXRS450_Gyro gyro;
    public static BuiltInAccelerometer accelerometer;

    // ---------------
    // Control Panel Superstructure
    // ---------------

    // A color sensor to detect the current position of the Control Panel.
    public static ColorSensorV3 colorSensor;

    // A solenoid to push the mechanism up and down, so that we can travel under the control panel or operate it.
    public static DoubleSolenoid controlPanelSolenoid;

    // A distance sensor to calculate the distance between the control panel and the wall.
    public static AnalogUltrasonic controlPanelUltraSonic;

    // Spin the control panel using a wheel attached to this motor, powered by friction.
    public static CANPWMFallback controlPanelSpinner;

    // ---------------
    // IntakeShooter Superstructure
    // ---------------

    // Rotate the ball holder to switch to a different holder.
    public static CANPWMFallback intakeShooterStorage;

    // Tilt the shooter up or down for shooting, or to operate the control panel spinner. Operated via a linear actuator.
    public static CANPWMFallback intakeShooterTilt;

    // Aim the shooter a bit left or right to shoot balls at a slight angle.
    public static CANPWMFallback projectileAimer;

    // The two motors used to actually grab and shoot the balls from the outside.
    public static CANPWMFallback intake;
    public static CANPWMFallback intake2;
    public static CANPWMFallback intakeRoller;

    // Check if the spinner is on a valid position. This is the metal detector.
    public static DigitalInput spinnerLocked;

    // A solenoid to push the ball out.
    public static DoubleSolenoid ballShooter;

    // Buttons to check if the tilt is maxed out in both directions.
    public static DigitalInput hitTop;
    public static DigitalInput hitBottom;

    // ---------------
    // Climber Superstructure
    // ---------------

    // The motor used to climb the rope to the hanger (activation switch).
    public static CANPWMFallback liftMotor;
    public static CANPWMFallback liftMotor2;

    // The solenoid to activate the climber.
    public static CANPWMFallback climberActivator;

    public static void init() {
        CANPWMFallback.defaultMode = PWM;

        // PWM (Motors and Servos)
        leftDriveFront = new CANPWMFallback(0, -1, "Left Drive"); //BL 3
        rightDriveFront = new CANPWMFallback(1, -1, "Right Drive"); //G1
        leftDriveRear = new CANPWMFallback(2, -1, "Left Drive (Rear)"); //O4
        rightDriveRear = new CANPWMFallback(3, -1, "Right Drive (Rear)"); //B2
        SpeedControllerGroup left = new SpeedControllerGroup(leftDriveFront, leftDriveRear);
        SpeedControllerGroup right = new SpeedControllerGroup(rightDriveFront, rightDriveRear);

        drive = new DifferentialDrive(left, right);

        intakeRoller = new CANPWMFallback(4, -1, "Intake Roller");
        climberActivator = new CANPWMFallback(5, -1, "Climber Activator(s)", true).useFullRange(true);
        projectileAimer = new CANPWMFallback(6, -1, "Projectile Aimer", true).useFullRange(true);

        intakeShooterTilt = new CANPWMFallback(7, -1, "IntakeShooter Tilt");
        liftMotor = new CANPWMFallback(8, -1, "Lift Motor Left");
        liftMotor2 = new CANPWMFallback(9, -1, "Lift Motor Right");

        // DIO (Limit switches, Ultrasonic)
        spinnerLocked = new DigitalInput(1);
        hitTop = new DigitalInput(2);
        hitBottom = new DigitalInput(3);

        // AI (Encoders, Potentiometers, Photo Resistors)
        // chirp chirp (they don't exist)

        CANPWMFallback.defaultMode = CAN;
        CANPWMFallback.forceCANConnection = true;

        // CAN (Motors)
        intake = new CANPWMFallback(-1, 0, "Intake Left").withRamping(0.5);
        intake2 = new CANPWMFallback(-1, 1, "Intake Right").withRamping(0.5);
        intakeShooterStorage = new CANPWMFallback(-1, 2, "IntakeShooter Storage");
        controlPanelSpinner = new CANPWMFallback(-1, 3, "Control Panel Spinner").withRamping(0.5);

        // PCM (Pneumatic Pistons)
        controlPanelSolenoid = new DoubleSolenoid(2, 3);
        ballShooter = new DoubleSolenoid(0, 1);

        // Other sensors on I2C or SPI (Gyro, Color Sensor)
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        controlPanelUltraSonic = new AnalogUltrasonic(3);
        gyro = new ADXRS450_Gyro();
        accelerometer = new BuiltInAccelerometer(Accelerometer.Range.k2G);
        gyro.calibrate();
    }

    public static class SerializerPrimitive implements ExclusionStrategy
    {
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            var type = f.getDeclaredType();
            return !(type == String.class || type == int.class || type == long.class || type == double.class || type == boolean.class || type == Color.class);
        }
    }

    static {
        /*
        GsonBuilder gsonBuilder  = new GsonBuilder();
        gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);
        gsonBuilder.setExclusionStrategies( new SerializerPrimitive() );
        Gson gsonInstance = gsonBuilder.create();

        File[] files = Filesystem.getDeployDirectory().listFiles();

        File jsonFile = null;
        for(File f : files)
            if(f.getName().equals("RobotMap.json"))
            {
                jsonFile = f;
                break;
            }

        Path path = Paths.get(Filesystem.getDeployDirectory().getAbsolutePath().concat("RobotMap.json"));
        if(jsonFile == null)
        {
            String data = gsonInstance.toJson(RobotMap.class, RobotMap.class);
            try(BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8))
            {
                writer.write(data);
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            try(BufferedReader reader = Files.newBufferedReader(path))
            {
                StringBuilder finalInput = new StringBuilder();
                while (true)
                {
                    String s = reader.readLine();
                    if(s == null || s.equals(""))
                        break;
                    finalInput.append(s);
                }
                RobotMap r = gsonInstance.fromJson(finalInput.toString(), RobotMap.class);
                
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
         */
    }
}
