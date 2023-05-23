public class Task {

    private int id;
    private int sensorID;
    private double complexity;
    private int actuatorID;

    Task(int newId, int sensorID, double newComplexity, int newActuatorID) {
        setID(newId);
        setSensorID(sensorID);
        setComplexity(newComplexity);
        setActuatorID(newActuatorID);
    }

    public int getID() {
        return this.id;
    }

    public int getSensorID() {
        return sensorID;
    }

    public double getComplexity() {
        return this.complexity;
    }

    public int getActuatorID() {
        return actuatorID;
    }

    private void setID(int newId) {
        if (newId > 0)
            this.id = newId;
        else
            this.id = -1;
    }

    private void setSensorID(int newID) {
        if (newID <= 0)
            this.sensorID = -1;
        else
            this.sensorID = newID;
    }

    private void setComplexity(double newComplexity) {
        if (newComplexity > 0)
            this.complexity = newComplexity;
        else
            this.complexity = 0.1;
    }

    private void setActuatorID(int newID) {
        if (newID <= 0)
            this.actuatorID = -1;
        else
            this.actuatorID = newID;
    }
}
