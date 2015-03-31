package se.wallenius;

import se.wallenius.exceptions.BadStateException;
import se.wallenius.fileops.MyFileReader;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EasyToTestImplementation implements TextFileCalculator {

    private Boolean readyToRead = false;
    private MyFileReader reader;

    public EasyToTestImplementation(MyFileReader reader) {
        this.reader = reader;
    }

    @Override
    public boolean open(String fileName) {
        this.readyToRead = reader.open(fileName);
        return this.readyToRead;
    }

    @Override
    public Integer parseAndCalculateResult() {
        if (!this.readyToRead) {
            throw new BadStateException("I have no file to read from");
        }

        boolean expectingNumber = true;
        Integer sum = 0;
        Operation operation = Operation.ADD;

        String line;
        while ((line = reader.nextLine()) != null) {
            if (expectingNumber && isValidNumber(line)) {
                sum = doTheMath(operation, sum, line);
                expectingNumber = false;
            } else if (!expectingNumber && isValidOperation(line)) {
                operation = parseOperator(line);
                expectingNumber = true;
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Skipping line: " + line);
            }

        }
        this.reader.close();

        return sum;
    }

    private Operation parseOperator(String line) {
        switch (line) {
            case "+":
                return Operation.ADD;
            case "-":
                return Operation.SUBTRACT;
            case "*":
                return Operation.MULTIPLY;
        }

        return null; // Can't happen since input is first validated.
    }

    private boolean isValidOperation(String input) {
        return input.matches("^(\\+|\\-|\\*)$");
    }

    private boolean isValidNumber(String input) {
        return input.matches("^\\d+$");
    }

    private Integer doTheMath(Operation nextOperation, Integer currentSum, String inputNumber) {
        Integer number = Integer.parseInt(inputNumber, 10);

        switch (nextOperation) {
            case ADD:
                return currentSum + number;
            case SUBTRACT:
                return currentSum - number;
            case MULTIPLY:
                return currentSum * number;
        }

        return null; // Can't happen since input is first validated.
    }

    enum Operation {
        ADD, SUBTRACT, MULTIPLY
    }
}
