package org.usfirst.frc.team3952.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.usfirst.frc.team3952.robot.commands.*;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;
import org.usfirst.frc.team3952.robot.devices.MainController;
import org.usfirst.frc.team3952.robot.devices.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import java.time.Instant;

import static org.usfirst.frc.team3952.robot.RobotMap.CONTROLLER_CHECK_DELAY;

/**
 * The actual main of the robot.
 */
public class Robot extends TimedRobot {
    private RobotSubsystems subsystems;

    private boolean mainControllerInit;
    private boolean secondaryControllerInit;
    private Instant checkControllerTime;

    @Override
    public void robotInit() {
        RobotMap.init();
        NetworkTableMap.init();
        checkCredits();

        DriveTrain driveTrain = new DriveTrain();
        IntakeShooter intakeShooter = new IntakeShooter();
        ControlWheel controlWheel = new ControlWheel();
        Climber climber = new Climber();

        // Dummy controllers.
        MainController mainController = new MainController(null, null);
        SecondaryController secondaryController = new SecondaryController(null, null);

        subsystems = new RobotSubsystems();
        subsystems.setMainController(mainController);
        subsystems.setSecondaryController(secondaryController);
        subsystems.setDriveTrain(driveTrain);
        subsystems.setIntakeShooter(intakeShooter);
        subsystems.setControlWheel(controlWheel);
        subsystems.setClimber(climber);

        // Actually sets them to real controllers.

        initMainController();
        initSecondaryController();

        driveTrain.setDefaultCommand(new ManualDrive(subsystems));
        controlWheel.setDefaultCommand(new ManualTurn(subsystems));
        intakeShooter.setDefaultCommand(new ManualIntakeShooter(subsystems));
        climber.setDefaultCommand(new ManualClimber(subsystems));
    }

    /**
     * More of a test, it reads a file in the RoboRIO and then spits it out.
     */
    private void checkCredits() {
        File[] deployFiles = Filesystem.getDeployDirectory().listFiles();
        if (deployFiles == null) return;
        for (File f : deployFiles)
            if (f.getName().contains("credits")) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String data = br.readLine();
                    while (data != null) {
                        System.out.println(data);
                        data = br.readLine();
                    }
                    br.close();
                } catch (Exception e) {
                    // screw this
                    return;
                }
            }
    }

    @Override
    public void robotPeriodic() {
        checkControllers();
        CANPWMFallback.reInit();
        CommandScheduler.getInstance().run();
    }

    /**
     * Enable a USB camera on the RoboRIO itself.
     */
    private void initCameras() {
        // For testing purposes, add this to the end of robotInit()
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(640, 480);
    }

    /**
     * Check if the controllers are connected to the Driver Station, and then init them if necessary.
     * This is usually called periodically in robotPeriodic().
     */
    private void checkControllers() {
        if (checkControllerTime == null)
            checkControllerTime = Instant.now();
        else if (!mainControllerInit || !secondaryControllerInit)
            if (Duration.between(checkControllerTime, Instant.now()).toMillis() > CONTROLLER_CHECK_DELAY) {
                if (!mainControllerInit)
                    initMainController();
                if (!secondaryControllerInit)
                    initSecondaryController();
                checkControllerTime = Instant.now();
            }
    }

    /**
     * (Re)Initialize the main controller.
     */
    private void initMainController() {
        try {
            subsystems.setMainController(new MainController(new Joystick(0), subsystems));
            mainControllerInit = true;
        } catch (Exception ex) {
            System.out.println("The main controller has failed to initialize. See logs.\n" +
                    "Have you checked if the controller is plugged in, and has the correct joystick number in the Driver Station?\n" +
                    "Please restart the RoboRIO after these errors are fixed.");
            ex.printStackTrace();
        }
    }

    /**
     * (Re)Initialize the secondary controller.
     */
    private void initSecondaryController() {
        try {
            subsystems.setSecondaryController(new SecondaryController(new Joystick(1), subsystems));
            secondaryControllerInit = true;
        } catch (Exception ex) {
            System.out.println("The ladder controller has failed to initialize. See logs.\n" +
                    "Have you checked if the controller is plugged in, and has the correct joystick number in the Driver Station?\n" +
                    "Please restart the RoboRIO after these errors are fixed.");
            ex.printStackTrace();
        }
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() { }

    @Override
    public void autonomousInit() {
        CommandScheduler.getInstance().schedule(new PseudoAutonomous(subsystems));
    }

    @Override
    public void autonomousPeriodic() {  }

    @Override
    public void teleopInit() { }

    @Override
    public void teleopPeriodic() { }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("GyroAccelTest");
        table.getEntry("AccelX").setDouble(RobotMap.accelerometer.getX());
        table.getEntry("AccelY").setDouble(RobotMap.accelerometer.getY());
        table.getEntry("AccelZ").setDouble(RobotMap.accelerometer.getZ());
        table.getEntry("GyroAngle").setDouble(RobotMap.gyro.getAngle());
        table.getEntry("GyroRate").setDouble(RobotMap.gyro.getRate());
    }
}
