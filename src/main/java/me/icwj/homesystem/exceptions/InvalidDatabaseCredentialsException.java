package me.icwj.homesystem.exceptions;

public class InvalidDatabaseCredentialsException extends RuntimeException {

    public InvalidDatabaseCredentialsException() {
        super();
    }

    public InvalidDatabaseCredentialsException(final String message) {
        super(message);
    }

    public InvalidDatabaseCredentialsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidDatabaseCredentialsException(final Throwable cause) {
        super(cause);
    }
}