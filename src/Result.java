public class Result extends Task {

    private double Y;

    Result(Task prevTask, double Y) {
        super(prevTask.getID(), prevTask.getSensorID(), prevTask.getComplexity(), prevTask.getActuatorID());
        setY(Y);
    }

    private void setY(double Y) {
        if (Y < 0)
            this.Y = 0;
        else
            this.Y = Y;
    }

    public double getY() {
        return Y;
    }

}
