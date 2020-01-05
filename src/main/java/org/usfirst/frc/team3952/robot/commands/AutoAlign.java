package org.usfirst.frc.team3952.robot.commands;

import org.usfirst.frc.team3952.robot.subsystems.*;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static org.usfirst.frc.team3952.robot.RobotMap.*;

public class AutoAlign extends CommandBase {
    private DriveTrain driveTrain;

    private NetworkTableInstance ntInst;
    private NetworkTable nTable;
    private NetworkTableEntry vector;

    private int cnt = 0;

    public AutoAlign(RobotSubsystems subsystems) {
        driveTrain = subsystems.getDriveTrain();
        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        ntInst = NetworkTableInstance.getDefault();
        nTable = ntInst.getTable("LimeLightLite");
        vector = nTable.getEntry("AutoAlign Coordinates");
    }

    @Override
    public void execute() {
        //TODO optimize for diff drive
        if(cnt++ % PERIOD <= STEP) {
            if(distanceToCenter()[0] > 0)
                driveTrain.drive(STEPPING_SPEED, 0, 0, false);
            else
                driveTrain.drive(-STEPPING_SPEED, 0, 0, false);
        }
        else
            driveTrain.stop();
    }

    @Override
    public boolean isFinished() {
        //TODO cancel command
        if(Math.abs(distanceToCenter()[0]) < MIN_DISTANCE_FROM_TARGET) {
            driveTrain.stop();
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.stop();
    }
    
    private double[] distanceToCenter() {
        return vector.getDoubleArray(new double[] {0.0, 0.0});
    }
}