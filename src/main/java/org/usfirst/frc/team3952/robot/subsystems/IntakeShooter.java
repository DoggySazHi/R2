package org.usfirst.frc.team3952.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.RobotMap;

public class IntakeShooter extends SubsystemBase {
    public static final double INTAKE_SPEED = 1.0;
    public static final double REJECT_SPEED = 1.0;

    private DoubleSolenoid piston = RobotMap.liftDeploy;
    private VictorSPX intakeLeft = RobotMap.intake;
    private VictorSPX intakeRight = RobotMap.intake2;
    private Talon angleMotor = null;

    public void shoot() {
        if (piston != null)
            piston.set(DoubleSolenoid.Value.kForward);
    }

    public void retract() {
        if(piston != null)
            piston.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggle() {
        if(piston == null) return;
        if (piston.get() == DoubleSolenoid.Value.kForward)
            piston.set(DoubleSolenoid.Value.kReverse);
        else
            piston.set(DoubleSolenoid.Value.kForward);
    }

    public void intake() {
        intakeLeft.set(ControlMode.PercentOutput, -1.0 * INTAKE_SPEED);
        intakeRight.set(ControlMode.PercentOutput, INTAKE_SPEED);
    }

    public void reject() {
        intakeLeft.set(ControlMode.PercentOutput, REJECT_SPEED);
        intakeRight.set(ControlMode.PercentOutput, -1.0 * REJECT_SPEED);
    }

    public boolean isExtended() {
        return piston.get() == DoubleSolenoid.Value.kForward;
    }

    public void stop() {
        intakeLeft.set(ControlMode.PercentOutput, 0);
        intakeRight.set(ControlMode.PercentOutput, 0);
        if (piston != null)
            piston.set(DoubleSolenoid.Value.kReverse);
    }

    public void setAngleMotor(double speed) {
        angleMotor.set(speed);
    }
}