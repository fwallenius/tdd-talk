package se.wallenius;

public class Adder {

    public Integer add(Integer one, Integer two) {

        if (one == null || two == null) {
            return null;
        }

        return  one + two;
    }

}
