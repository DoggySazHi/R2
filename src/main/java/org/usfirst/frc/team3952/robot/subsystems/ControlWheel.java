package org.usfirst.frc.team3952.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team3952.robot.NetworkTableMap;
import org.usfirst.frc.team3952.robot.RobotMap;
import org.usfirst.frc.team3952.robot.devices.AnalogUltrasonic;
import org.usfirst.frc.team3952.robot.devices.CANPWMFallback;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;
import static org.usfirst.frc.team3952.robot.NetworkTableMap.*;
import static org.usfirst.frc.team3952.robot.RobotMap.*;

/**
 * A subsystem to handle the spinning of the Control Panel.
 */
public class ControlWheel extends SubsystemBase {
    private final CANPWMFallback motor = RobotMap.controlPanelSpinner;
    private final ColorSensorV3 colorSensor = RobotMap.colorSensor;
    private final AnalogUltrasonic controlPanelUltrasonic = RobotMap.controlPanelUltraSonic;
    private final DoubleSolenoid enableSolenoid = RobotMap.controlPanelSolenoid;

    private final ColorMatch colorMatch;

    public ControlWheel() {
        colorMatch = new ColorMatch();
        colorMatch.addColorMatch(CP_RED);
        colorMatch.addColorMatch(CP_GREEN);
        colorMatch.addColorMatch(CP_BLUE);
        colorMatch.addColorMatch(CP_YELLOW);
    }
    //
    public void set(double value) {
    	motor.set(ControlMode.PercentOutput, value);
    }
    
    public void stop() {
    	motor.set(ControlMode.PercentOutput, 0);
    }

    public Color getColor() {
        Color c = colorSensor.getColor();
        colorValue.setDoubleArray(new double[] {c.red, c.green, c.blue});
        return colorSensor.getColor();
    }

    public ColorMatchResult getClosestColor() {
        ColorMatchResult output = colorMatch.matchClosestColor(colorSensor.getColor());
        if (CP_RED.equals(output.color))
            color.setString("RED (" + output.confidence + ")");
        else if (CP_GREEN.equals(output.color))
            color.setString("GREEN (" + output.confidence + ")");
        else if (CP_BLUE.equals(output.color))
            color.setString("BLUE (" + output.confidence + ")");
        else if (CP_YELLOW.equals(output.color))
            color.setString("YELLOW (" + output.confidence + ")");
        else
            color.setString("NO MATCH");
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
        NetworkTableMap.rotations.setDouble(rotations);
    }

    public void enable()
    {
        enableSolenoid.set(kForward);
    }

    public void disable()
    {
        enableSolenoid.set(kReverse);
    }

    public double getDistance()
    {
        return controlPanelUltrasonic.getRangeMM();
    }
}