package org.usfirst.frc.team3952.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public abstract class AbstractController
{
    boolean successfulInit;
    Joystick joystick;
    Button[] buttons;

    public AbstractController(Joystick joystick)
    {
        this.joystick = joystick;
        buttons = new Button[joystick.getButtonCount() + 1];
        for(int i = 1; i < buttons.length; i++)
            buttons[i] =  new JoystickButton(joystick, i);
        if(buttons.length >= 2)
            buttons[0] = buttons[1];
        init();
        successfulInit = true;
    }

    protected abstract void init();
}
