package org.usfirst.frc.team3952.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.RobotMap;
import org.usfirst.frc.team3952.robot.RobotMap.*;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;

import static org.usfirst.frc.team3952.robot.RobotMap.*;

public class IntakeShooter extends SubsystemBase {
    private CANPWMFallback intakeLeft = RobotMap.intake;
    private CANPWMFallback intakeRight = RobotMap.intake2;

    private CANPWMFallback angleMotor = RobotMap.intakeShooterTilt;
    private CANPWMFallback spinnerMotor = RobotMap.intakeShooterStorage;
    private CANPWMFallback rollerMotor = RobotMap.intakeRoller;
    private AnalogEncoder liftMotorEncoder = RobotMap.linearActuatorEncoder;
    private Servo tiltServos = RobotMap.projectileAimer;
    private DoubleSolenoid ballShooter = RobotMap.ballShooter;
    private DigitalInput spinnerLocked = RobotMap.spinnerLocked;
    private DigitalInput hitTop = RobotMap.hitTop;
    private DigitalInput hitBottom = RobotMap.hitBottom;

    private boolean[] ballsStored = new boolean[MAX_BALL_STORAGE];

    private int ballPosition = 0;

    public void intake(boolean max)
    {
        intake(max, INTAKE_ROLLER_SPEED);
    }

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
        retract();
    }

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
        rollerMotor.set(ControlMode.PercentOutput, 0.0);

        // Recreating the code Haoyan found on r/FRC.
        if(shoot)
            shoot();
        else
            retract();
    }

    public void stop() {
        intakeLeft.set(ControlMode.PercentOutput, 0);
        intakeRight.set(ControlMode.PercentOutput, 0);
        angleMotor.stopMotor();
        rollerMotor.set(ControlMode.PercentOutput, 0.0);
    }

    // Control how high to point the shooter at. This accepts direct control.
    public void setAngleMotor(double speed) {
        if (speed < 0 && hitTop.get() || speed > 0 && hitBottom.get())
            return;
        angleMotor.set(speed);
    }

    // Control the tilt (ball shooting) servos. Already compensates for controller input.
    public void setTiltServos(double speed) {
        tiltServos.set((speed + 1.0)/2.0);
    }

    // Control the storage motor to find a slot. Should be combined with isLocked().
    public void setRotateMotor(double speed) {
        spinnerMotor.set(speed);
    }

    // Check if the ball holder is currently on a valid position (not half open)
    public boolean isLocked() {
        return spinnerLocked.get();
    }

    // Check if there's a ball stored in the current position.
    public boolean ballInPosition() {
        return ballsStored[ballPosition];
    }

    // Count the amount of balls that are in the IntakeShooter.
    public int getBalls() {
        int balls = 0;
        for(boolean b : ballsStored) if(b) balls++;
        return balls;
    }

    // Advance the counter. NOTE: You should be running the motor before this is called!
    public void advance() {
        ballPosition = (ballPosition + 1) % ballsStored.length;
    }

    public Direction getDirectionToEmpty()
    {
        //TODO don't bs this
        return Direction.Right;
    }

    public Direction getDirectionToFull()
    {
        //TODO don't bs this
        return Direction.Left;
    }

    // Rewind the counter. NOTE: You should be running the motor before this is called!
    public void rewind() {
        ballPosition = (ballPosition + ballsStored.length - 1) % ballsStored.length;
    }

    // Get the raw value of the actuator's encoder.
    public double getPositionRaw() {
        return liftMotorEncoder.get();
    }

    // Get the current "angle" of the shooter position. Might be removed in favor of Position (see RobotMap)
    public double getAngle() {
        //TODO when we have access to encoders, we will check this
        return 0;
    }

    // Get the current position of the shooter's angle.
    public Position getPosition() {
        // Use a linear search to find the closest Position (enum) to the current angle.
        Position toReturn = Position.values()[0];
        double encoder = liftMotorEncoder.get();
        for (Position p : Position.values()) {
            // Compare the difference of the iterating Position to the encoder versus the one we saved.
            if (Math.abs(p.getDistance() - encoder) < Math.abs(toReturn.getDistance() - encoder))
                toReturn = p;
        }
        return toReturn;
    }

    private void shoot() {
        ballShooter.set(Value.kForward);
    }

    public void retract() {
        ballShooter.set(Value.kReverse);
    }
}