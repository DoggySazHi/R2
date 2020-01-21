package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.MainController;
import org.usfirst.frc.team3952.robot.subsystems.ControlWheel;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class ManualTurn extends CommandBase
{
    private RobotSubsystems subsystems;

    private NetworkTableEntry colorValue;

    public ManualTurn(RobotSubsystems subsystems) {
        this.subsystems = subsystems;

        addRequirements(subsystems.getControlWheel());
    }

    @Override
    public void initialize() {
        NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
        NetworkTable nTable = ntInst.getTable("Sensors");
        colorValue = nTable.getEntry("Color");
    }

    @Override
    public void execute() {
        MainController mainController = subsystems.getMainController();
        ControlWheel controlWheel = subsystems.getControlWheel();

        double rot = mainController.getHorizontalMovement();
        controlWheel.set(rot);
        Color c = controlWheel.getColor();
        colorValue.setDoubleArray(new double[] {c.red, c.green, c.blue});
    }

    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        ControlWheel controlWheel = subsystems.getControlWheel();
        controlWheel.stop();
    }
}
