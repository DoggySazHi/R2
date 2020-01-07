package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.RobotMap;

public class PneumaticPiston extends SubsystemBase
{
    public static final double INTAKE_SPEED = 0.5;
    public static final double REJECT_SPEED = 0.8;

    private DoubleSolenoid piston = RobotMap.claw;
    private Talon intakeLeft = RobotMap.intakeLeft;
    private Talon intakeRight = RobotMap.intakeRight;

    public void shoot() {
        if(piston != null)
            piston.set(DoubleSolenoid.Value.kForward);
    }

    public void retract() {
        if(piston != null)
            piston.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggle() {
        if (piston.get() == DoubleSolenoid.Value.kForward)
            piston.set(DoubleSolenoid.Value.kReverse);
        else
            piston.set(DoubleSolenoid.Value.kForward);
    }

    public void intake() {
        intakeLeft.set(INTAKE_SPEED);
        intakeRight.set(-1.0 * INTAKE_SPEED);
    }

    public void reject() {
        intakeLeft.set(-1.0 * REJECT_SPEED);
        intakeRight.set(REJECT_SPEED);
    }

    public boolean isExtended()
    {
        return piston.get() == DoubleSolenoid.Value.kForward;
    }

    public void stop() {
        intakeLeft.stopMotor();
        intakeRight.stopMotor();
        piston.set(DoubleSolenoid.Value.kReverse);
    }
}