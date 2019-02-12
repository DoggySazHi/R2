package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ManualDrive extends Command
{
    public ManualDrive() {
        requires(Robot.driveTrain);
        setInterruptible(true);
    }

    protected void initialize() {}

    protected void execute() {
    	double hor = Robot.mainController.getHorizontalMovement();
    	double lat = Robot.mainController.getLateralMovement();
    	double rot = Robot.mainController.getRotation();
    	Robot.driveTrain.drive(hor, lat, rot);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.driveTrain.stop();
    }

    protected void interrupted() {
    	Robot.driveTrain.stop();
    }
}
