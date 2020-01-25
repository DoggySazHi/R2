package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.MainController;
import org.usfirst.frc.team3952.robot.SecondaryController;

import java.util.Set;


public class RobotSubsystems
{
    private DriveTrain driveTrain;
    private IntakeShooter intakeShooter;
    private ControlWheel controlWheel;
    private Climber climber;

    private MainController mainController;
    private SecondaryController secondaryController;

    /*public boolean subsystemIsUsed(SubsystemBase subsystem) {
        return subsystem.getCurrentCommand() != null;
    }*/

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

    public void setDriveTrain(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
    }

    public void setIntakeShooter(IntakeShooter intakeShooter) {
        this.intakeShooter = intakeShooter;
    }

    public void setControlWheel(ControlWheel controlWheel) {
        this.controlWheel = controlWheel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setSecondaryController(SecondaryController secondaryController) {
        this.secondaryController = secondaryController;
    }

    public void setClimber(Climber climber) {
        this.climber = climber;
    }

    public Climber getClimber() {
        return climber;
    }
}
