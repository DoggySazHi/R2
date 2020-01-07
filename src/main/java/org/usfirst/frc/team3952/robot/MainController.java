package org.usfirst.frc.team3952.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import org.usfirst.frc.team3952.robot.commands.PlayWheelOfFortune;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

public class MainController extends AbstractController
{
    /*
    Each button can be accessed via buttons[number], where number is an integer starting from 1.
    Why do they start from 1 and not 0? Good question.
    Why was this written with buttons[0] being pointed to buttons[1]? Good question too.
     */

    public double cT = 0.08;
    public double deadzoneT = 0.08;
    public double maxT = 0.4;
    public double kT = (maxT - cT) / Math.log(2 - deadzoneT);

    public MainController(Joystick joystick, RobotSubsystems subsystems)
    {
        super(joystick, subsystems);
    }

    protected void init()
    {
        System.out.println("Controller inited");
        for(Button b : buttons)
            b.whenPressed(new PlayWheelOfFortune(subsystems));
        //buttons[1].whenPressed();
    }

    public double getRotation() {
        double t = joystick.getZ();
        return Math.abs(t) >= deadzoneT ? kT * Math.signum(t) * (Math.log(Math.abs(t) + 1 - deadzoneT) + cT) : 0;
    }

    public double c = 0.1;
    public double deadzone = 0.2;
    public double max = 0.8;
    public double k = (max - c) / Math.log(2 - deadzone);

    public double getHorizontalMovement()
    {
        double x = joystick.getX();
        return Math.abs(x) >= deadzone ? k * Math.signum(x) * (Math.log(Math.abs(x) + 1 - deadzone) + c) : 0;
    }

    public double getLateralMovement()
    {
        double y = -joystick.getY();
        return Math.abs(y) >= deadzone ? k * Math.signum(y) * (Math.log(Math.abs(y) + 1 - deadzone) + c) : 0;
    }

    public int getPOV()
    {
        //0 - top, going clockwise to 7
        return joystick.getPOV(0) == -1 ? -1 : (int) Math.round(joystick.getPOV(0) / 45.0);
    }

    public boolean getQuickTurn()
    {
        return joystick.getRawButtonPressed(1);
    }
}
