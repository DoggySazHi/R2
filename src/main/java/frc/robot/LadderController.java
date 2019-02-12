package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class LadderController extends AbstractController
{
    /*
    Each button can be accessed via buttons[number], where number is an integer starting from 1.
    Why do they start from 1 and not 0? Good question.
    Why was this written with buttons[0] being pointed to buttons[1]? Good question too.
     */

    public LadderController(Joystick joystick)
    {
        super(joystick);
    }

    protected void init()
    {
        // "It's piecewise refinement!" - reid, probably
        buttons[1].whenPressed(null);
    }
}
