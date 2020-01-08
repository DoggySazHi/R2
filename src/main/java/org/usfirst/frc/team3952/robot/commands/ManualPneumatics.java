package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.usfirst.frc.team3952.robot.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.PneumaticPiston;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class ManualPneumatics extends CommandBase {
    
    private DoubleSolenoid.Value direction;

    private PneumaticPiston pneumaticPiston;
    private SecondaryController secondaryController;

    public ManualPneumatics(RobotSubsystems subsystems) {
        pneumaticPiston = subsystems.getPneumaticPiston();
        secondaryController = subsystems.getSecondaryController();

        addRequirements(pneumaticPiston);
    }

    @Override
    public void initialize() { /* TODO: Maybe make the piston retract and then shut off */ }

    boolean isFinished = false;

    @Override
    public void execute() {
        if(secondaryController.getRawButton(3))
            pneumaticPiston.reject();
        else if(secondaryController.getRawButton(4))
            pneumaticPiston.intake();
        else
            pneumaticPiston.stop();
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
