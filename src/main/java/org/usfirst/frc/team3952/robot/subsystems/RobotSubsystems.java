package org.usfirst.frc.team3952.robot.subsystems;

import org.usfirst.frc.team3952.robot.MainController;
import org.usfirst.frc.team3952.robot.SecondaryController;

public class RobotSubsystems
{
    private DriveTrain driveTrain;
    private IntakeShooter intakeShooter;
    private ControlWheel controlWheel;
    private Intake intake;
    private ShooterSS shooter;

    private MainController mainController;
    private SecondaryController secondaryController;

    public RobotSubsystems(DriveTrain driveTrain, IntakeShooter intakeShooter, ControlWheel controlWheel,Intake intake,ShooterSS shooter, MainController mainController, SecondaryController secondaryController)
    {
        this.driveTrain = driveTrain;
        this.intakeShooter = intakeShooter;
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

    public IntakeShooter getIntakeShooter() {
        return intakeShooter;
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