package org.usfirst.frc.team3952.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

/**
 * Oh my, you remember this lab?
 * https://drive.google.com/drive/u/2/folders/1LTU0j3dl_jQ-gvhfyn8F5cIc2QuDd8AT
 * See BasicCommandLab.
 */
public class DriveTime extends CommandBase
{
    private RobotSubsystems subsystems;

    public DriveTime(RobotSubsystems subsystems)
    {
        this.subsystems = subsystems;
    }

    @Override
    public void execute()
    {

    }

    @Override
    public void initialize()
    {

    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end(boolean interrupted)
    {

    }
}
