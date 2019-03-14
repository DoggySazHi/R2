package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.*;
import org.usfirst.frc.team3952.robot.*;
import org.usfirst.frc.team3952.robot.commands.*;

public class DriveTrain extends Subsystem
{
    double lastAngle = 0.0;
    final static double THRESHOLD = 1.0;
    double Kp = 0.03;
    boolean isStrafing = false;
	private MecanumDrive drive = RobotMap.drive;
	
	public Encoder left = RobotMap.leftEncoder;
    public Encoder right = RobotMap.rightEncoder;
    public ADXRS450_Gyro gyro = RobotMap.gyro;
	
    public void initDefaultCommand() { setDefaultCommand(new ManualDrive()); }
    
    public void drive(double hor, double lat, double rot) {
        if(lat == 0 && rot == 0 && gyro != null)
        {    
            if(!isStrafing)
            {
                lastAngle = gyro.getAngle();
                isStrafing = true;
            }
            if(Math.abs(gyro.getAngle() - lastAngle) > THRESHOLD)
            {
                if(gyro.getAngle() - lastAngle > 0)
                    rot = -Kp;
                else
                    rot = Kp;
            }
        }
        else
            isStrafing = false;
    	drive.driveCartesian(lat, hor, rot);
    }
    
    public void stop() {
    	drive.driveCartesian(0,  0,  0);
    }
}

