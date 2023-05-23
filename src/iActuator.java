public interface iActuator extends Runnable {

    public abstract void addToBuffer(Result toAddResult);

    public abstract void executeTasks();

    public abstract int getActuatorID();
}
