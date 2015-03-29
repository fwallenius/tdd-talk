package se.wallenius.exceptions;

/**
 * Created by fwallenius on 29/03/15.
 */
public class FileOpenException extends RuntimeException {

    public FileOpenException(String message, Throwable cause) {
        super(message, cause);
    }
}
