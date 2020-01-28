package org.usfirst.frc.team3952.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team3952.robot.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The subsystem to shoot the claw in order to hang on the clothing hanger :)
 */
public class Climber extends SubsystemBase {
    private VictorSPX liftMotor = RobotMap.liftMotor;
    private VictorSPX liftMotor2 = RobotMap.liftMotor2;
    private DigitalInput hitTop = RobotMap.hitTop;
    private Servo climberActivator = RobotMap.climberActivator;
    private Servo climberActivator2 = RobotMap.climberActivator2;

    public Climber() {
        retract();
    }

   public void deploy(){
       climberActivator.set(-1.0);
       climberActivator2.set(1.0);
   }

   public void postDeploy() {
        //d  e  l  e  t  e  d   f  o  r   c  o  m        patab         ility      
    }

    public void retract() {
        climberActivator.set(1.0);
        climberActivator2.set(-1.0);
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
