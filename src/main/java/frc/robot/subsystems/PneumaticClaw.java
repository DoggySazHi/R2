package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class PneumaticClaw
{

    public class PneumaticClaw extends Subsystem
    {
        public DoubleSolenoid piston = RobotMap.p;

        public boolean extended;

        public void initDefaultCommand() {
            setDefaultCommand(new ManualDiscHolder());
        }

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

        public void stop() {
            piston.set(DoubleSolenoid.Value.kOff);
        }
    }
}
