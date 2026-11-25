package parkly.exception;

public class ParklyException extends Exception {
    private static final long serialVersionUID = 1L;

    public ParklyException(String message) {
        super(message);
    }

    public ParklyException(String message, Throwable cause) {
        super(message, cause);
    }
}
