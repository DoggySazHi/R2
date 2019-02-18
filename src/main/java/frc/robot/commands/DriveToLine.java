package frc.robot.commands;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveToLine extends Command {
    public static final double TIMEOUT = 5.0;
    public static final int LIGHT_THRESHOLD = 100;
    public static final double SPEED =  0.5;
    
    public AnalogInput sensor = RobotMap.qtiSensor;

    public DriveToLine() {
        requires(Robot.driveTrain);
        setTimeout(TIMEOUT);
        setInterruptible(true);
    }

    @Override
    protected void initialize() {}

    @Override
    protected void execute() {
        Robot.driveTrain.drive(0, SPEED, 0);
        
    }

    @Override
    protected boolean isFinished() {
        return sensor.getValue() < LIGHT_THRESHOLD || Robot.ladderController.override();
    }

    @Override
    protected void end() {
    	Robot.driveTrain.stop();
    }

    @Override
    protected void interrupted() {
    	Robot.driveTrain.stop();
    }
}