package org.usfirst.frc.team3952.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;

import org.usfirst.frc.team3952.robot.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.PneumaticPiston;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class ManualPneumatics extends CommandBase {
    

    private PneumaticPiston lift;
    private SecondaryController secondaryController;

    public ManualPneumatics(RobotSubsystems subsystems) {
        lift = subsystems.getPneumaticPiston();
        secondaryController = subsystems.getSecondaryController();

        addRequirements(lift);
    }

    @Override
    public void initialize() { /* TODO: Maybe make the piston retract and then shut off */ }

    boolean isFinished = false;

    @Override
    public void execute() {
        if(secondaryController.getRawButton(3))
            lift.extend();
        else if(secondaryController.getRawButton(4))
            lift.retract();
        else
            lift.stop();
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
