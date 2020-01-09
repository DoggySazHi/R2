package org.usfirst.frc.team3952.robot.subsystems;

import org.usfirst.frc.team3952.robot.*;

public class RobotSubsystems
{
    private DriveTrain driveTrain;
    private PneumaticPiston pneumaticPiston;
    private ControlWheel controlWheel;
    private Intake intake;
    private ShooterSS shooter;

    private MainController mainController;
    private SecondaryController secondaryController;

    public RobotSubsystems(DriveTrain driveTrain, PneumaticPiston pneumaticPiston, ControlWheel controlWheel,Intake intake,ShooterSS shooter, MainController mainController, SecondaryController secondaryController)
    {
        this.driveTrain = driveTrain;
        this.pneumaticPiston = pneumaticPiston;
        this.controlWheel = controlWheel;
        this.mainController = mainController;
        this.secondaryController = secondaryController;
        this.intake = intake;
        this.shooter = shooter;
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

    public Intake intake(){
        return intake;
    }

    public ShooterSS shooter(){
        return shooter;
    }
}