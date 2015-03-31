package se.wallenius;

import se.wallenius.fileops.DefaultFileReader;

public class Main {

    public static void main(String[] args) {

        TextFileCalculator calculator = new EasyToTestImplementation(new DefaultFileReader());
        calculator.open(System.getProperty("user.dir") + "/exampleInput1.txt");

        int result = calculator.parseAndCalculateResult();

        System.out.println("Result: " + result);
    }
}
