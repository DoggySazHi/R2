package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3952.robot.*;

public class ManualLadder extends Command
{
    public ManualLadder() {
        requires(Robot.ladder);
        setInterruptible(true);
    }

    protected void initialize() {}

    public Encoder encoder = RobotMap.ladderEncoder;
    
    //literally a "manual ladder"
    public NetworkTableEntry netEncoder = Robot.nTable.getEntry("Ladder Encoder");
    public NetworkTableEntry netPosition = Robot.nTable.getEntry("Ladder Position");
    public NetworkTableEntry netActualSpeed = Robot.nTable.getEntry("Actual Ladder Speed");

    protected void execute() {
        netEncoder.setDouble(encoder.get());
        netActualSpeed.setDouble(encoder.getRate()/encoder.getDistancePerPulse());
        switch(Robot.ladder.pos)
        {
            case 0:
                netPosition.setString("Rocket Port 1");
                break;
            case 1:
                netPosition.setString("Cargo Port 1");
                break;
            case 2:
                netPosition.setString("Rocket Port 2");
                break;
            case 3:
                netPosition.setString("Cargo Port 2");
                break;
            case 4:
                netPosition.setString("Rocket Port 3");
                break;
            case 5:
                netPosition.setString("Cargo Port 3");
                break;
            default:
                netPosition.setString("Yes. (undefined)");
                break;
        }
        if(Math.abs(Robot.ladderController.getLateralMovement()) > 0)
			RobotMap.ladder.set(Robot.ladderController.getLateralMovement());
        else
            Robot.ladder.stop();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.ladder.stop();
    }
    
    protected void interrupted() {
    	Robot.ladder.stop();
    }
}
