/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3952.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.usfirst.frc.team3952.robot.commands.ManualDrive;
import org.usfirst.frc.team3952.robot.commands.ManualPneumatics;
import org.usfirst.frc.team3952.robot.commands.ManualTurn;
import org.usfirst.frc.team3952.robot.subsystems.ControlWheel;
import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import java.time.Duration;
import java.time.Instant;

public class Robot extends TimedRobot {
    private DriveTrain driveTrain;
    private IntakeShooter intakeShooter;
    private ControlWheel controlWheel;

    private MainController mainController;
    private SecondaryController secondaryController;

    private RobotSubsystems subsystems;

    private boolean mainControllerInit;
    private boolean secondaryControllerInit;
    private Instant checkControllerTime;

    private final long CHECK_DELAY = 5000;

    @Override
    public void robotInit() {
        RobotMap.init();
        driveTrain = new DriveTrain();
        intakeShooter = new IntakeShooter();
        controlWheel = new ControlWheel();

        subsystems = new RobotSubsystems(driveTrain, intakeShooter, controlWheel, mainController, secondaryController);

        try
        {
            mainController = new MainController(new Joystick(0), subsystems);
            mainControllerInit = true;
        }
        catch(Exception ex)
        {
            System.out.println("The main controller has failed to initialize. See logs.\n" +
                "Have you checked if the controller is plugged in, and has the correct joystick number in the Driver Station?\n" + 
                "Please restart the RoboRIO after these errors are fixed.");
        }

        try
        {
            secondaryController = new SecondaryController(new Joystick(1), subsystems);
            secondaryControllerInit = true;
        } catch (Exception ex) {
            System.out.println("The ladder controller has failed to initialize. See logs.\n" +
                    "Have you checked if the controller is plugged in, and has the correct joystick number in the Driver Station?\n" +
                    "Please restart the RoboRIO after these errors are fixed.");
        }

        subsystems = new RobotSubsystems(driveTrain, intakeShooter, controlWheel, mainController, secondaryController);
        driveTrain.setDefaultCommand(new ManualDrive(subsystems));
        intakeShooter.retract();
        intakeShooter.setDefaultCommand(new ManualPneumatics(subsystems));
        controlWheel.setDefaultCommand(new ManualTurn(subsystems));

        // requires pi (see other code)
        /*
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(640, 480);
        */
    }

    @Override
    public void robotPeriodic() {
        if(checkControllerTime == null)
            checkControllerTime = Instant.now();
        else if(!(mainControllerInit && secondaryControllerInit))
            if(Duration.between(checkControllerTime, Instant.now()).toMillis() > CHECK_DELAY)
            {
                if(!mainControllerInit)
                {
                    try
                    {
                        mainController = new MainController(new Joystick(0), subsystems);
                        mainControllerInit = true;
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Failed to connect main controller. Retrying later.");
                    }
                }
                if(!secondaryControllerInit)
                {
                    try
                    {
                        secondaryController = new SecondaryController(new Joystick(1), subsystems);
                        secondaryControllerInit = true;
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Failed to connect ladder controller. Retrying later.");
                    }
                }
                checkControllerTime = Instant.now();
            }
        CommandScheduler.getInstance().run();
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
