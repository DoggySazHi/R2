package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3952.robot.*;

public class PneumaticClaw extends Subsystem
{
    public DoubleSolenoid piston = RobotMap.claw;

    public boolean extended;
    
    public NetworkTableEntry netExtended = Robot.nTable.getEntry("Claw Extended?");

    public void initDefaultCommand() { }

    public void shoot() {
        if(!extended) {
            piston.set(DoubleSolenoid.Value.kForward);
            extended = true;
        }
        netExtended.setBoolean(extended);
    }

    public void retract() {
        if(extended) {
            piston.set(DoubleSolenoid.Value.kReverse);
            extended = false;
        }
        netExtended.setBoolean(extended);
    }

    public boolean isExtended()
    {
        return extended;
    }

    public void stop() {
        piston.set(DoubleSolenoid.Value.kOff);
    }
}