package org.usfirst.frc.team3952.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class Delay extends CommandBase {

    private long startTime;
    private long delayMillis;

    public Delay(RobotSubsystems subsystems, long delayMillis) {
        this.delayMillis = delayMillis;
    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - startTime >= delayMillis;
    }
}
