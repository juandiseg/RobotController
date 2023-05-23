public class Analyser implements Runnable {

    private Controller theController;
    private double X;

    Analyser(Controller theController, double xValue) {
        this.theController = theController;
        this.X = xValue;
    }

    @Override
    public void run() {
        analyseTasks();
    }

    // Keeps trying to get a nextTask from the Controller. When it gets it, it
    // sleeps for the value of the complexity of the Task and creates a Result which
    // is written in the Controller's actuator buffer.
    private void analyseTasks() {
        while (true) {
            Task temp = theController.getNextTask();
            if (temp != null) {
                try {
                    Thread.sleep((long) (1000 * temp.getComplexity()));
                    theController.addToActuatorBuffer(new Result(temp, Math.pow((1 - temp.getComplexity()), X)));
                } catch (InterruptedException e) {
                    System.out.println("Analyser error: unable to analyse task ID {" + temp.getID() + "}.");
                }
            }
        }
    }

}
