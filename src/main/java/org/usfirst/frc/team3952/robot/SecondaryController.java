package org.usfirst.frc.team3952.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team3952.robot.commands.PlayWheelOfFortune;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class SecondaryController extends AbstractController
{
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
        buttons[2].whenPressed(new PlayWheelOfFortune(subsystems));
    }

    //it's not really a deadzone, but eh
    public static final double DEADZONE = 0.2;

    public double getHorizontalMovement() {
        if (joystick == null) return 0;
        return Math.abs(joystick.getX()) >= DEADZONE ? joystick.getX() : 0;
    }

    public double getLateralMovement() {
        if (joystick == null) return 0;
        return Math.abs(joystick.getY()) >= DEADZONE ? joystick.getY() : 0;
    }
}
