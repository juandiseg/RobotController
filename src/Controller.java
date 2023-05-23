import java.util.LinkedList;
import java.util.List;

public class Controller implements Runnable {

    // bufferAnalyser stores the Tasks that have to be sent to the Analyser.
    private ConcurrentBuffer<Task> bufferAnalyser = new ConcurrentBuffer<Task>();
    // bufferActuators stores the Results that have to be sent to their respective
    // Actuators.
    private ConcurrentBuffer<Result> bufferActuators = new ConcurrentBuffer<Result>();
    // A list of all Actuators.
    private List<iActuator> actuatorList = new LinkedList<iActuator>();
    int lastActuator = -1;

    @Override
    public void run() {
        sendResultsToActuators();
    }

    private void sendResultsToActuators() {
        // Tries to get the next element from bufferActuator and, if it is not null, it
        // searches in "actuatorList" for its respective Actuator and adds it to that
        // Actuator's buffer.
        while (true) {
            Result temp = bufferActuators.getHead();
            if (!actuatorList.isEmpty() && temp != null) {
                int count = 1;
                for (iActuator t : actuatorList) {
                    if (count == temp.getActuatorID()) {
                        t.addToBuffer(temp);
                    }
                    count++;
                }
            }
        }
    }

    public synchronized void addActuator(iActuator newActuator) {
        if (newActuator != null) {
            for (iActuator temp : actuatorList) {
                if (newActuator.getActuatorID() == temp.getActuatorID())
                    return;
            }
            actuatorList.add(newActuator);
        }
    }

    public void addToAnalyseBuffer(Task newTask) {
        bufferAnalyser.addToBuffer(newTask);
    }

    public void addToActuatorBuffer(Result newResult) {
        bufferActuators.addToBuffer(newResult);
    }

    public Task getNextTask() {
        return bufferAnalyser.getHead();
    }

    public int getNumberOfActuators() {
        return actuatorList.size();
    }

}