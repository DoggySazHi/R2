package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class MoveLadderToNextPos extends Command {
    /*
    Since nobody could seem to explain such a mess of a method, it's pretty much this:
    - Drop the ladder to the next position
    - Not die
    - Cheer for 3952 (but not their software team; some head decided to force
    two people to work alone, what the hell and claimed they did nothing >:[0)
     */

    public static final double TIMEOUT = 8.0;
    //TODO: edit
    public static final double[] POSITIONS = new double[] {100, //panel #1
                                                           200, //ball #1
                                                           300, //panel #2
                                                           400, //ball #2
                                                           500, //panel #3
                                                           600  //ball #3
                                                          };
    public static final int MIN = 0;
    public static final int MAX = 5;
    public static final int THRESHOLD = 50;

    public double oldpos;
    public double pos;
    public boolean dir;
    public boolean override;

    public Encoder encoder = Robot.ladder.encoder;
    public DigitalInput topLimit = RobotMap.ladderTopLimit;
    public DigitalInput bottomLimit = RobotMap.ladderBottomLimit;

    public MoveLadderToNextPos(boolean dir) {
        requires(Robot.ladder);
        setTimeout(TIMEOUT);
        setInterruptible(true);
        this.dir = dir;
    }

    @Override
    protected void initialize() {
        double oldpos = encoder.getDistance();
        if(dir) {
            int position = MIN;
            while(oldpos >= POSITIONS[position] && position < MAX) {
                position++;
            }
            pos = POSITIONS[Math.min(MAX, position + 1)];
        } else {
            int position = MAX;
            while(oldpos <= POSITIONS[position] && position > MIN) {
                position--;
            }
            pos = POSITIONS[Math.min(MIN, position - 1)];
        }
    }

    @Override
    protected void execute() {
        //tahnk you c# for teaching me this
        override |= Robot.ladderController.override();
        Robot.ladder.goTo(oldpos, pos);
    }

    @Override
    protected boolean isFinished() {
        if(topLimit.get() || bottomLimit.get() || override)
            return true;
        //stop if it is within a threshold, or just simply "past" it if the robotrio didn't update encoder values fast enough
        return Math.abs(encoder.get() - pos) <= THRESHOLD || oldpos < pos ? encoder.get() > pos : encoder.get() < pos;
    }

    @Override
    protected void end() {
        Robot.ladder.stop();
    }

    @Override
    protected void interrupted() {
        Robot.ladder.stop();
    }
}