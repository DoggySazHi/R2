package org.usfirst.frc.team3952.robot.commands.autonomous;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.RobotMap;
import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import static org.usfirst.frc.team3952.robot.RobotMap.GYRO_THRESHOLD;

public class TurnAngle extends CommandBase {

    private final RobotSubsystems subsystems;
    private ADXRS450_Gyro gyro;

    private double endingAngle;
    private double speed;

    public TurnAngle(RobotSubsystems subsystems, double turnAngle, double speed) {
        this.subsystems = subsystems;
        DriveTrain driveTrain = subsystems.getDriveTrain();
        endingAngle = turnAngle;
        this.speed = speed;
        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        gyro = RobotMap.gyro;
        endingAngle += gyro.getAngle();
    }

    //TODO Figure out if negative is left, pos is right or vice versa
    @Override
    public void execute() {
        DriveTrain driveTrain = subsystems.getDriveTrain();
        driveTrain.drive(0, 0, 1, false);
        if (endingAngle < gyro.getAngle()) {
            driveTrain.drive(0, 0, -speed, false);
        } else {
            driveTrain.drive(0, 0, speed, false);
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(endingAngle - gyro.getAngle()) <= GYRO_THRESHOLD;
    }

    @Override
    public void end(boolean interrupted)
    {
        DriveTrain driveTrain = subsystems.getDriveTrain();
        driveTrain.stop();
    }
}
