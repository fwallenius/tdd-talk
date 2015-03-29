package se.wallenius;


/**
 * Implementations should open a text file of a specific format
 * and calculate the result of the numbers and arithmetic operations specified
 * in the file.
 */
public interface TextFileCalculator {

    /**
     * Try to open the file with the given path and name.
     *
     * @param fileName Full path and name of text file to read from.
     * @return True if file was successfully opened, false otherwise.
     */
    boolean open(String fileName);

    /**
     * Reads file line by line and if it follows the correct format
     * this method returns the result.
     *
     * @return The sum of the arithmetic operations.
     */
    Integer parseAndCalculateResult();

}
