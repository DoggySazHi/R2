package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class IntakeBall extends CommandBase {

    private IntakeShooter shooter;
    private NetworkTableEntry ballDetectionEntry;

    public IntakeBall(RobotSubsystems subsystems) {
        shooter = subsystems.getIntakeShooter();
        addRequirements(shooter);
    }
    @Override
    public void initialize() {
        // TODO Set shooter to the downward position. Possibly run shared code from GoToPosition?
        System.out.println("Button success!");
        ballDetectionEntry = NetworkTableInstance.getDefault().getTable("LimeLightLite").getEntry("Ball Detected");
    }

    @Override
    public void execute() {
        shooter.setAngleMotor(1.0);
    }

    @Override
    public boolean isFinished() {
        return ballDetectionEntry.getBoolean(false);
    }
}
