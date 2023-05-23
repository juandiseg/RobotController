import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class driver {

    List<iSense> listSensors = new ArrayList<iSense>();
    List<iActuator> listActuators = new ArrayList<iActuator>();
    Controller theController = new Controller();
    Analyser theAnalyser;
    Robot theRobot;
    double initialPostion;
    double lambda;
    double xValue;

    driver(double initialPostion, double lambda, double xValue) {
        if (initialPostion >= 0 && initialPostion <= 1)
            this.initialPostion = initialPostion;
        if (lambda > 0)
            this.lambda = lambda;
        if (xValue >= 0)
            this.xValue = xValue;
        theRobot = new Robot(this.initialPostion);
        theAnalyser = new Analyser(theController, xValue);
    }

    private void addSensors(int numberSensors) {
        if (numberSensors <= 0)
            return;
        for (int i = 1; i < numberSensors + 1; i++) {
            listSensors.add(new MovementSensor(theController, lambda, i));
        }
    }

    private void addActuators(int numberActuators) {
        if (numberActuators <= 0)
            return;
        iActuator temp;
        for (int i = 1; i < numberActuators + 1; i++) {
            temp = new MovementActuator(theRobot, i);
            listActuators.add(temp);
            theController.addActuator(temp);
        }
    }

    private void createAndRunThreads() {
        List<Thread> listThreads = new ArrayList<Thread>();
        for (iSense s : listSensors) {
            listThreads.add(new Thread(s));
        }
        for (iActuator a : listActuators) {
            listThreads.add(new Thread(a));
        }
        listThreads.add(new Thread(theAnalyser));
        listThreads.add(new Thread(theController));
        for (Thread t : listThreads) {
            t.start();
        }
    }

    private static int askInputInt(String txt) {
        System.out.print("Please, enter " + txt);
        while (true) {
            Integer inputNumber = 0;
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            String line = "";
            try {
                line = buffer.readLine();
                inputNumber = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print(line + " is not a valid number. Please input a number: ");
                continue;
            } catch (IOException e) {
                System.out.println();
            }
            if (inputNumber > 0) {
                return inputNumber;
            }
        }
    }

    private static double askInputDouble(String txt, double max) {
        System.out.print("Please, enter " + txt);
        while (true) {
            Double inputNumber = 0.0;
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            String line = "";
            try {
                line = buffer.readLine();
                inputNumber = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println(line + " is not a valid number. Please input a number.");
                continue;
            } catch (IOException e) {
                System.out.println();
            }
            if (max == 0) {
                if (inputNumber > 0 && inputNumber < 999) {
                    return inputNumber;
                }
                System.out.println(
                        inputNumber + " is not a valid number. Please input a number greater than 0.");
            } else {
                if (inputNumber > 0 && inputNumber <= max) {
                    return inputNumber;
                }
                System.out.println(
                        inputNumber + " is not a valid number. Please input a number smaller or equal to " + max + ".");
            }
        }
    }

    public static void main(String args[]) throws Exception {

        double initialPosition = askInputDouble("the initial position of the Robot [0-1] : ", 1);
        double lambda = askInputDouble("a value for Lambda: ", 0);
        double xValue = askInputDouble("a value for X: ", 0);
        int numberActuators = askInputInt("the number of desired actuators: ");
        int numberSensors = askInputInt("the number of desired sensors: ");
        System.out.println("\nInitializing Robot.");
        Thread.sleep(1000);
        driver everything = new driver(initialPosition, lambda, xValue);
        everything.addActuators(numberActuators);
        everything.addSensors(numberSensors);
        everything.createAndRunThreads();
    }

}
