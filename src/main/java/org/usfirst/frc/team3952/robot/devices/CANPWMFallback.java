package org.usfirst.frc.team3952.robot.devices;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import edu.wpi.first.hal.CANData;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

import static org.usfirst.frc.team3952.robot.RobotMap.MOTOR_CHECK_DELAY;
import static org.usfirst.frc.team3952.robot.devices.CANPWMFallback.Mode.*;

/**
 * Allows easy swapping of port numbers, as well as identification of motors using both PWM and CAN.
 * Also reminds the user of port conflicts, instead of dumping horrendous exceptions.
 */
public class CANPWMFallback implements MotorController {

    public enum Mode {None, PWM, CAN}

    /**
     * Set the default mode the <code>CANPWMFallback</code> will go to.
     */
    public static Mode defaultMode = CAN;

    public static boolean forceCANConnection = false;

    private static final CANPWMFallback[] usedPWM = new CANPWMFallback[10];
    private static final CANPWMFallback[] usedCAN = new CANPWMFallback[64];

    private Mode overrideMode;

    private final String name;
    private int pwmNum;
    private int canNum;
    private final boolean isServo;
    private boolean servoInverted;
    private boolean useFullRange = true;

    private VictorSPX canDevice;
    private Talon pwmDevice;
    private Servo servo;

    /**
     * Create a <code>CANPWMFallback</code> with a PWM/CAN port, and name.
     * Both port numbers can be filled out, however it will default to whatever is stated in <code>CANPWMFallback.defaultMode</code>.
     * It can be overridden using <code>CANPWMFallback.setOverrideMode(Mode mode).</code>
     *
     * @param pwmNum PWM channel number.
     * @param canNum CAN device number.
     * @param name Name of the device.
     */
    public CANPWMFallback(int pwmNum, int canNum, String name)
    {
        this(pwmNum, canNum, name, false);
    }

