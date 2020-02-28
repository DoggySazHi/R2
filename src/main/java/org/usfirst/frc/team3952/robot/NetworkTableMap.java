package org.usfirst.frc.team3952.robot;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkTableMap {
    private static NetworkTableInstance instance;

    private static NetworkTable r2Table;
    public NetworkTableEntry calibrateGyro;

    public void init()
    {
        instance = NetworkTableInstance.getDefault();
        r2Table = instance.getTable("R2");

        calibrateGyro = r2Table.getEntry("Calibrate Gyro");
        calibrateGyro.setBoolean(false);
        calibrateGyro.addListener(event -> {
            RobotMap.gyro.calibrate();
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }
}
