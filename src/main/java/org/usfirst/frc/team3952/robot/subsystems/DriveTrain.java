package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.*;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;

public class DriveTrain extends SubsystemBase {
    private CANPWMFallback leftDrive = RobotMap.leftDrive;
    private CANPWMFallback rightDrive = RobotMap.rightDrive;

    //code accounts for reversing of motors no need for mechanical solution

    public void drive(double hor, double lat, double rot, boolean quickTurn) {
    	if (quickTurn){
            leftDrive.set(rot);
            rightDrive.set(rot);
        } else {
            leftDrive.set(Math.sqrt(hor + rot));
            rightDrive.set(-Math.sqrt(rot - hor));
        }
    }
    
    public void stop() {
        leftDrive.set(0);
        rightDrive.set(0);
    }
}