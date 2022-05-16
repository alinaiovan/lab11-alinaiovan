package ro.ubb.lab11.core.exceptions;

public class AirlineException extends RuntimeException{
    public AirlineException(String message) {
        super(message);
    }

    public AirlineException(String message, Throwable cause) {
        super(message, cause);
    }

    public AirlineException(Throwable cause) {
        super(cause);
    }
}
