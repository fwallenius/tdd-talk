package se.wallenius;

import se.wallenius.exceptions.BadStateException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HardToTestImplementation implements TextFileCalculator {

    private Path currentPath;
    private Boolean readyToRead = false;

    @Override
    public boolean open(String fileName) {
        this.readyToRead = false;
        this.currentPath = Paths.get(fileName);

        try (BufferedReader reader = this.getReaderForPath(this.currentPath)) {

            String testLine = reader.readLine();
            if (testLine != null) {
                this.readyToRead = true;
            }

        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

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

        try (BufferedReader reader = this.getReaderForPath(this.currentPath)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (expectingNumber && isValidNumber(line)) {
                    sum = doTheMath(operation, sum, line);
                    expectingNumber = false;
                }
                else if (!expectingNumber && isValidOperation(line)) {
                    operation = parseOperator(line);
                    expectingNumber = true;
                }
                else {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Skipping line: " + line);
                }

            }

        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

        return sum;
    }

    private Operation parseOperator(String line) {
        switch (line) {
            case "+": return Operation.ADD;
            case "-": return Operation.SUBTRACT;
            case "*": return Operation.MULTIPLY;
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
            case ADD:       return currentSum + number;
            case SUBTRACT:  return currentSum - number;
            case MULTIPLY:  return currentSum * number;
        }

        return null; // Can't happen since input is first validated.
    }
    
    private BufferedReader getReaderForPath(Path path) throws IOException {
        return Files.newBufferedReader(currentPath, Charset.defaultCharset());
    }

    enum Operation {
        ADD, SUBTRACT, MULTIPLY
    }
}
