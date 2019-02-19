package org.usfirst.frc.team3952.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team3952.robot.commands.*;

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

    MoveLadderToNextPos mltnp;

    protected void init()
    {
        // "It's piecewise refinement!" - reid, probably
        if(buttons.length < 8)
        {
            //no buttons (except for index 0, useless)
            System.out.println("Uh oh, the ladder controller seems to not have enough buttons!");
            throw new ArrayIndexOutOfBoundsException();
        }
        //open
        buttons[1].whenPressed(new ManualPneumatics());
        buttons[4].whenPressed(new AutoAlign());
        buttons[5].whenPressed(new AutoAlign());
        buttons[8].whenPressed(new DeployClaw());
        buttons[8].whenReleased(new ResetClaw());
        buttons[3].whenPressed(new MoveLadderToNextPos(true));
        buttons[2].whenPressed(new MoveLadderToNextPos(false));
        //buttons[6].cancelWhenPressed(move ladder to next pos i guess);
    }

    //WARNING: THE OVERRIDE BUTTON WILL KILL THE CURRENT COMMAND (EXCEPT MANUALS)
    public boolean override()
    {
        //i don't got time to figure out how cancelling works
        return joystick.getRawButtonPressed(6);
    }

    //it's not really a deadzone, but eh
    public static final double DEADZONE = 0.2;

    public double getHorizontalMovement() {
        return Math.abs(joystick.getX()) >= DEADZONE ? joystick.getX() : 0;
    }

    public double getLateralMovement() {
        return Math.abs(joystick.getY()) >= DEADZONE ? joystick.getY() : 0;
    }
}
