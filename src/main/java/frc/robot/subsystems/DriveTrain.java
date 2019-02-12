package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.RobotMap;
import frc.robot.commands.ManualDrive;

public class DriveTrain extends Subsystem
{
	private MecanumDrive drive = RobotMap.drive;
	
	public Encoder left = RobotMap.leftEncoder;
	public Encoder right = RobotMap.rightEncoder;
	public ADXRS450_Gyro gyro = RobotMap.gyro;
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualDrive());
    }
    
    public void drive(double hor, double lat, double rot) {
    	drive.driveCartesian(lat, hor, rot);
    }
    
    public void stop() {
    	drive.driveCartesian(0,  0,  0);
    }
}

