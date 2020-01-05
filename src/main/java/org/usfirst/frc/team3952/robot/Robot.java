/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3952.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.usfirst.frc.team3952.robot.subsystems.*;

import java.time.Duration;
import java.time.Instant;

import org.usfirst.frc.team3952.robot.commands.*;

public class Robot extends TimedRobot {
    private DriveTrain driveTrain;
    private PneumaticPiston pneumaticPiston;
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
        pneumaticPiston = new PneumaticPiston();
        controlWheel = new ControlWheel();

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
        }
        catch(Exception ex)
        {
            System.out.println("The ladder controller has failed to initialize. See logs.\n" +
                "Have you checked if the controller is plugged in, and has the correct joystick number in the Driver Station?\n" + 
                "Please restart the RoboRIO after these errors are fixed.");
        }

        subsystems = new RobotSubsystems(driveTrain, pneumaticPiston, controlWheel, mainController, secondaryController);
        driveTrain.setDefaultCommand(new ManualDrive(subsystems));
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
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() { }

    @Override
    public void autonomousPeriodic() { Scheduler.getInstance().run(); }

    @Override
    public void teleopInit() { }

    @Override
    public void teleopPeriodic() { Scheduler.getInstance().run(); }

    @Override
    public void testInit() {}

    @Override
    public void testPeriodic() { Scheduler.getInstance().run(); }
}
