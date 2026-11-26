package parkly.exception;

public class VehicleNotFoundException extends ParklyException {
    private static final long serialVersionUID = 1L;

    public VehicleNotFoundException(String message) {
        super(message);
    }

    public VehicleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
