package src.exceptions;

public class InvalidPositionError extends Exception {
    public InvalidPositionError(String errorMessage) {
        super(errorMessage);
    }
}
