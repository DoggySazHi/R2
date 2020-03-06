package org.usfirst.frc.team3952.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.IntakeShooter;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;


public class TiltShooterHorizontal extends CommandBase {
    private final RobotSubsystems subsystems;
    private boolean finish;
    private double position;

    public TiltShooterHorizontal(RobotSubsystems subsystems, double position) {
        this.subsystems = subsystems;
        this.position = position;
        IntakeShooter shooter = subsystems.getIntakeShooter();

        addRequirements(shooter);
    }
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        shooter.setTiltServos(position);

    }
    @Override
    public void end(boolean interrupted) {
        IntakeShooter shooter = subsystems.getIntakeShooter();
        shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return finish;
    }
}
