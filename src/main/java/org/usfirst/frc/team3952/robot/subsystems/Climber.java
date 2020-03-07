package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.RobotMap;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;
import org.usfirst.frc.team3952.robot.devices.MainController;

import static org.usfirst.frc.team3952.robot.RobotMap.FLIP_SERVO_START_POS;
import static org.usfirst.frc.team3952.robot.RobotMap.SERVO_MAXPOWER;

/**
 * The subsystem to go up and down to hang on the activator.
 * The subsystem to shoot the claw in order to hang on the clothing hanger :)
 */
public class Climber extends SubsystemBase {
    private final CANPWMFallback liftMotor = RobotMap.liftMotor;
    private final CANPWMFallback liftMotor2 = RobotMap.liftMotor2;
    private final CANPWMFallback climberActivator = RobotMap.climberActivator;
    //private Servo climberActivator2 = RobotMap.climberActivator2;

    /**
     * It is the constructor for the climber subsystem
     */
    public Climber() {
        retract();
    }

    /**
     * It toggles the deploy mechanisms that deploys the claw
     *
     * 5 is Deploy,
     * 6 is Retract
     */
    public void deploy() {
        climberActivator.set(SERVO_MAXPOWER);
    }

    /**
     * Opposite of deploy. See deploy <code>org.usfirst.frc.team3952.robot.subsystems.Climber.deploy()</code>
     */
    public void retract() {
        climberActivator.set(-SERVO_MAXPOWER);
    }

    /**
     * It turns the motor that lifts the robot up on
     *
     * @param value It sets the speed at which the motors will turn in order to lift the robot up
     */
    public void lift(double value) {
        liftMotor.set(-value);
        liftMotor2.set(value);
    }

    public void liftR(double value){
        liftMotor2.set(-value);
    }

    public void liftL(double value){
        liftMotor.set(value);
    }

    public void stop() {
        liftMotor.stop();
        liftMotor2.stop();
    }
}
