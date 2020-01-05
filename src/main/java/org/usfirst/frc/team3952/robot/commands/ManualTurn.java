package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.usfirst.frc.team3952.robot.MainController;
import org.usfirst.frc.team3952.robot.subsystems.*;

public class ManualTurn extends CommandBase
{
    private ControlWheel controlWheel;
    private MainController mainController;

    private NetworkTableInstance ntInst;
    private NetworkTable nTable;
    private NetworkTableEntry colorValue;

    public ManualTurn(RobotSubsystems subsystems) {
        controlWheel = subsystems.getControlWheel();
        mainController = subsystems.getMainController();

        addRequirements(controlWheel);
    }

    @Override
    public void initialize() {
        ntInst = NetworkTableInstance.getDefault();
        nTable = ntInst.getTable("Sensors");
        colorValue = nTable.getEntry("Color");
    }

    @Override
    public void execute() {
        double rot = mainController.getRotation();
        controlWheel.set(rot);
        Color c = controlWheel.getColor();
        colorValue.setDoubleArray(new double[] {c.red, c.green, c.blue});
    }

    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    	controlWheel.stop();
    }
}
