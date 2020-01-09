package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.usfirst.frc.team3952.robot.RobotMap;

public class Intake extends SubsystemBase
{
    
    public static final double INTAKE_SPEED = 1.0;
    public static final double REJECT_SPEED = 1.0;

    private VictorSPX intake = RobotMap.intake;

    public void intake() {
        intake.set(ControlMode.PercentOutput, -1.0 * INTAKE_SPEED);
        intake.set(ControlMode.PercentOutput, INTAKE_SPEED);
    }

    public void reject() {
        intake.set(ControlMode.PercentOutput, REJECT_SPEED);
        intake.set(ControlMode.PercentOutput, -1.0 * REJECT_SPEED);
    }

   
    public void stop() {
        intake.set(ControlMode.PercentOutput, 0);
        intake.set(ControlMode.PercentOutput, 0);
      
}
}