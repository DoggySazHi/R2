package org.usfirst.frc.team3952.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.RobotMap;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;

import static org.usfirst.frc.team3952.robot.RobotMap.*;

/**
 * The subsystem to go up and down to hang on the activator.
 * The subsystem to shoot the claw in order to hang on the clothing hanger :)
 */
public class Climber extends SubsystemBase {
    private CANPWMFallback liftMotor = RobotMap.liftMotor;
    private Servo climberActivator = RobotMap.climberActivator;
    //private Servo climberActivator2 = RobotMap.climberActivator2;
    /**
    * It is the constructor for the climber subsystem
    */
    public Climber() {
        retract();
    }
    /**
    * It toggles the deploy mechanisms that deploys the claw
    */
    public void deploy() {
        if (!FLIP_SERVO_START_POS)
            servoControl(SERVO_MAXPOWER);
        else
            servoControl(-SERVO_MAXPOWER);
    }
    /**
    * Nothing :))))
    */
    public void postDeploy() {
        //deleted for compatibility
    }
    /**
    * Opposite of deploy. See deploy <code>org.usfirst.frc.team3952.robot.subsystems.Climber.deploy()</code>
    */
    public void retract() {
        if (!FLIP_SERVO_START_POS)
            servoControl(-SERVO_MAXPOWER);
        else
            servoControl(SERVO_MAXPOWER);
    }

    /**
    * It turns the motor that lifts the robot up on
    */
    public void lift(double value) {
        liftMotor.set(ControlMode.PercentOutput, value);
    }
    /**
    * It is a way to control the climber activater servo. climberActivator.set(value);
    */
    private void servoControl(double value) {
        climberActivator.set(value);
        /*
        if (!INVERT_CLIMBER_SERVOS)
            climberActivator2.set(value);
        else
            climberActivator2.set(-value);
         */
    }
}
