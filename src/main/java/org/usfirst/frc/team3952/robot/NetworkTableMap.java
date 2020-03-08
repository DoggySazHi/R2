package org.usfirst.frc.team3952.robot;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkTableMap {
    private static NetworkTableInstance instance;

    public static NetworkTableEntry xPosition;
    public static NetworkTableEntry yPosition;
    public static NetworkTableEntry xVelocity;
    public static NetworkTableEntry yVelocity;
    public static NetworkTableEntry color;
    public static NetworkTableEntry colorValue;
    public static NetworkTableEntry fmsColor;
    public static NetworkTableEntry rotations;
    public static NetworkTableEntry autoAlignX;
    public static NetworkTableEntry autoAlignY;
    public static NetworkTableEntry calibrateGyro;
    public static NetworkTableEntry cameraMode;
    public static NetworkTableEntry manualClimber;
    public static NetworkTableEntry forceClimberDeploy;

    private static NetworkTable accelTable;
    private static NetworkTable controlWheelTable;

    private static NetworkTable r2Table;
    private static NetworkTable limeLiteTable;

    public enum CameraMode { Front, Rear, Both }

    public static void init() {
        instance = NetworkTableInstance.getDefault();

        r2Table = instance.getTable("R2");

        calibrateGyro = r2Table.getEntry("Calibrate Gyro");
        calibrateGyro.setBoolean(false);
        calibrateGyro.addListener(event -> RobotMap.gyro.calibrate(), EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
        manualClimber = r2Table.getEntry("Manual Climber Setup");
        manualClimber.setBoolean(false);

        accelTable = NetworkTableInstance.getDefault().getTable("AccelTest");
        xPosition = accelTable.getEntry("XPos");
        yPosition = accelTable.getEntry("YPos");
        xVelocity = accelTable.getEntry("XVel");
        yVelocity = accelTable.getEntry("YVel");

        controlWheelTable = instance.getTable("Control Wheel");
        color = controlWheelTable.getEntry("Color");
        colorValue = controlWheelTable.getEntry("Color Value");
        fmsColor = controlWheelTable.getEntry("FMS Color");
        rotations = controlWheelTable.getEntry("Rotations");

        limeLiteTable = instance.getTable("LimeLightLite");
        autoAlignX = limeLiteTable.getEntry("X Position");
        autoAlignY = limeLiteTable.getEntry("Y Position");
        cameraMode = limeLiteTable.getEntry("Camera Mode");
        cameraMode.setDefaultDouble(CameraMode.Both.ordinal());

        forceClimberDeploy = r2Table.getEntry("Force Deploy");
        forceClimberDeploy.setBoolean(false);
        forceClimberDeploy.setDefaultBoolean(false);
    }
}
