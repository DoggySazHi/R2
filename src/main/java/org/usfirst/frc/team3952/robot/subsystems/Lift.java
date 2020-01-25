package org.usfirst.frc.team3952.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.RobotMap;



public class Lift extends SubsystemBase {
   
   
    private DoubleSolenoid pneumaticLift = RobotMap.pneumaticLift;
    private VictorSPX liftMotor1 = RobotMap.liftMotor1;
    private VictorSPX liftMotor2= RobotMap.liftMotor2;

    public Lift () {
        pneumaticLift.set(Value.kReverse);
    }

   public void deployClaw(){
       pneumaticLift.set(Value.kForward);
   }

   public void deployed() {
       pneumaticLift.set(Value.kOff);
   }

   public void lift(double value){
       liftMotor1.set(ControlMode.PercentOutput, value);
       liftMotor2.set(ControlMode.PercentOutput, value);
   }

   
}