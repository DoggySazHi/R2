package org.usfirst.frc.team3952.robot.commands;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class AutoAlign extends CommandBase {
    private IntakeShooter shooter;

    public AutoAlign(RobotSubsystems subsystems) {
        shooter = subsystems.getIntakeShooter();
        addRequirements(shooter);
    }
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        NetworkTableInstance netTable = NetworkTableInstance.getDefault();
        double currentAngle = shooter.getAngle();
        int xPos = netTable.getEntry("X Position").getNumber(-1).intValue();
        int yPos = netTable.getEntry("Y Position").getNumber(-1).intValue();
        if (xPos == -1 || yPos == -1) {
            return;
        }

    }
}