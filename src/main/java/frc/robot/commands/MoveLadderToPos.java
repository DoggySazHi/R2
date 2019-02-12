package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveLadderToPos extends Command
{
    public static final double TIMEOUT = 8.0;

    public static final int[] POSITIONS = new int[] {200, 300, 400, 500, 600, 700};

    public int pos;

    public MoveLadderToPos(int pos) {
        requires(Robot.ladder);
        setTimeout(TIMEOUT);
        setInterruptible(false);
        this.pos = pos;
    }

    protected void initialize() {}

    protected void execute() {
        switch(pos) {
            case 0:
                if(Robot.ladder.bottomLimit.get()) {
                    Robot.ladder.pos = 0;
                    Robot.ladder.encoder.reset();
                    Robot.ladder.stop();
                } else {
                    Robot.ladder.retract();
                }
                break;
            case 7:
                if(Robot.ladder.topLimit.get()) {
                    Robot.ladder.pos = 7;
                    Robot.ladder.stop();
                } else {
                    Robot.ladder.extend();
                }
                break;
            default:
                int diff = POSITIONS[pos - 1] - (int)Robot.ladder.encoder.getDistance();
                if(Math.abs(diff) < 100) {
                    Robot.ladder.pos = pos;
                    Robot.ladder.stop();
                } else if(diff > 0) {
                    Robot.ladder.extend();
                } else {
                    Robot.ladder.retract();
                }
                break;
        }
    }

    protected boolean isFinished() {
        return Robot.ladder.pos == pos;
    }

    protected void end() {
        Robot.ladder.stop();
    }

    protected void interrupted() {
        Robot.ladder.stop();
    }
}
