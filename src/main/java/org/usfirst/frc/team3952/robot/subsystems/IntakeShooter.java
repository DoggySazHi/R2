package org.usfirst.frc.team3952.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.RobotMap;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;

import static org.usfirst.frc.team3952.robot.RobotMap.*;

public class IntakeShooter extends SubsystemBase {
    private CANPWMFallback intakeLeft = RobotMap.intake;
    private CANPWMFallback intakeRight = RobotMap.intake2;

    private CANPWMFallback angleMotor = RobotMap.intakeShooterTilt;
    private CANPWMFallback spinnerMotor = RobotMap.intakeShooterStorage;
    private CANPWMFallback rollerMotor = RobotMap.intakeRoller;
    private CANPWMFallback tiltServos = projectileAimer;
    private DoubleSolenoid ballShooter = RobotMap.ballShooter;
    private DigitalInput spinnerLocked = RobotMap.spinnerLocked;

    private DigitalInput hitTop = RobotMap.hitTop;
    private DigitalInput hitBottom = RobotMap.hitBottom;

    private boolean[] ballsStored = new boolean[MAX_BALL_STORAGE];

    private int ballPosition = 0;
    /**
    * The intake motors run at a set speed in order to take in the ball. 
    * @param max Checks if the motor is running at max speed.
    */
    public void intake(boolean max)
    {
        intake(max, INTAKE_ROLLER_SPEED);
    }
    /**
    * The intake motors run at a speed (one of the parameter) in order to take in the ball. 
    * @param max checks if the motor is running at max speed. 
    * @param rollerSpeed sets the intake motors to the specified speed. 
    */
    public void intake(boolean max, double rollerSpeed)
    {
        if(max)
        {
            intakeLeft.set(ControlMode.PercentOutput, 1.0);
            intakeRight.set(ControlMode.PercentOutput, -1.0);
            rollerMotor.set(ControlMode.PercentOutput, 1.0);
        }
        else
        {
            intakeLeft.set(ControlMode.PercentOutput, INTAKE_SPEED);
            intakeRight.set(ControlMode.PercentOutput, -INTAKE_SPEED);
            rollerMotor.set(ControlMode.PercentOutput, rollerSpeed);
        }
        setBallAtCurrentPosition(true);
        retract();
    }
    /**
    * The intake motors run at a speed in order to shoot out the ball. 
    * @param max checks if the motor is running at full speed. 
    * @param shoot checks if you want to shoot the ball or just reject the ball
    */
    public void reject(boolean max, boolean shoot) {
        if(max)
        {
            intakeLeft.set(ControlMode.PercentOutput, -1.0);
            intakeRight.set(ControlMode.PercentOutput, 1.0);
        }
        else 
        {
            intakeLeft.set(ControlMode.PercentOutput, -REJECT_SPEED);
            intakeRight.set(ControlMode.PercentOutput, REJECT_SPEED);
        }
        setBallAtCurrentPosition(false);        
        rollerMotor.set(ControlMode.PercentOutput, 0.0);

        // Recreating the code Haoyan found on r/FRC.
        if(shoot)
            shoot();
        else
            retract();
    }
    /**
    * It stops everything in the subsystem. I hope.
    */
    public void stop() {
        intakeLeft.set(ControlMode.PercentOutput, 0);
        intakeRight.set(ControlMode.PercentOutput, 0);
        angleMotor.stopMotor();
        rollerMotor.set(ControlMode.PercentOutput, 0.0);
        spinnerMotor.set(0);
    }

    /**
    * Control how high to point the shooter at. This accepts direct control. The double parameter sets the speed of the angler of the shooter.
     * @param speed How fast to tilt up or down. Negative might be up and positive downwards.
    */ 
    public void setAngleMotor(double speed) {
        if(speed < 0 && hitTop.get() || speed > 0 && hitBottom.get()) return;
        angleMotor.set(speed);
    }

    /**
     * Control the tilt (ball shooting) servos. Already compensates for controller input. The double parameter sets the speed at which the the shooter tilts
     * @param speed At what position the servos are tilted at.
     */
    public void setTiltServos(double speed) {
        tiltServos.set(Math.min(1.0, Math.max(-1.0, speed + TILT_SERVO_TRIM)));
    }

    /**
     * Control the storage motor to find a slot. Should be combined with isLocked(). The double parameter sets the rotating speed of the shooter.
     * @param speed At how fast the rotation motor spins.
     */
    public void setRotateMotor(double speed) {
        spinnerMotor.set(speed);
    }

    /**
     * Check if the ball holder is currently on a valid position (not half open). 
     */
    public boolean isLocked() {
        return spinnerLocked.get();
    }

    /**
     * Check if there's a ball stored in the current position.
     */
    public boolean ballInPosition() {
        return ballsStored[ballPosition];
    }

    /**
     * Count the amount of balls that are in the IntakeShooter.
     */
    public int getBalls() {
        int balls = 0;
        for(boolean b : ballsStored) if(b) balls++;
        return balls;
    }

    /**
     * Advance the counter. NOTE: You should be running the motor before this is called!
     */
    public void advance() {
        ballPosition = (ballPosition + 1) % ballsStored.length;
    }

    /**
     * Set if at the current position, there is a ball.
     * @param hasBall If there is a ball (true) or not (false).
     */
    public void setBallAtCurrentPosition(boolean hasBall) {
        ballsStored[ballPosition] = hasBall;
    }

    /**
     * It sets the pneumatic piston to move forward which therefore shoots the ball out 
     */
    private void shoot() {
        ballShooter.set(Value.kForward);
    }

    /**
     * It sets the pneumatic piston to move backwards which therefore allows the shooter to shoot again
     */
    public void retract() {
        ballShooter.set(Value.kReverse);
    }

    /**
     * Check if the <code>IntakeShooter</code> hit the top limit switch.
     * @return Whether the top is hit.
     */
    public boolean hitTop() {
        return hitTop.get();
    }

    /**
     * Check if the <code>IntakeShooter</code> hit the bottom limit switch.
     * @return Whether the bottom is hit.
     */
    public boolean hitBottom() {
        return hitBottom.get();
    }
}