package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.usfirst.frc.team3952.robot.subsystems.PneumaticPiston;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class ManualPneumatics extends CommandBase {
    
    private DoubleSolenoid.Value direction;
    private PneumaticPiston pneumaticPiston;

    public ManualPneumatics(RobotSubsystems subsystems, Value direction) {
        pneumaticPiston = subsystems.getPneumaticPiston();
        this.direction = direction;

        addRequirements(pneumaticPiston);
    }

    @Override
    public void initialize() { /* TODO: Maybe make the piston retract and then shut off */ }

    boolean isFinished = false;

    @Override
    public void execute() {
        if(direction == Value.kForward)
            pneumaticPiston.shoot();
        else
            pneumaticPiston.retract();
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
