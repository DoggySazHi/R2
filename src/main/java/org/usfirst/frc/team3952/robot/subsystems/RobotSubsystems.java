package org.usfirst.frc.team3952.robot.subsystems;

import org.usfirst.frc.team3952.robot.*;

public class RobotSubsystems
{
    private DriveTrain driveTrain;
    private PneumaticPiston pneumaticPiston;
    private ControlWheel controlWheel;

    private MainController mainController;
    private SecondaryController secondaryController;

    public RobotSubsystems(DriveTrain driveTrain, PneumaticPiston pneumaticPiston, ControlWheel controlWheel, MainController mainController, SecondaryController secondaryController)
    {
        this.driveTrain = driveTrain;
        this.pneumaticPiston = pneumaticPiston;
        this.controlWheel = controlWheel;
        this.mainController = mainController;
        this.secondaryController = secondaryController;
    }

    public DriveTrain getDriveTrain()
    {
        return driveTrain;
    }

    public PneumaticPiston getPneumaticPiston()
    {
        return pneumaticPiston;
    }

    public MainController getMainController()
    {
        return mainController;
    }

    public SecondaryController getSecondaryController()
    {
        return secondaryController;
    }

    public ControlWheel getControlWheel()
    {
        return controlWheel;
    }
}