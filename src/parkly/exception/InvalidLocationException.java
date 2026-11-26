package parkly.exception;

public class InvalidLocationException extends ParklyException {
    private static final long serialVersionUID = 1L;

    public InvalidLocationException(String message) {
        super(message);
    }

    public InvalidLocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
