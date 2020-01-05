package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import org.usfirst.frc.team3952.robot.subsystems.PneumaticPiston;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class TogglePiston extends CommandBase {

    private PneumaticPiston piston;

    public TogglePiston(RobotSubsystems subsystems) {
        this.piston = subsystems.getPneumaticPiston();
        addRequirements(piston);
    }

    @Override
    public void initialize() {
        piston.shoot();
    }

    @Override
    public void execute() {
        // Do... absolutely nothing.
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        piston.retract();
    }
}