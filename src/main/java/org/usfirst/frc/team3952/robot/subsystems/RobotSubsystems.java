package org.usfirst.frc.team3952.robot.subsystems;

import org.usfirst.frc.team3952.robot.MainController;
import org.usfirst.frc.team3952.robot.SecondaryController;

public class RobotSubsystems
{
    private DriveTrain driveTrain;
    private IntakeShooter intakeShooter;
    private ControlWheel controlWheel;

    private MainController mainController;
    private SecondaryController secondaryController;

    public RobotSubsystems(DriveTrain driveTrain, IntakeShooter intakeShooter, ControlWheel controlWheel, MainController mainController, SecondaryController secondaryController) {
        this.driveTrain = driveTrain;
        this.intakeShooter = intakeShooter;
        this.controlWheel = controlWheel;
        this.mainController = mainController;
        this.secondaryController = secondaryController;
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
}