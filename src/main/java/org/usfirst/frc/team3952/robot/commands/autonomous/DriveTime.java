package org.usfirst.frc.team3952.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;

import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class DriveTime extends CommandBase {
    private long time;
    private long startTime;
    private RobotSubsystems subsystems;
    private double speedX;
    private double speedY;
    private double speedZ;
    private boolean quickTurn;

    public DriveTime(RobotSubsystems subsystems, long time, double speedX, double speedY, double speedZ, boolean quickTurn) {
        this.time = time;
        this.subsystems = subsystems;
        this.speedX = speedX;
        this.speedY = speedY;
        this.speedZ = speedZ;
        this.quickTurn = quickTurn;

        addRequirements(subsystems.getDriveTrain());
    }
    

    @Override
    public void initialize() {
        DriveTrain driveTrain = subsystems.getDriveTrain();
        startTime = System.currentTimeMillis();
        driveTrain.drive(speedX, speedY, speedZ, quickTurn);
    }

    @Override
    public void execute() {
        DriveTrain driveTrain = subsystems.getDriveTrain();
        driveTrain.drive(speedX, speedY, speedZ, quickTurn);
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - startTime >= time;
    }

    @Override
    public void end(boolean interrupted) {
        DriveTrain driveTrain = subsystems.getDriveTrain();
        driveTrain.stop();
    }
}