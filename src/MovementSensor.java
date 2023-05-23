import java.lang.Math;
import java.util.Random;

public class MovementSensor implements iSense {

    private Controller theController;
    private int lastId = 0;
    private int sensorID;
    private double lambda;

    MovementSensor(Controller theController, double lambda, int sensorID) {
        this.theController = theController;
        this.lambda = lambda;
        this.sensorID = sensorID;
    }

    @Override
    public void run() {
        generateTasks();
    }

    @Override
    // It generates a random number of Tasksbased on the Random Poisson distribution
    // for Lambda. Then, sleeps for a second and does it again.
    public void generateTasks() {
        while (true) {
            for (int j = 0; j < generateRandomPoisson(); j++) {
                theController.addToAnalyseBuffer(generateSingleTask());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Task generateSingleTask() {
        double complexity = new Random().nextDouble(0.25);
        lastId++;
        Task temp = new Task(lastId, sensorID, complexity,
                new Random().nextInt(theController.getNumberOfActuators()) + 1);
        return temp;
    }

    private double generateRandomPoisson() {
        double temp = Math.exp(-lambda);
        double k = 0;
        double p = 1;
        while (p > temp) {
            k++;
            p = p * Math.random();
        }
        return k - 1;
    }

}
