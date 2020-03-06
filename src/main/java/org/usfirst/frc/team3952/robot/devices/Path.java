package org.usfirst.frc.team3952.robot.devices;

import com.google.gson.Gson;
import edu.wpi.first.wpilibj.Filesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.usfirst.frc.team3952.robot.devices.Path.PathStatus.*;

/**
 * A class that handles the path data, reading from a JSON file in the <code>deploy</code> folder.
 *
 * @author DoggySazHi (touhou addict)
 */
public class Path implements Runnable {
    private static final Gson gson;

    /**
     * Instructions available are:
     * MOVE TIME X Y Z QUICK-TURN
     * LIFT TOP/BOTTOM
     * TILT X
     * TURN DEGREES
     * AUTOALIGN
     * END
     */
    private List<String> instructions;
    private transient PathStatus status = Unloaded;
    private transient String fileName;

    /**
     * Get all instructions associated with the path.
     * @return The instructions as a dictionary.
     */
    public List<String> getInstructions() {
        return instructions;
    }

    public PathStatus getStatus() {
        return status;
    }

    public enum PathStatus {Unloaded, Invalid, Loading, Ready}

    static {
        gson = new Gson();
    }

    public Path(List<String> instructions)
    {
        this.instructions = instructions;
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
            else
                throw new FileNotFoundException("JSON file could not be found! Did you place it in the deploy folder, and name it correctly?");
        }
        catch (Exception e) {
            e.printStackTrace();
            status = Invalid;
        }
        finally {
            if(status == Unloaded)
                status = Invalid;
        }
    }
}
