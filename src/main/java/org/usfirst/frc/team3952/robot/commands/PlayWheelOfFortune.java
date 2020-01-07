package org.usfirst.frc.team3952.robot.commands;

import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.ControlWheel;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import static org.usfirst.frc.team3952.robot.RobotMap.MIN_COUNT;
import static org.usfirst.frc.team3952.robot.RobotMap.WHEEL;

//"The Wheel of Fortune Turning Over" - Sagume Kishin
public class PlayWheelOfFortune extends CommandBase
{
    private ControlWheel controlWheel;

    private int tilesPassed;
    private int currentColor;
    private int direction;

    public PlayWheelOfFortune(RobotSubsystems subsystems) {
        controlWheel = subsystems.getControlWheel();

        addRequirements(controlWheel);
        //no, bad withTimeout(15);
    }

    @Override
    public void initialize() {
        tilesPassed = 0;
        direction = 0;
        System.out.println("AYAYYAYAYAYAYAAYYAYAYAYAYYAYAYYAYYYAYYAY!");
        Color c = controlWheel.getClosestColor().color;
        for (int i = 0; i < 4; i++)
            if(c.equals(WHEEL[i])) {
                currentColor = i;
                return;
            }
        System.out.println("We might have a problem... Couldn't find initial color!");
        currentColor = 0;
    }

    @Override
    public void execute() {
        ColorMatchResult match = controlWheel.getClosestColor();
        if(match != null && !match.color.equals(WHEEL[currentColor])) {
            if(direction == 0) {
                if (match.color.equals(WHEEL[(currentColor + 1) % WHEEL.length])) {
                    tilesPassed++;
                    direction = 1;
                } else if (match.color.equals(WHEEL[(currentColor + WHEEL.length - 1) % WHEEL.length])) {
                    direction = -1;
                    tilesPassed++;
                } else {
                    System.out.println("Failed to find direction!");
                    direction = 1;
                }
            }
            else
            {
                int nextIndex = (currentColor + WHEEL.length + direction) % WHEEL.length;
                if(match.color.equals(WHEEL[nextIndex]))
                {
                    tilesPassed++;
                    currentColor = nextIndex;
                }
                else
                    System.out.println("Read incorrectly; is the motor randomly switching, or is the sensor messed up?");
            }
        }
        controlWheel.update(tilesPassed);
    }

    public boolean isFinished() {
        if(tilesPassed >= MIN_COUNT && controlWheel.getClosestColor().color.equals(controlWheel.getFMSColor()))
        {
            controlWheel.stop();
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    	controlWheel.stop();
    }
}
