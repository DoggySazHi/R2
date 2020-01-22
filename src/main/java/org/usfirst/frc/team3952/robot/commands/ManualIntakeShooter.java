package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;
import static org.usfirst.frc.team3952.robot.RobotMap.*;

public class ManualIntakeShooter extends CommandBase {

    private RobotSubsystems subsystems;

    public ManualIntakeShooter(RobotSubsystems subsystems) {
        this.subsystems = subsystems;

        addRequirements(subsystems.getIntakeShooter());
    }

    @Override
    public void initialize() {
    }

    private boolean isIntaking = false;
    private boolean chuteReady = true;

    @Override
    public void execute() {
        IntakeShooter intakeShooter = subsystems.getIntakeShooter();
        SecondaryController secondaryController = subsystems.getSecondaryController();

        if (secondaryController.getRawButton(3))
            intakeShooter.reject();
        else if (secondaryController.getRawButton(4))
            intakeShooter.intake();
        else
            intakeShooter.stop();

        intakeShooter.setTiltServos(secondaryController.getLateralMovement());

        if(intakeShooter.getEnableSwitch() && intakeShooter.getBallsStored() < MAX_BALL_STORAGE) {
            if(isIntaking) {
                intakeShooter.intake();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
