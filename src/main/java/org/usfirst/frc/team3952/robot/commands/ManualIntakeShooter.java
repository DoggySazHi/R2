package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.RobotMap;
import org.usfirst.frc.team3952.robot.devices.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class ManualIntakeShooter extends CommandBase {

    private RobotSubsystems subsystems;

    public ManualIntakeShooter(RobotSubsystems subsystems) {
        this.subsystems = subsystems;

        addRequirements(subsystems.getIntakeShooter());
    }

    @Override
    public void initialize() {
        subsystems.getIntakeShooter().retract();
    }

    private boolean isIntaking = false;

    @Override
    public void execute() {
        IntakeShooter intakeShooter = subsystems.getIntakeShooter();
        SecondaryController secondaryController = subsystems.getSecondaryController();

        if (secondaryController.getRawButton(3))
            intakeShooter.intake(secondaryController.getRawButton(5));
        else if (secondaryController.getRawButton(4))
            intakeShooter.reject(secondaryController.getRawButton(5));
        else
            intakeShooter.stop();

        intakeShooter.setTiltServos(secondaryController.getHorizontalMovement());

        /*
        //TODO Remove.
        double distance = RobotMap.controlPanelUltraSonic.getRangeMM();
        System.out.println(distance + "mm = ");
        System.out.println(distance/100.0 + "cm");
        System.out.println(distance/1000.0 + "m");
         */
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
