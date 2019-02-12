/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
    public static DriveTrain driveTrain;
    public static Ladder ladder;
    public static PneumaticClaw pneumaticClaw;

    public static MainController mainController;
    public static LadderController ladderController;

    public static NetworkTableInstance ntInst;
    public static NetworkTable nTable;
    public static NetworkTableEntry autoAlignX;
    public static NetworkTableEntry autoAlignY;

    @Override
    public void robotInit() {
        driveTrain = new DriveTrain();
        ladder = new Ladder();
        pneumaticClaw = new PneumaticClaw();
        mainController = new MainController(new Joystick(0));
        ladderController = new LadderController(new Joystick(1));

        //requires pi (see other code)
        ntInst = NetworkTableInstance.getDefault();
        nTable = ntInst.getTable("datatable");
        autoAlignX = nTable.getEntry("movex");
        autoAlignY = nTable.getEntry("movey");
    }

    @Override
    public void robotPeriodic() {}

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().add(new DeployClaw());
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() { Scheduler.getInstance().add(new DeployClaw()); }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testInit() {}

    @Override
    public void testPeriodic() {
        Scheduler.getInstance().run();
    }

    public static int[] distanceToCenter() {
        return new int[] {autoAlignX.getNumber(0).intValue(), autoAlignY.getNumber(0).intValue()};
    }
}
