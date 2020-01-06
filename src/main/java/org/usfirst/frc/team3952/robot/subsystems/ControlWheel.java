package org.usfirst.frc.team3952.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;

import org.usfirst.frc.team3952.robot.*;

import static org.usfirst.frc.team3952.robot.RobotMap.*;
import static org.usfirst.frc.team3952.robot.RobotMap.CP_YELLOW;

public class ControlWheel extends SubsystemBase
{
    private VictorSPX motor = RobotMap.controlPanelSpinner;
    private ColorSensorV3 sensor = RobotMap.colorSensor;

    private NetworkTableEntry color;
    private NetworkTableEntry colorValue;
    private NetworkTableEntry fmsColor;
    private NetworkTableEntry rotations;
    private ColorMatch colorMatch;

    public ControlWheel()
    {
        NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
        NetworkTable nTable = ntInst.getTable("Control Wheel");
        color = nTable.getEntry("Color");
        colorValue = nTable.getEntry("Color Value");
        fmsColor = nTable.getEntry("FMS Color");
        rotations = nTable.getEntry("Rotations");

        colorMatch = new ColorMatch();
        colorMatch.addColorMatch(CP_RED);
        colorMatch.addColorMatch(CP_GREEN);
        colorMatch.addColorMatch(CP_BLUE);
        colorMatch.addColorMatch(CP_YELLOW);
    }

    public void set(double value) {
    	motor.set(ControlMode.PercentOutput, value);
    }
    
    public void stop() {
    	motor.set(ControlMode.PercentOutput, 0);
    }

    public Color getColor() {
        Color c = sensor.getColor();
        colorValue.setDoubleArray(new double[] {c.red, c.green, c.blue});
        return sensor.getColor();
    }

    public ColorMatchResult getClosestColor() {
        ColorMatchResult output = colorMatch.matchClosestColor(sensor.getColor());
        if (CP_RED.equals(output.color)) {
            color.setString("RED");
        } else if (CP_GREEN.equals(output.color)) {
            color.setString("GREEN");
        } else if (CP_BLUE.equals(output.color)) {
            color.setString("BLUE");
        } else if (CP_YELLOW.equals(output.color)) {
            color.setString("YELLOW");
        } else {
            color.setString("NO MATCH");
        }
        return output;
    }

    public Color getFMSColor() {
        String data = DriverStation.getInstance().getGameSpecificMessage();
        Color output = null;
        if(data.length() > 0)
        {
            switch (data.charAt(0))
            {
                case 'R' :
                    output = CP_RED;
                    fmsColor.setString("RED");
                    break;
                case 'G' :
                    output = CP_GREEN;
                    fmsColor.setString("GREEN");
                    break;
                case 'B' :
                    output = CP_BLUE;
                    fmsColor.setString("BLUE");
                    break;
                case 'Y' :
                    output = CP_YELLOW;
                    fmsColor.setString("YELLOW");
                    break;
                default :
                    fmsColor.setString("MALFORMED");
            }
        }
        else
            fmsColor.setString("UNKNOWN");
        return output;
    }

    public void update(int rotations)
    {
        getColor();
        getClosestColor();
        getFMSColor();
        this.rotations.setDouble(rotations);
    }
}