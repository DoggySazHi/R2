package org.usfirst.frc.team3952.robot.devices;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import edu.wpi.first.hal.CANData;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Talon;

import static org.usfirst.frc.team3952.robot.devices.CANPWMFallback.Mode.*;

public class CANPWMFallback {
    public enum Mode {None, PWM, CAN}

    public static Mode defaultMode = CAN;

    public static CANPWMFallback[] usedPWM = new CANPWMFallback[10];
    public static CANPWMFallback[] usedCAN = new CANPWMFallback[64];

    private Mode overrideMode;

    private String name;
    private int pwmNum;
    private int canNum;

    private VictorSPX canDevice;
    private Talon pwmDevice;

    public CANPWMFallback(int pwmNum, int canNum, String name)
    {
        this.name = name;

        // Check for valid values. If they aren't, set to -1 (disabled)
        if(pwmNum >= 0 && pwmNum <= 9)
        {
            this.pwmNum = pwmNum;
            if(usedPWM[pwmNum] != null)
                usedPWM[pwmNum] = this;
            else
            {
                System.out.println(name != null ? name : "Something" + " attempted to use the same PWM motor as " + usedPWM[pwmNum].name + "! PWM support will be disabled.");
                this.pwmNum = -1;
            }
        }
        else
            this.pwmNum = -1;

        if(canNum >= 0 && canNum <= 63)
        {
            this.canNum = canNum;
            if(usedCAN[canNum] != null)
                usedCAN[canNum] = this;
            else
            {
                System.out.println(name != null ? name : "Something" + " attempted to use the same CAN motor as " + usedCAN[canNum].name + "! CAN support will be disabled.");
                this.canNum = -1;
            }
        }
        else
            this.canNum = -1;

        setMode();
        init();

        if(getMode() == None)
            System.out.println("WARNING: " + (name != null ? name : "Something") + " could not be activated with any motors! Check for conflicts.");
    }

    private void setMode() {
        if (pwmNum == -1 && canNum == -1) overrideMode = None;
        if (defaultMode == PWM && pwmNum == -1) overrideMode = CAN;
        if (defaultMode == CAN && canNum == -1) overrideMode = PWM;
    }

    public CANPWMFallback setOverrideMode(Mode mode) {
        if (getMode() != None)
            throw new IllegalStateException("You cannot set mode if a system was already setup!" + (name != null ? " - " + name : ""));
        if (mode == CAN && canNum == -1 || mode == PWM && pwmNum == -1)
            throw new IllegalStateException("Robots should not try to use an override without valid port numbers!" + (name != null ? " - " + name : ""));
        overrideMode = mode;
        init();

        return this;
    }

    public CANPWMFallback withRamping(double time) {
        if (getMode() != CAN) return this;

        VictorSPXConfiguration rampConf = new VictorSPXConfiguration();
        rampConf.openloopRamp = time;
        rampConf.closedloopRamp = time;
        canDevice.configAllSettings(rampConf);
        return this;
    }

    public void init() {
        if (pwmDevice != null) {
            pwmDevice.close();
            pwmDevice = null;
        }
        canDevice = null;

        //TODO make sure the CAN check works
        if (overrideMode == CAN && canNum != -1 && isCANAvailable())
            canDevice = new VictorSPX(canNum);
        else if (overrideMode == PWM && pwmNum != -1)
            pwmDevice = new Talon(pwmNum);
        // None: Don't init.
    }

    public Mode getMode()
    {
        if(canDevice != null)
            return CAN;
        if(pwmDevice != null)
            return PWM;
        return None;
    }

    private boolean isCANAvailable()
    {
        CAN device = new CAN(canNum);
        CANData c = new CANData();
        boolean success = device.readPacketTimeout(0x01040000, 1000, c);
        if (success) System.out.println("API_ID: 0x01040000");
        success = device.readPacketTimeout(0, 1000, c);
        if (success) System.out.println("API_ID: 0");
        device.close();
        return success;
    }

    public void set(double value)
    {
        if(getMode() == CAN)
            canDevice.set(ControlMode.PercentOutput, value);
        if(getMode() == PWM)
            pwmDevice.set(value);
    }

    public void set(ControlMode cm, double value)
    {
        set(value);
    }

    public void stop()
    {
        if(getMode() == PWM)
            pwmDevice.stopMotor();
        else
            set(0);
    }
}
