package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



import org.usfirst.frc.team3952.robot.RobotMap;

public class PneumaticPiston extends SubsystemBase
{
    public static final double INTAKE_SPEED = 1.0;
    public static final double REJECT_SPEED = 1.0;

    private DoubleSolenoid lift = RobotMap.liftDeploy;
    

    public void extend() {
        if(lift != null)
            lift.set(DoubleSolenoid.Value.kForward);
    }

    public void retract() {
        if(lift != null)
            lift.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggle() {
        if(lift == null) return;
        if (lift.get() == DoubleSolenoid.Value.kForward)
            lift.set(DoubleSolenoid.Value.kReverse);
        else
            lift.set(DoubleSolenoid.Value.kForward);
    }

   

    public boolean isExtended()
    {
        return lift.get() == DoubleSolenoid.Value.kForward;
    }

    public void stop() {
     
        if(lift != null)
            lift.set(DoubleSolenoid.Value.kReverse);
    }
}