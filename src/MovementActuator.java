
public class MovementActuator implements iActuator {

    private ConcurrentBuffer<Result> buffer = new ConcurrentBuffer<Result>();
    private Robot theRobot;
    private int actuatorID;
    private int lastID = -1;

    MovementActuator(Robot newRobot, int actuatorID) {
        this.theRobot = newRobot;
        this.actuatorID = actuatorID;
    }

    @Override
    public void run() {
        executeTasks();
    }

    @Override
    public void executeTasks() {
        // Tries to get the next element from "buffer" and, if it is not null, it
        // calls the Robot's "move()" method and passes the Result as a parameter.
        long start = -1;
        while (true) {
            Result temp = buffer.getHead();
            if (temp != null) {
                if (start != -1) {
                    System.out.println("Actuator " + actuatorID
                            + " error: no results to process. Last task processed {" + lastID
                            + "}. Actuate has been inactive for " + (System.nanoTime() - start) / 1000000
                            + " milliseconds.");
                    start = -1;
                }
                theRobot.move(temp);
                lastID = temp.getID();
            } else if (start == -1) {
                start = System.nanoTime();
            }
        }
    }

    @Override
    public void addToBuffer(Result toAddResult) {
        buffer.addToBuffer(toAddResult);
    }

    @Override
    public int getActuatorID() {
        return actuatorID;
    }

}
