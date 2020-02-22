package org.usfirst.frc.team3952.robot.devices;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class Light {
    private AddressableLED light;
    private AddressableLEDBuffer lightData;
    private int rainbowStat;

    public Light(int pwmPort, int ledCount) {
        light = new AddressableLED(pwmPort);
        lightData = new AddressableLEDBuffer(ledCount);
        light.setLength(lightData.getLength());
        light.setData(lightData);
    }

    public void setColor(int r, int g, int b) {
        r = Math.min(255, Math.max(0, r));
        g = Math.min(255, Math.max(0, g));
        b = Math.min(255, Math.max(0, b));
        for (var i = 0; i < lightData.getLength(); i++)
            lightData.setRGB(i, r, g, b);
        light.setData(lightData);
    }

    public void setColor(int r, int g, int b, int pos) {
        lightData.setRGB(pos, r, g, b);
        light.setData(lightData);
    }


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
