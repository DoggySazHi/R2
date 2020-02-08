package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.MainController;
import org.usfirst.frc.team3952.robot.SecondaryController;
import org.usfirst.frc.team3952.robot.subsystems.Climber;
import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class ManualClimber extends CommandBase
{
    private RobotSubsystems subsystems;

    public ManualClimber(RobotSubsystems subsystems) {
        this.subsystems = subsystems;
        addRequirements(subsystems.getClimber());
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Climber climber = subsystems.getClimber();
        MainController mainController = subsystems.getMainController();
        if(mainController.getRawButton(5))
            climber.deploy();
        if(mainController.getRawButton(6))
            climber.retract();
    }

    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        DriveTrain driveTrain = subsystems.getDriveTrain();
        driveTrain.stop();
    }
}
