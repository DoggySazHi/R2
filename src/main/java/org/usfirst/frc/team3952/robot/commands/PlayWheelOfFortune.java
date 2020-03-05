package org.usfirst.frc.team3952.robot.commands;

import com.revrobotics.ColorMatchResult;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team3952.robot.subsystems.ControlWheel;
import org.usfirst.frc.team3952.robot.subsystems.RobotSubsystems;

import java.time.Duration;
import java.time.Instant;

import static org.usfirst.frc.team3952.robot.RobotMap.*;

//"The Wheel of Fortune Turning Over" - Sagume Kishin

/**
 * A command to automatically operate the Control Panel, using the color sensor.
 */
public class PlayWheelOfFortune extends CommandBase {
    private final RobotSubsystems subsystems;

    // Is the robot somehow spinning the wrong way?
    private NetworkTableEntry badColor;

    private int tilesPassed;
    // An index for RobotMap.WHEEL of the current color
    private int currentColor;

    // States direction of rotation (+1 to the right, -1 to the left, 0 if unknown)
    private int direction;

    private Instant startTime;

    private boolean incorrectDirection = false;

    public PlayWheelOfFortune(RobotSubsystems subsystems) {
        this.subsystems = subsystems;

        addRequirements(subsystems.getControlWheel());
        //no, bad withTimeout(15);
    }

    @Override
    public void initialize() {
        ControlWheel controlWheel = subsystems.getControlWheel();

        NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
        NetworkTable nTable = ntInst.getTable("Control Wheel");
        badColor = nTable.getEntry("Bad Color Spinner");
        badColor.setBoolean(false);

        tilesPassed = 0;
        direction = 0;

        Color c = controlWheel.getClosestColor().color;
        for (int i = 0; i < 4; i++)
            if(c.equals(WHEEL[i])) {
                currentColor = i;
                return;
            }
        System.out.println("We might have a problem... Couldn't find initial color!");
        currentColor = 0;

        controlWheel.enable();
        startTime = Instant.now();
    }

    @Override
    public void execute() {
        if (Duration.between(startTime, Instant.now()).toMillis() < CP_ACTIVATION_TIMER)
            return;
        ControlWheel controlWheel = subsystems.getControlWheel();

        ColorMatchResult match = controlWheel.getClosestColor();
        if (match != null && match.confidence >= MIN_COLOR_CONFIDENCE && !match.color.equals(WHEEL[currentColor])) {
            if (direction == 0) {
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

        if (tilesPassed < MIN_COUNT - SLOWDOWN_TILES)
            controlWheel.set(CW_SPEED_FAST);
        else
            controlWheel.set(CW_SPEED_SLOW);
    }

    public boolean isFinished() {
        ControlWheel controlWheel = subsystems.getControlWheel();

        if(tilesPassed >= MIN_COUNT && controlWheel.getClosestColor().color.equals(controlWheel.getFMSColor()))
        {
            controlWheel.stop();
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        ControlWheel controlWheel = subsystems.getControlWheel();
        controlWheel.stop();
        controlWheel.disable();
    }
}
