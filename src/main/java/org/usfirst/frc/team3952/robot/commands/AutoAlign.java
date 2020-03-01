package org.usfirst.frc.team3952.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import static org.usfirst.frc.team3952.robot.NetworkTableMap.autoAlignX;
import static org.usfirst.frc.team3952.robot.NetworkTableMap.autoAlignY;

/**
 * The core for the AutoAlign system, where the robot will drive around by itself based on the RPi.
 */
public class AutoAlign extends CommandBase {
    private RobotSubsystems subsystems;

    public AutoAlign(RobotSubsystems subsystems) {
        this.subsystems = subsystems;

        IntakeShooter shooter = subsystems.getIntakeShooter();
        DriveTrain driveTrain = subsystems.getDriveTrain();
        
        addRequirements(shooter, driveTrain);
    }
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        int xPos = autoAlignX.getNumber(-1).intValue();
        int yPos = autoAlignY.getNumber(-1).intValue();
        if (xPos == -1 || yPos == -1) {
            return;
        }
    }

    @Override
    public void end(boolean interrupted) {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        DriveTrain driveTrain = subsystems.getDriveTrain();

        shooter.stop();
        driveTrain.stop();
    }
}