package se.wallenius;

import org.junit.Assert;
import org.junit.Test;

public class AdderTests {


    @Test
    public void shouldCorrectlyAddIntegers() {
        Adder adder = new Adder();
        Integer result = adder.add(10, 10);

        Assert.assertEquals((Integer) 20, result);
    }

    @Test
    public void shouldReturnNullIfAnyInputIsNull() {
        Adder adder = new Adder();
        Integer result = adder.add(10, null);

        Assert.assertNull("Expected to get null back", result);
    }


}
