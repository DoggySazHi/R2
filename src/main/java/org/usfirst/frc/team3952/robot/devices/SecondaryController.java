package org.usfirst.frc.team3952.robot.devices;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team3952.robot.commands.GoToEmptySpot;
import org.usfirst.frc.team3952.robot.commands.GoToNextPosition;
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
    private GoToEmptySpot goToEmptySpot;
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
        goToEmptySpot = new GoToEmptySpot(subsystems);
        buttons[7].whenPressed(wheelOfFortune);
        buttons[6].cancelWhenPressed(wheelOfFortune);

        buttons[8].whenPressed(new GoToNextPosition(subsystems).withTimeout(5));
    }

    //it's not really a deadzone, but eh
    public static final double DEADZONE = 0.05;
    public final double throttleZero = 0.1;
    public final double throttleMax = 0.9;

    /**
     * It returns how much the joystick is turned left or right, it returns a double value. it ranges from -1 to 1. NOTE: this is the SECONDARY controller, not the main
     */
    public double getHorizontalMovement() {
        if (joystick == null) return 0;
        return Math.abs(joystick.getX()) >= DEADZONE ? joystick.getX() : 0;
    }

    /**
     * It returns how much the joystick is turned forward or backward, it returns a double value. It ranges from -1 to 1. NOTE: this is the SECONDARY controller, not the main
     */
    public double getLateralMovement() {
        if (joystick == null) return 0;
        return Math.abs(joystick.getY()) >= DEADZONE ? joystick.getY() : 0;
    }
    /**
    * It is like an airplane throttle, just returns a double value that shows how much the throttle is turned. It returns 0 or 1. NOTE: this is the SECONDARY controller, not the main
    */
    public double getThrottle()
    {
        if (joystick == null) return 0;
        double throttle = joystick.getZ();
        if (throttle < throttleZero) return 0;
        if (throttle > throttleMax) return 1;
        return throttle;
    }
}
