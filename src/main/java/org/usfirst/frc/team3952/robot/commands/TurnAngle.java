package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.usfirst.frc.team3952.robot.RobotMap;
import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class TurnAngle extends CommandBase {

    private RobotSubsystems subsystems;
    private ADXRS450_Gyro gyro;

    private static final double THRESHOLD = 3.0;
    private double endingAngle;
    public TurnAngle(RobotSubsystems subsystems, double turnAngle) {
        this.subsystems = subsystems;
        DriveTrain driveTrain = subsystems.getDriveTrain();
        endingAngle = turnAngle;
        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        gyro = RobotMap.gyro;
        endingAngle += gyro.getAngle();
    }
    
    @Override
    public void execute() {
        DriveTrain driveTrain = subsystems.getDriveTrain();
        driveTrain.drive(0, 0, 1, false);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(endingAngle - gyro.getAngle()) <= THRESHOLD;
    }

    @Override
    public void end(boolean interrupted)
    {
        DriveTrain driveTrain = subsystems.getDriveTrain();
        driveTrain.stop();
    }
}
