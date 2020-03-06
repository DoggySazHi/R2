package org.usfirst.frc.team3952.robot.devices;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

/**
 * This should be the Microsoft SideWinder 2 controller.
 * X, Y, Z (rotate stick), Throttle. POV as well.
 * Buttons 1 (throttle) to 5 on handle, 6-11 counter-clockwise on the bottom.
 **/
public class MainController extends AbstractController {
    /*
    Each button can be accessed via buttons[number], where number is an integer starting from 1.
    Why do they start from 1 and not 0? Good question.
    Why was this written with buttons[0] being pointed to buttons[1]? Good question too.
     */

    public final double cT = 0.08;
    public final double deadzoneT = 0.08;
    public final double maxT = 0.4;
    public final double kT = (maxT - cT) / Math.log(2 - deadzoneT);
    private boolean isInverted;

    public MainController(Joystick joystick, RobotSubsystems subsystems) {
        super(joystick, subsystems);
    }

    protected void init()
    {
        // Place your button bindings here.
    }

    /**
     * This will return the rotation of the joystick, it is a double value and this correspond to how much you twist the controller
     */
    public double getRotation() {
        if (joystick == null) return 0;
        double t = joystick.getZ();
        double dist = Math.abs(t) >= deadzoneT ? kT * Math.signum(t) * (Math.log(Math.abs(t) + 1 - deadzoneT) + cT) : 0;
        return dist;
    }

    public final double c = 0.1;
    public final double deadzone = 0.2;
    public final double max = 1.0;
    public final double k = (max - c) / Math.log(2 - deadzone);

    public final double throttleZero = 0.1;
    public final double throttleMax = 0.9;

    /**
     * It returns how much the joystick is turned left or right, it returns a double value. it ranges from -1 to 1
     */
    public double getHorizontalMovement() {
        if (joystick == null) return 0;
        double x = joystick.getX();
        double dist = Math.abs(x) >= deadzone ? k * Math.signum(x) * (Math.log(Math.abs(x) + 1 - deadzone) + c) : 0;
        return dist;
    }
    /**
    * Sometimes during the match we need to drive backwards and that is HARD, so we basically inverse all the controls, ex. turnLeft turns right, moveForward goes backwards.
    */
    public void setInverted(boolean isInverted)
    {
        this.isInverted = isInverted;
    }
    /**
    * It returns how much the joystick is turned forward or backward, it returns a double value. It ranges from -1 to 1
    */
    public double getLateralMovement()
    {
        if (joystick == null) return 0;
        double y = -joystick.getY();
        double dist = Math.abs(y) >= deadzone ? k * Math.signum(y) * (Math.log(Math.abs(y) + 1 - deadzone) + c) : 0;
        return isInverted ? dist * -1 : dist;
    }
    /**
    * It is like an airplane throttle, just returns a double value that shows how much the throttle is turned. It returns 0 or 1
    */
    public double getThrottle()
    {
        if (joystick == null) return 0;
        double throttle = joystick.getThrottle();
        if (throttle < throttleZero) return 0;
        if (throttle > throttleMax) return 1;
        return throttle;
    }
    /**
    * Returns an integer from 0 to 7. It is the white circular button that you can turn. It represents what angle/direction it is turned towards
    */
    public int getPOV()
    {
        //0 - top, going clockwise to 7
        if (joystick == null) return -1;
        return joystick.getPOV(0) == -1 ? -1 : (int) Math.round(joystick.getPOV(0) / 45.0);
    }
    /**
    * It returns if button 1 is pressed on the controller. boolean value. used to enable quickTurn
    */
    public boolean getQuickTurn()
    {
        if (joystick == null) return false;
        return joystick.getRawButtonPressed(1);
    }
}
