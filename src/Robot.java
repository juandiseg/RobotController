import java.text.DecimalFormat;

public class Robot {

    private final DecimalFormat rounding = new DecimalFormat("0.000");
    private double currentPosition;
    private double lastPosition = 0;
    private boolean movingRight = true;

    Robot(double initialPosition) {
        if (initialPosition >= 0 && initialPosition <= 1)
            this.currentPosition = initialPosition;
        else {
            System.out.println("Invalid input for the Robot's initial position. It will be defaulted to 0.");
            currentPosition = 0;
        }
    }

    // This method is called by the Actuators and makes the Robot 'move'. Based on
    // whether "movingRight" is true or not it determines in which direction to
    // move and calls "moveRight()" or "moveLeft()". If the distance to move is more
    // than the space left at right or left then "moveRight()" or "moveLeft()" call
    // the opposite recursively until all the distance is covered.
    public synchronized void move(Result resultTask) {
        if (resultTask.getY() <= 0)
            return;
        if (movingRight) {
            moveRight(resultTask.getY(), currentPosition);
            print(resultTask);
            return;
        }
        moveLeft(resultTask.getY(), currentPosition);
        print(resultTask);
    }

    private void moveRight(double distanceToMove, double originalPosition) {
        double distanceLeftAtRight = 1 - currentPosition;
        if (distanceToMove > distanceLeftAtRight) {
            currentPosition = 1;
            moveLeft(distanceToMove - distanceLeftAtRight, originalPosition);
        } else {
            lastPosition = originalPosition;
            currentPosition += distanceToMove;
            movingRight = true;
        }
    }

    private void moveLeft(double distanceToMove, double originalPosition) {
        double distanceLeftAtLeft = currentPosition;
        if (distanceToMove > distanceLeftAtLeft && distanceToMove > 0) {
            currentPosition = 0;
            moveRight(distanceToMove - distanceLeftAtLeft, originalPosition);
        } else {
            lastPosition = originalPosition;
            currentPosition -= distanceToMove;
            movingRight = false;
        }
    }

    private void print(Result temp) {
        System.out.println(
                "Robot moving. Task ID {" + temp.getID() + "}, sensor ID {" + temp.getSensorID() + "}, actuator ID {"
                        + temp.getActuatorID() + "} task complexity {"
                        + rounding.format(temp.getComplexity()) + "}, result {" + rounding.format(temp.getY())
                        + "}, old position: {" + rounding.format(lastPosition) + "}, new position: {"
                        + rounding.format(currentPosition) + "}.");
    }

}