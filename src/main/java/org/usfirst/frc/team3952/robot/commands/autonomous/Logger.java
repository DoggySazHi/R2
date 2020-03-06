package org.usfirst.frc.team3952.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class Logger extends CommandBase {
    private final RobotSubsystems subsystems;
    private boolean end;
    private String message;

    public Logger(RobotSubsystems subsystems, String message) {
        this.subsystems = subsystems;
        this.message = message;
    }


    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (message != null)
            System.out.println(message);
        end = true;
    }

    @Override
    public boolean isFinished() {
        return end;
    }
}