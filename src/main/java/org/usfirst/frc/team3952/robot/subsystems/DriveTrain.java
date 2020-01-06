package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.*;
import org.usfirst.frc.team3952.robot.*;

public class DriveTrain extends SubsystemBase
{
	private DifferentialDrive drive = RobotMap.drive;
    
    public void drive(double hor, double lat, double rot, boolean quickTurn) {
    	drive.curvatureDrive(lat, hor, quickTurn);
    }
    
    public void stop() {
    	drive.curvatureDrive(0,  0, false);
    }
}