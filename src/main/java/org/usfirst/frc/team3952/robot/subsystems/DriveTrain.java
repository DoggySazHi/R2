package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team3952.robot.*;
import org.usfirst.frc.team3952.robot.commands.*;

public class DriveTrain extends Subsystem
{
	private DifferentialDrive drive = RobotMap.drive;
	
	public Encoder left = RobotMap.leftEncoder;
    public Encoder right = RobotMap.rightEncoder;
    public ADXRS450_Gyro gyro = RobotMap.gyro;
	
    public void initDefaultCommand() { setDefaultCommand(new ManualDrive()); }
    
    public void drive(double hor, double lat, double rot) {
    	drive.curvatureDrive(lat, hor, Robot.mainController.getQuickTurn());
    }
    
    public void stop() {
    	drive.curvatureDrive(0,  0,  false);
    }
}

