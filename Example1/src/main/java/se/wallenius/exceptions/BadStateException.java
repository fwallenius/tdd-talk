package se.wallenius.exceptions;

public class BadStateException extends RuntimeException {

    public BadStateException(String message) {
        super(message);
    }
}
