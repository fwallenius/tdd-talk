package se.wallenius;

public class Main {

    public static void main(String[] args) {

        TextFileCalculator calculator = new BadImplementation();
        calculator.open(System.getProperty("user.dir") + "/exampleInput1.txt");
        int result = calculator.parseAndCalculateResult();

        System.out.println("Result: " + result);
    }
}
