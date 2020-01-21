package org.usfirst.frc.team3952.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.usfirst.frc.team3952.robot.commands.ManualDrive;
import org.usfirst.frc.team3952.robot.commands.ManualIntakeShooter;
import org.usfirst.frc.team3952.robot.commands.ManualTurn;
import org.usfirst.frc.team3952.robot.subsystems.ControlWheel;
import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import java.time.Duration;
import java.time.Instant;

import static org.usfirst.frc.team3952.robot.RobotMap.CONTROLLER_CHECK_DELAY;

public class Robot extends TimedRobot {
    private RobotSubsystems subsystems;

    private boolean mainControllerInit;
    private boolean secondaryControllerInit;
    private Instant checkControllerTime;

    @Override
    public void robotInit() {
        RobotMap.init();

        DriveTrain driveTrain = new DriveTrain();
        IntakeShooter intakeShooter = new IntakeShooter();
        ControlWheel controlWheel = new ControlWheel();

        // Dummy controllers.
        MainController mainController = new MainController(null, null);
        SecondaryController secondaryController = new SecondaryController(null, null);

        subsystems = new RobotSubsystems();
        subsystems.setMainController(mainController);
        subsystems.setSecondaryController(secondaryController);

        // Actually sets them to real controllers.

        initMainController();
        initSecondaryController();

        subsystems.setDriveTrain(driveTrain);
        subsystems.setIntakeShooter(intakeShooter);
        subsystems.setControlWheel(controlWheel);

        driveTrain.setDefaultCommand(new ManualDrive(subsystems));
        controlWheel.setDefaultCommand(new ManualTurn(subsystems));
        intakeShooter.setDefaultCommand(new ManualIntakeShooter(subsystems));
    }

    @Override
    public void robotPeriodic() {
        checkControllers();
        CommandScheduler.getInstance().run();
    }

    private void initCameras() {
        // For testing purposes, add this to the end of robotInit()
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(640, 480);
    }

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
    public void autonomousInit() { /* TODO autoalign should be .schedule()ed */ }

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
    public void testPeriodic() { }
}
