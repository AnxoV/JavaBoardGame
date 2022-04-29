package src.Exceptions;

public class InvalidPositionError extends Exception {
    public InvalidPositionError(String errorMessage) {
        super(errorMessage);
    }
}
