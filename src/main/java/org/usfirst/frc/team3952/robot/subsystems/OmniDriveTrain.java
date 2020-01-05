package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.*;
import org.usfirst.frc.team3952.robot.*;
import org.usfirst.frc.team3952.robot.commands.*;

// Image for reference https://seas.yale.edu/sites/default/files/imce/other/HolonomicOmniWheelDrive.pdf

public class OmniDriveTrain extends Subsystem
{
    public static Talon frontLeftWheel = RobotMap.frontLeftWheel;
    public static Talon frontRightWheel = RobotMap.frontRightWheel;
    public static Talon rearLeftWheel = RobotMap.rearLeftWheel;
    public static Talon rearRightWheel = RobotMap.rearRightWheel;
	
    public void initDefaultCommand() { setDefaultCommand(new ManualDrive()); }
    
    private static final double PI4 = Math.PI/4.0;
    private static final double DEADZONE = 0.1;

    public void drive(double hor, double lat, double rot) {
        // Decrease the effects of rotation, especially when the robot is moving.
        rot /= 2.0;

        // Calculate a hor/lat that stays with -1 to 1, depending on the rotation.
        double x = hor * Math.sqrt(Math.pow(1 - Math.abs(rot), 2) / 2.0);
        double y = lat * Math.sqrt(Math.pow(1 - Math.abs(rot), 2) / 2.0);

        double power = Math.sqrt(Math.pow(y, 2) + Math.pow(x, 2));
        double angle = Math.atan2(y, x);

        // Doubt this would ever be true... though that's a :reimuthink: from William.
        if(angle < 0) angle += Math.PI * 2;
        
        double powerX = power * Math.sin(angle + PI4);
        double powerY = power * Math.sin(angle - PI4);

        // FL and RL motors SHOULD be inverted compared to the FR and RR motors.
        double fl = -1.0 * powerY + rot;
        double fr = 1.0 * powerX + rot;
        double rl = -1.0 * powerX + rot;
        double rr = 1.0 * powerY + rot;

        System.out.println(fl + " " + fr + " " + rl + " " + rr);

        // Deadzone motors
        if(Math.abs(fl) < DEADZONE) fl = 0.0;
        if(Math.abs(fr) < DEADZONE) fr = 0.0;
        if(Math.abs(rl) < DEADZONE) rl = 0.0;
        if(Math.abs(rr) < DEADZONE) rr = 0.0;

        // Actually not swap max and min
        frontLeftWheel.set(Math.min(Math.max(fl, -1), 1));
        frontRightWheel.set(Math.min(Math.max(fr, -1), 1));
        rearLeftWheel.set(Math.min(Math.max(rl, -1), 1));
        rearRightWheel.set(Math.min(Math.max(rr, -1), 1));
    }
    
    public void stop() {
        frontLeftWheel.stopMotor();
        frontRightWheel.stopMotor();
        rearLeftWheel.stopMotor();
        rearRightWheel.stopMotor();
    }
}

