package se.wallenius;


import org.junit.Assert;
import org.junit.Test;
import se.wallenius.exceptions.BadStateException;
import se.wallenius.fileops.MyFileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EasyToTestImplementationTests {

    @Test
    public void openShouldReturnFalseWhenFileOpenFails() {
        MockFileReader mock = new MockFileReader(false, null);
        TextFileCalculator underTest = new EasyToTestImplementation(mock);

        Assert.assertEquals(false, underTest.open("mock"));
    }

    @Test(expected = BadStateException.class)
    public void shouldThrowExceptionIfTryingToReadWithNoFile() {
        MockFileReader mock = new MockFileReader(false, null);
        TextFileCalculator underTest = new EasyToTestImplementation(mock);

        underTest.parseAndCalculateResult();
    }

    @Test
    public void shouldHandleEmptyInput() {
        String[] input = new String[0];
        MockFileReader mock = new MockFileReader(true, new ArrayList<>(Arrays.asList(input)));
        TextFileCalculator underTest = new EasyToTestImplementation(mock);

        underTest.open("mock");
        Assert.assertEquals((Integer) 0, underTest.parseAndCalculateResult());
    }

    @Test
    public void shouldHandleAddition() {
        String[] input = new String[]{
                "1",
                "+",
                "2"
        };
        MockFileReader mock = new MockFileReader(true, new ArrayList<>(Arrays.asList(input)));
        TextFileCalculator underTest = new EasyToTestImplementation(mock);

        underTest.open("mock");
        Assert.assertEquals((Integer) 3, underTest.parseAndCalculateResult());
    }

    @Test
    public void shouldHandleMultiplication() {
        String[] input = new String[]{
                "1",
                "*",
                "2"
        };
        MockFileReader mock = new MockFileReader(true, new ArrayList<>(Arrays.asList(input)));
        TextFileCalculator underTest = new EasyToTestImplementation(mock);

        underTest.open("mock");
        Assert.assertEquals((Integer) 2, underTest.parseAndCalculateResult());
    }

    @Test
    public void shouldHandleSubtraction() {
        String[] input = new String[]{
                "2",
                "-",
                "1"
        };
        MockFileReader mock = new MockFileReader(true, new ArrayList<>(Arrays.asList(input)));
        TextFileCalculator underTest = new EasyToTestImplementation(mock);

        underTest.open("mock");
        Assert.assertEquals((Integer) 1, underTest.parseAndCalculateResult());
    }

    @Test
    public void shouldNeglectStrangeLines() {
        String[] input = new String[]{
                " ",
                " 22 ",
                "some comment",
                "*****",
                "!",
                "1",
                "+",
                "2"
        };
        MockFileReader mock = new MockFileReader(true, new ArrayList<>(Arrays.asList(input)));
        TextFileCalculator underTest = new EasyToTestImplementation(mock);

        underTest.open("mock");
        Assert.assertEquals((Integer) 3, underTest.parseAndCalculateResult());
    }


    private class MockFileReader implements MyFileReader {

        private int currentLine = 0;
        private List<String> lines;
        private boolean openResponse;

        private MockFileReader(boolean openResponse, List<String> lines) {
            this.openResponse = openResponse;
            this.lines = lines;
        }

        @Override
        public boolean open(String fileName) {
            return openResponse;
        }

        @Override
        public String nextLine() {
            if (this.lines != null && this.currentLine < this.lines.size()) {
                return this.lines.get(currentLine++);
            } else {
                return null;
            }
        }

        @Override
        public void close() {

        }
    }
}

