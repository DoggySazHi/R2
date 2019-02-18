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
        //apparently drive team doesn't want to use horizontal for strafing?
        //double hor = Robot.mainController.getHorizontalMovement();
        double hor = 0;
        double lat = Robot.mainController.getLateralMovement();
        double rot = Robot.mainController.getRotation();
        int pov = Robot.mainController.getPOV();
        if (pov == 1 || pov == 3)
            hor = 0.4;
        else if (pov == 2)
            hor = 0.8;
        else if (pov == 5 || pov == 7)
            hor = -0.4;
        else if (pov == 6)
            hor = -0.8;
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
