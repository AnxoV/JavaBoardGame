package src.exceptions;

public class InvalidParameterError extends Exception {
    public InvalidParameterError(String errorMessage) {
        super(errorMessage);
    }
}
