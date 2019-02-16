package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public abstract class AbstractController
{
    Joystick joystick;
    Button[] buttons;

    public double c = 0.1;
    public double deadzone = 0.2;
    public double max = 0.8;
    public double k = (max - c) / Math.log(2 - deadzone);

    public AbstractController(Joystick joystick)
    {
        this.joystick = joystick;
        buttons = new Button[joystick.getButtonCount() + 1];
        for(int i = 1; i < buttons.length; i++)
            buttons[i] =  new JoystickButton(joystick, i);
        if(buttons.length >= 2)
            buttons[0] = buttons[1];
        init();
    }

    protected abstract void init();

    public double getHorizontalMovement() {
        double x = joystick.getX();
        return Math.abs(x) >= deadzone ? k * Math.signum(x) * (Math.log(Math.abs(x) + 1 - deadzone) + c) : 0;
    }

    public double getLateralMovement() {
        double y = -joystick.getY();
        return Math.abs(y) >= deadzone ? k * Math.signum(y) * (Math.log(Math.abs(y) + 1 - deadzone) + c) : 0;
    }
}
