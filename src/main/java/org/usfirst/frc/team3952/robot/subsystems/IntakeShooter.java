package org.usfirst.frc.team3952.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.RobotMap;

import static org.usfirst.frc.team3952.robot.RobotMap.linearActuatorEncoder;

public class IntakeShooter extends SubsystemBase {
    public static final double INTAKE_SPEED = 1.0;
    public static final double REJECT_SPEED = 1.0;

    private VictorSPX intakeLeft = RobotMap.intake;
    private VictorSPX intakeRight = RobotMap.intake2;

    private Talon angleMotor = RobotMap.linearActuator;
    private AnalogEncoder liftMotorEncoder = RobotMap.linearActuatorEncoder;

    public void intake() {
        intakeLeft.set(ControlMode.PercentOutput, -1.0 * INTAKE_SPEED);
        intakeRight.set(ControlMode.PercentOutput, INTAKE_SPEED);
    }

    public void reject() {
        intakeLeft.set(ControlMode.PercentOutput, REJECT_SPEED);
        intakeRight.set(ControlMode.PercentOutput, -1.0 * REJECT_SPEED);
    }

    public void stop() {
        intakeLeft.set(ControlMode.PercentOutput, 0);
        intakeRight.set(ControlMode.PercentOutput, 0);
        angleMotor.stopMotor();
    }

    public void setAngleMotor(double speed) {
        angleMotor.set(speed);
    }

    public double getEncoderValue() {
        return liftMotorEncoder.get();
    }

    public double getPositionRaw()
    {
        return linearActuatorEncoder.get();
    }

    public double getAngle() {
        //TODO haoyan doesn't know how to write code
        return 0;
    }

    public RobotMap.Position getPosition()
    {
        RobotMap.Position toReturn = RobotMap.Position.values()[0];
        double encoder = linearActuatorEncoder.get();
        for(RobotMap.Position p : RobotMap.Position.values()) {
            if (Math.abs(p.getDistance() - encoder) < Math.abs(toReturn.getDistance() - encoder))
                toReturn = p;
        }
        return toReturn;
    }
}