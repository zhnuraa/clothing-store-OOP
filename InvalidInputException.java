package exception;

/**
 * Custom exception used across the project for invalid input / invalid model state.
 * Week 6: models must THROW exceptions (no printing inside setters).
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
