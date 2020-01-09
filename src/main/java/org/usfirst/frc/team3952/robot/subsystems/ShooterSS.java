package org.usfirst.frc.team3952.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import org.usfirst.frc.team3952.robot.RobotMap;

public class ShooterSS extends SubsystemBase
{
    public  Talon projectileEjector = RobotMap.projectileEjector;
    public  Servo projectileAimer = RobotMap.projectileAimer;
    public  Talon projectileStorage = RobotMap.projectileStorage;
    public  Talon projectileTilt = RobotMap.projectileTilt;
    public Encoder encoder = RobotMap.linearActuatorEncoder;

    public int speed = 100;
    public int accuracy = 5;

    //these are the values of the calculated x and y cords offset from center of the target
    //calculation done and sent by the limelite lite. 
    private NetworkTableEntry x;
    private NetworkTableEntry y;

   public ShooterSS(){
    NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
    NetworkTable nTable = ntInst.getTable("Vision");
    x = nTable.getEntry("X_Offset");
    y = nTable.getEntry("Y_Offset");
   }

   public void autoShoot(){
        aling();
        shoot();
   }

   public void shoot(){
       //TODO
   }

   public void trenchMode(){
       //TODO
   }

   public void wheelOfFortuneMode(){
       //TODO
   }

   public void ballPickingMode(){
       //TODO
   }

   public void aling(){
        while (x.getNumber(0).intValue() > accuracy || y.getNumber(0).intValue() > accuracy){
            //change linear actuator based on offset
            projectileTilt.set(y.getNumber(0).intValue()/speed);
            //change servo angle based on observation
            projectileAimer.setAngle(projectileAimer.getAngle() - x.getNumber(0).intValue());
        }
   }

}