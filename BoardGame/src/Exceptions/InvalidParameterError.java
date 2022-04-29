package src.Exceptions;

public class InvalidParameterError extends Exception {
    public InvalidParameterError(String errorMessage) {
        super(errorMessage);
    }
}
