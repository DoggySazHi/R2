package org.usfirst.frc.team3952.robot.devices;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;

public class AnalogUltrasonic {
    private final AnalogInput ai;
    private NetworkTableEntry ntStorage;

    public AnalogUltrasonic(int analogPin)
    {
        ai = new AnalogInput(analogPin);
    }

    public AnalogUltrasonic withNTData(String table, String entry)
    {
        ntStorage = NetworkTableInstance.getDefault().getTable(table).getEntry(entry);
        return this;
    }

    private static final double toInch = 0.125;
    private static final double inchToCM = 2.54;

    public double getRangeMM()
    {
        double distance = ai.getValue() * toInch * inchToCM * 100;
        if(ntStorage != null)
            ntStorage.setDouble(distance);
        return distance;
    }
}
