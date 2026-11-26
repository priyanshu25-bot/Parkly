package parkly.util;

import parkly.exception.InvalidLocationException;

public final class GeoValidator {
    private GeoValidator() {
    }

    public static String validateVehicleNumber(String vehicleNumber) throws InvalidLocationException {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new InvalidLocationException("Vehicle number cannot be empty.");
        }
        return vehicleNumber.trim().toUpperCase();
    }

    public static void validateCoordinates(double lat, double lon) throws InvalidLocationException {
        if (!Double.isFinite(lat) || lat < -90.0 || lat > 90.0
                || !Double.isFinite(lon) || lon < -180.0 || lon > 180.0) {
            throw new InvalidLocationException("Invalid GPS coordinates.");
        }
    }
}
