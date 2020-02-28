package org.usfirst.frc.team3952.robot.devices;

import com.google.gson.Gson;
import edu.wpi.first.wpilibj.Filesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Dictionary;
import java.util.*;

import static org.usfirst.frc.team3952.robot.devices.Path.PathStatus.*;

/**
 * A class that handles the path data, reading from a JSON file in the <code>deploy</code> folder.
 *
 * @author DoggySazHi (touhou addict)
 */
public class Path implements Runnable {
    private static Gson gson;

    /**
     * Instructions formatting: Long: time in milliseconds for a "command" to run. String: The instruction itself.
     * Instructions available are:
     * NONE
     * MOVE X Y Z
     * LIFT 0.0-1.0
     * TILT 0.0-1.0
     * END
     */
    private List<Long> timings;
    private List<String> instructions;
    private PathStatus status = Invalid;
    private String fileName;
    private boolean started;
    private long lastCheck;

    /**
     * Get all instructions associated with the path.
     * @return The instructions as a dictionary.
     */
    private List<String> getInstructions() {
        return instructions;
    }

    private List<Long> getTimings() {
        return timings;
    }

    public PathStatus getStatus() {
        return status;
    }

    public enum PathStatus {Invalid, Loading, Ready}

    static {
        gson = new Gson();
    }

    public Path(List<String> instructions, List<Long> timings)
    {
        this.instructions = instructions;
        this.timings = timings;
    }

    public Path(String fileName)
    {
        this.fileName = fileName;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            File dataFile = null;
            File[] deployFiles = Filesystem.getDeployDirectory().listFiles();
            if (deployFiles == null) return;
            for (File f : deployFiles)
                if (f.getName().contains(fileName)) {
                    dataFile = f;
                    break;
                }

            if(dataFile != null) {
                status = Loading;
                instructions = gson.fromJson(new FileReader(dataFile), Path.class).getInstructions();
                status = Ready;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            status = Invalid;
        }
    }

    public void start()
    {
        started = true;
        lastCheck = System.currentTimeMillis();
    }

    public String getCurrentInstruction()
    {
        if(!started || instructions.size() == 0) return "NONE";
        long deltaTime = System.currentTimeMillis() - lastCheck;
        lastCheck = System.currentTimeMillis();

        if(timings.get(0) - deltaTime <= 0) {
            timings.set(1, timings.get(1) + timings.get(0) - deltaTime);
            timings.remove(0);
            instructions.remove(0);
        }
        else
            timings.set(0, timings.get(0) - deltaTime);
        if(instructions.size() == 0) return "NONE";

        return instructions.get(0);
    }
}
