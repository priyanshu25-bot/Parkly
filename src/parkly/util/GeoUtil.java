package parkly.util;

import parkly.exception.ParklyException;

public class GeoUtil {
    private static final double EARTH_RADIUS_M = 6371000.0;

    public static void validateCoords(double lat, double lon) throws ParklyException {
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            throw new ParklyException("Invalid GPS coordinates.");
        }
    }

    public static double distanceMeters(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_M * c;
    }
}
