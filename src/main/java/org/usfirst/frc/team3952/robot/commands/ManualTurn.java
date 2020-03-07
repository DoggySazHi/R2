package org.usfirst.frc.team3952.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.devices.MainController;
import org.usfirst.frc.team3952.robot.subsystems.ControlWheel;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import static org.usfirst.frc.team3952.robot.NetworkTableMap.colorValue;

/**
 * Doesn't actually allow the user to manually turn, but rather update all values into NetworkTables.
 */
public class ManualTurn extends CommandBase
{
    private final RobotSubsystems subsystems;

    public ManualTurn(RobotSubsystems subsystems) {
        this.subsystems = subsystems;
        addRequirements(subsystems.getControlWheel());
    }

    @Override
    public void initialize() {
        ControlWheel controlWheel = subsystems.getControlWheel();
        controlWheel.disable();
    }

    @Override
    public void execute() {
        MainController mainController = subsystems.getMainController();
        ControlWheel controlWheel = subsystems.getControlWheel();

        double rot = mainController.getHorizontalMovement();

        if(mainController.getRawButton(7))
            controlWheel.enable();
        if(mainController.getRawButton(8))
            controlWheel.disable();

        controlWheel.set(rot);
        Color c = controlWheel.getColor();
        colorValue.setDoubleArray(new double[] {c.red, c.green, c.blue})