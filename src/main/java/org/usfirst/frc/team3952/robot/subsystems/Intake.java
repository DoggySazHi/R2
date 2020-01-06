package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.*;
import com.ctre.phoenix.motorcontrol.can.*;

public class Intake extends SubsystemBase
{
    public static final double INTAKE_SPEED = 0.5;
    public static final double REJECT_SPEED = 0.8;

    private Talon intakeLeft = RobotMap.intakeLeft;
    private Talon intakeRight = RobotMap.intakeRight;

    public void intake() {
        intakeLeft.set(INTAKE_SPEED);
        intakeRight.set(-1.0 * INTAKE_SPEED);
    }

    public void reject() {
        intakeLeft.set(-1.0 * REJECT_SPEED);
        intakeRight.set(REJECT_SPEED);
    }

    public void stop() {
        intakeLeft.stopMotor();
        intakeRight.stopMotor();
    }
}

