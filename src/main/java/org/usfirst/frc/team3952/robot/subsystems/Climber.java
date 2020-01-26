package org.usfirst.frc.team3952.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team3952.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The subsystem to go up and down to hang on the activator.
 */
public class Climber extends SubsystemBase {
    private DoubleSolenoid climberActivator = RobotMap.climberActivator;
    private VictorSPX liftMotor = RobotMap.liftMotor;
    private VictorSPX liftMotor2 = RobotMap.liftMotor2;
    private DigitalInput hitTop = RobotMap.hitTop;

    public Climber() {
        climberActivator.set(Value.kReverse);
    }

   public void deploy(){
       climberActivator.set(Value.kForward);
   }

   public void postDeploy() {
       climberActivator.set(Value.kOff);
   }

    public void retract() {
        climberActivator.set(Value.kOff);
    }

   public void lift(double value){
       liftMotor.set(ControlMode.PercentOutput, value);
       liftMotor2.set(ControlMode.PercentOutput, value);
   }

   public boolean hasHitTop()
   {
       return hitTop.get();
   }
}
