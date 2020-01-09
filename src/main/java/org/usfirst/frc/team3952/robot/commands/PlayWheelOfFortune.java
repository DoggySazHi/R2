package org.usfirst.frc.team3952.robot.commands;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.networktables.*;
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

    private NetworkTableEntry badColor;

    private int tilesPassed;
    private int currentColor;
    private int direction;
    private boolean incorrectDirection = false;

    public PlayWheelOfFortune(RobotSubsystems subsystems) {
        controlWheel = subsystems.getControlWheel();

        addRequirements(controlWheel);
        //no, bad withTimeout(15);
    }

    @Override
    public void initialize() {
        //NetworkTables Input From Vision System
        //William, can you put more comments so we know what exactly these network table values are
        NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
        NetworkTable nTable = ntInst.getTable("Control Wheel");
        badColor = nTable.getEntry("Bad Color Spinner");
        badColor.setBoolean(false);

        //varibles initialization
        tilesPassed = 0;
        direction = 0;

        //terrible debug message dont imitate
        System.out.println("AYAYAYAYAYAYAYAYAYAYAYAYAYAYAYAYAYAYA!");

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
                    incorrectDirection = false;
                    tilesPassed++;
                    currentColor = nextIndex;
                }
                else
                {
                    if(!incorrectDirection)
                        System.out.println("Read incorrectly; is the motor randomly switching, or is the sensor messed up?");
                    incorrectDirection = true;
                }
                badColor.setBoolean(incorrectDirection);
            }
        }
        controlWheel.update(tilesPassed);
        //.5 might seem too fast
        controlWheel.set(0.5);
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
