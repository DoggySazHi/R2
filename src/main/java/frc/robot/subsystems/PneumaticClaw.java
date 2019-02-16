package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class PneumaticClaw extends Subsystem
{
    public DoubleSolenoid piston = RobotMap.claw;

    public boolean extended;

    public void initDefaultCommand() { }

    public void shoot() {
        if(!extended) {
            piston.set(DoubleSolenoid.Value.kForward);
            extended = true;
        }
    }

    public void retract() {
        if(extended) {
            piston.set(DoubleSolenoid.Value.kReverse);
            extended = false;
        }
    }

    public boolean isExtended()
    {
        return extended;
    }

    public void stop() {
        piston.set(DoubleSolenoid.Value.kOff);
    }
}