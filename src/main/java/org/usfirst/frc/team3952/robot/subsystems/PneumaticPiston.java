package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.usfirst.frc.team3952.robot.*;

public class PneumaticPiston extends SubsystemBase
{
    private DoubleSolenoid piston = RobotMap.claw;

    public void shoot() {
        piston.set(DoubleSolenoid.Value.kForward);
    }

    public void retract() {
        piston.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggle() {
        if (piston.get() == DoubleSolenoid.Value.kForward)
            piston.set(DoubleSolenoid.Value.kReverse);
        else
            piston.set(DoubleSolenoid.Value.kForward);
    }

    public boolean isExtended()
    {
        return piston.get() == DoubleSolenoid.Value.kForward;
    }

    public void stop() {
        piston.set(DoubleSolenoid.Value.kOff);
    }
}