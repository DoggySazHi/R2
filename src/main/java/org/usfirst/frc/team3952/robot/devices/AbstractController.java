package org.usfirst.frc.team3952.robot.devices;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public abstract class AbstractController
{
    RobotSubsystems subsystems;

    boolean successfulInit;
    Joystick joystick;
    Button[] buttons;

    public AbstractController(Joystick joystick, RobotSubsystems subsystems)
    {
        this.subsystems = subsystems;
        this.joystick = joystick;
        if(joystick == null)
            return;
        buttons = new Button[joystick.getButtonCount() + 1];
        for(int i = 1; i < buttons.length; i++)
            buttons[i] =  new JoystickButton(joystick, i);
        if(buttons.length >= 2)
            buttons[0] = buttons[1];
        init();
        successfulInit = true;
    }

    protected abstract void init();

    public boolean getRawButton(int button)
    {
        if(!successfulInit || button >= buttons.length || button < 0 || buttons[button] == null)
            return false;
        return buttons[button].get();
    }
}
