package org.usfirst.frc.team3952.robot.devices;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team3952.robot.commands.PlayWheelOfFortune;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

/**
 * This should be the Logitech Attack 3 controller.
 * X, Y, static Z.
 * Buttons 1 (throttle) to 5 on handle, 6-11 counter-clockwise on the bottom.
 **/
public class SecondaryController extends AbstractController
{
    private PlayWheelOfFortune wheelOfFortune;
    /*
    Each button can be accessed via buttons[number], where number is an integer starting from 1.
    Why do they start from 1 and not 0? Good question.
    Why was this written with buttons[0] being pointed to buttons[1]? Good question too.
     */

    public SecondaryController(Joystick joystick, RobotSubsystems subsystems)
    {
        super(joystick, subsystems);
    }

    protected void init()
    {
        // "It's piecewise refinement!" - reid, probably
        if(buttons.length < 8)
        {
            //no buttons (except for index 0, useless)
            throw new ArrayIndexOutOfBoundsException("Uh oh, the ladder controller seems to not have enough buttons!");
        }
        wheelOfFortune = new PlayWheelOfFortune(subsystems);
        buttons[7].whenPressed(wheelOfFortune);
        buttons[6].cancelWhenPressed(wheelOfFortune);
    }

    //it's not really a deadzone, but eh
    public static final double DEADZONE = 0.2;
    public double throttleZero = 0.1;
    public double throttleMax = 0.9;

    public double getHorizontalMovement() {
        if (joystick == null) return 0;
        return Math.abs(joystick.getX()) >= DEADZONE ? joystick.getX() : 0;
    }

    public double getLateralMovement() {
        if (joystick == null) return 0;
        return Math.abs(joystick.getY()) >= DEADZONE ? joystick.getY() : 0;
    }

    public double getThrottle()
    {
        if (joystick == null) return 0;
        double throttle = joystick.getZ();
        if (throttle < throttleZero) return 0;
        if (throttle > throttleMax) return 1;
        return throttle;
    }
}