    /**
     * Create a <code>CANPWMFallback</code> with a PWM/CAN port, and name.
     * Both port numbers can be filled out, however it will default to whatever is stated in <code>CANPWMFallback.defaultMode</code>.
     * It can be overridden using <code>CANPWMFallback.setOverrideMode(Mode mode).</code>
     * This constructor allows you to also set if a device is a servo.
     *
     * @param pwmNum PWM channel number.
     * @param canNum CAN device number (however you probably don't need this if you're using this constructor).
     * @param name Name of the device.
     */
    public CANPWMFallback(int pwmNum, int canNum, String name, boolean isServo)
    {
        this.name = name;
        this.isServo = isServo;

        // Check for valid values. If they aren't, set to -1 (disabled)
        if(pwmNum >= 0 && pwmNum <= 9)
        {
            this.pwmNum = pwmNum;
            if(usedPWM[pwmNum] == null)
                usedPWM[pwmNum] = this;
            else
            {
                System.out.println((name != null ? name : "Something") + " attempted to use the same PWM motor as " + usedPWM[pwmNum].name + "! PWM support will be disabled.");
                this.pwmNum = -1;
            }
        }
        else
            this.pwmNum = -1;

        if(canNum >= 0 && canNum <= 63)
        {
            this.canNum = canNum;
            if(usedCAN[canNum] == null)
                usedCAN[canNum] = this;
            else
            {
                System.out.println((name != null ? name : "Something") + " attempted to use the same CAN motor as " + usedCAN[canNum].name + "! CAN support will be disabled.");
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

    /**
     * Fallback to another mode, depending on invalidness.
     */
    private void setMode() {
        overrideMode = defaultMode;
        if (pwmNum == -1 && canNum == -1) overrideMode = None;
        if (defaultMode == PWM && pwmNum == -1) overrideMode = CAN;
        if (defaultMode == CAN && canNum == -1) overrideMode = PWM;
    }

    /**
     * Set whether a <code>CANPWMFallback</code> uses a different device than whatever is stated in <code>CANPWMFallback.defaultMode</code>.
     *
     * @param mode The mode to switch over to.
     * @return Itself. Useful for daisy-chaining in the RobotMap.
     */
    public CANPWMFallback setOverrideMode(Mode mode) {
        if (getMode() != None)
            throw new IllegalStateException("You cannot set mode if a system was already setup!" + (name != null ? " - " + name : ""));
        if (mode == CAN && canNum == -1 || mode == PWM && pwmNum == -1)
            throw new IllegalStateException("Robots should not try to use an override without valid port numbers!" + (name != null ? " - " + name : ""));
        overrideMode = mode;
        init();

        return this;
    }

    /**
     * Only applicable to CAN devices, it sets how long a motor will take until it hits max or min speed.
     *
     * @param time The time, in seconds, of how long it should ramp up and down.
     * @return Itself. Useful for daisy-chaining in the RobotMap.
     */
    public CANPWMFallback withRamping(double time) {
        if (getMode() != CAN) return this;

        VictorSPXConfiguration rampConf = new VictorSPXConfiguration();
        rampConf.openloopRamp = time;
        rampConf.closedloopRamp = time;
        canDevice.configAllSettings(rampConf);
        return this;
    }

    /**
     * Only applicable to servos, it sets whether it will accept full-range values (-1.0 1.0) or regular (0.0 1.0).
     *
     * @param useFullRange Whether to set using full-range values or regular values.
     * @return Itself. Useful for daisy-chaining in the RobotMap.
     */
    public CANPWMFallback useFullRange(boolean useFullRange) {
        this.useFullRange = useFullRange;
        return this;
    }

    /**
     * Actually create the motor instances.
     */
    private void init() {
        if (pwmDevice != null) {
            pwmDevice.close();
            pwmDevice = null;
        }
        canDevice = null;

        //TODO make sure the CAN check works
        if (overrideMode == CAN && canNum != -1) {
            if(isCANAvailable())
            {
                canDevice = new VictorSPX(canNum);
            }

            else
                System.out.println("CAN device failed to connect for " + name + "!");
        }
        else if (overrideMode == PWM && pwmNum != -1) {
            if(isServo())
                servo = new Servo(pwmNum);
            else
            {
                pwmDevice = new Talon(pwmNum);
            }
        }
        // None: Don't init.
    }

    /**
     * Get the current mode of the <code>CANPWMFallback</code>.
     * @return The current mode.
     */
    public Mode getMode()
    {
        if(canDevice != null)
            return CAN;
        if(pwmDevice != null)
            return PWM;
        return None;
    }

    /**
     * I really don't know. It *should* detect whether the CAN works, however this probably doesn't work.
     * @return Whether the CAN port exists.
     */
    private boolean isCANAvailable()
    {
        if(forceCANConnection) return true;
        CAN device = new CAN(canNum);
        CANData c = new CANData();
        boolean success = device.readPacketTimeout(0x01040000, 1000, c);
        if (success) System.out.println("API_ID: 0x01040000");
        success = device.readPacketTimeout(0, 1000, c);
        if (success) System.out.println("API_ID: 0");
        device.close();
        System.out.println("Attempted to fetch CAN status for " + canNum);
        return success;
    }

    /**
     * Set the speed (or position) of the motor.
     * @param value The speed, usually from -1.0 to 1.0, or if a servo, 0.0 to 1.0 unless <code>useFullRange</code> is true.
     */
    public void set(double value)
    {
        if(getMode() == CAN)
            canDevice.set(ControlMode.PercentOutput, value);
        if(getMode() == PWM) {
            if(isServo())
                setServo(value);
            else
                pwmDevice.set(value);
        }
    }

    /**
     * Set a servo's position.
     * @param value The power.
     */
    private void setServo(double value)
    {
        if(!isServo()) return;
        if(useFullRange) value = (value + 1.0) / 2.0;
        if(servoInverted) value = 1.0 - value;
        servo.setPosition(value);
    }

    /**
     * Get the current power (or position) of the device.
     * @return The power.
     */
    @Override
    public double get() {
        if(getMode() == CAN)
            return canDevice.getMotorOutputPercent();
        if(getMode() == PWM) {
            if(isServo()) {
                if (useFullRange)
                    return (servo.get() * 2.0) - 1.0;
                return servo.get();
            }
            return pwmDevice.get();
        }
        else
            return 0;
    }

    /**
     * Set whether to invert the input values (or power) of the motor.
     * @param isInverted If to invert the motor or not.
     */
    @Override
    public void setInverted(boolean isInverted) {
        if(getMode() == CAN)
            canDevice.setInverted(isInverted);
        if(getMode() == PWM) {
            if(isServo())
                this.servoInverted = isInverted;
            else
                pwmDevice.setInverted(isInverted);
        }
    }

    /**
     * Check if the values are inverted or not.
     * @return If the motor is inverted.
     */
    @Override
    public boolean getInverted() {
        if(getMode() == CAN)
            return canDevice.getInverted();
        if(getMode() == PWM) {
            if(isServo())
                return servoInverted;
            return pwmDevice.getInverted();
        }
        else
            return false;
    }

    /**
     * ...whether the <code>CANPWMFallback</code> holds a servo.
     * Do I really need to explain any further?
     *
     * @return When the darn object holds a servo.
     */
    public boolean isServo() {
        return isServo && servo != null;
    }

    /**
     * How many times to we need to say it stops the motor?
     */
    @Override
    public void disable() {
        stop();
    }

    /**
     * Just like <code>set(double value)</code>, but it fallbacks for code that still uses the CAN <code>ControlMode</code>.
     * Really, an alias.
     * @param cm Really doesn't matter.
     * @param value The power "percentage".
     */
    public void set(ControlMode cm, double value)
    {
        set(value);
    }

    /**
     * It probably stops the motor. I think. If it doesn't, I'm suing you!
     */
    public void stop()
    {
        if(getMode() == PWM) {
            if(isServo())
                servo.setSpeed(0);
            else
                pwmDevice.stopMotor();
        }
        else
            set(0);
    }

    /**
     * An alias to stop(), it stops the motor.
     */
    public void stopMotor()
    {
        stop();
    }

    private static long lastCheck;

    /**
     * Usually located in <code>Robot.robotPeriodic()</code>, it checks if a CAN/PWM port is available.
     */
    public static void reInit()
    {
        if(System.currentTimeMillis() - lastCheck >= MOTOR_CHECK_DELAY)
        {
            lastCheck = System.currentTimeMillis();
            for(CANPWMFallback device : usedCAN)
                if(device != null && device.getMode() == None)
                    device.init();
            for(CANPWMFallback device : usedPWM)
                if(device != null && device.getMode() == None)
                    device.init();
        }
    }
}
