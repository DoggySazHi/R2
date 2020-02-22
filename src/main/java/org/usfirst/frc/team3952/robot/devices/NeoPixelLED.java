package org.usfirst.frc.team3952.robot.devices;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

/**
 * A way to control WS2812B LEDs, commonly known as NeoPixels. We have two of these rings.
 * They hook up to PWM however, so watch out.
 */
public class NeoPixelLED {
    private AddressableLED light;
    private AddressableLEDBuffer lightData;
    private int rainbowStat;

    /**
     * Create a ring/strip of NeoPixels at the port number and count of LEDs. WARNING: The RoboRIO uses 6v on PWM!
     * @param pwmPort The port to connect on.
     * @param ledCount The amount of LEDs on the strip or ring. For our rings, it's 24.
     */
    public NeoPixelLED(int pwmPort, int ledCount) {
        light = new AddressableLED(pwmPort);
        lightData = new AddressableLEDBuffer(ledCount);
        light.setLength(lightData.getLength());
        light.setData(lightData);
    }

    /**
     * Set the color of the ring or strip for all LEDs.
     * @param r Value of Red, from 0-255. This automatically goes to the closest valid value, so -1 would be 0.
     * @param g Value of Green, from 0-255. This automatically goes to the closest valid value, so 65535 would be 255.
     * @param b Value of Blue, from 0-255. This automatically goes to the closest valid value, so 69 would be 69.
     */
    public void setColor(int r, int g, int b) {
        r = Math.min(255, Math.max(0, r));
        g = Math.min(255, Math.max(0, g));
        b = Math.min(255, Math.max(0, b));
        for (var i = 0; i < lightData.getLength(); i++)
            lightData.setRGB(i, r, g, b);
        light.setData(lightData);
    }

    /**
     * Set the color of the ring or strip for a specified LED.
     * @param r Value of Red, from 0-255. This automatically goes to the closest valid value, so -1 would be 0.
     * @param g Value of Green, from 0-255. This automatically goes to the closest valid value, so 65535 would be 255.
     * @param b Value of Blue, from 0-255. This automatically goes to the closest valid value, so 69 would be 69.
     * @param pos The LED to change the color of, on the strip/ring.
     */
    public void setColor(int r, int g, int b, int pos) {
        lightData.setRGB(pos, r, g, b);
        light.setData(lightData);
    }

    /**
     * A generic rainbow pattern. This should be called repeatedly on a command's execute function. William, why.
     */
    public void rainbow() {
        for (var i = 0; i < lightData.getLength(); i++) {
            int hue = (rainbowStat + (i * 180 / lightData.getLength())) % 180;
            lightData.setHSV(i, hue, 255, 128);
        }
        rainbowStat += 3;
        rainbowStat %= 180;

        light.setData(lightData);
    }
}
