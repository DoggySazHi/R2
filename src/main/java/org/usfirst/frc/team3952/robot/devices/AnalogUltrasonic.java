package org.usfirst.frc.team3952.robot.devices;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.AnalogInput;

public class AnalogUltrasonic {
    private AnalogInput ai;
    public AnalogUltrasonic(int analogPin)
    {
        ai = new AnalogInput(analogPin);
    }

    private static final double toInch = 0.125;
    private static final double inchToCM = 2.54;

    public double getRangeMM()
    {
        double distance = ai.getValue() * toInch * inchToCM * 100;
        NetworkTableInstance.getDefault().getTable(":patchythink:").getEntry(":reimuthink:").setDouble(distance);
        return distance;
    }
}
