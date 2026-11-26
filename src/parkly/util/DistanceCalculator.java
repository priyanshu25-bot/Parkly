package parkly.util;

import parkly.model.Location;

public final class DistanceCalculator {
    private static final double EARTH_RADIUS_METERS = 6371000.0;

    private DistanceCalculator() {
    }

    public static double haversineMeters(Location a, Location b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Locations cannot be null.");
        }
        double dLat = Math.toRadians(b.getLat() - a.getLat());
        double dLon = Math.toRadians(b.getLon() - a.getLon());
        double sinDLat = Math.sin(dLat / 2.0);
        double sinDLon = Math.sin(dLon / 2.0);
        double haversine = sinDLat * sinDLat
                + Math.cos(Math.toRadians(a.getLat()))
                * Math.cos(Math.toRadians(b.getLat()))
                * sinDLon * sinDLon;
        haversine = Math.min(1.0, Math.max(0.0, haversine));
        double c = 2.0 * Math.atan2(Math.sqrt(haversine), Math.sqrt(1.0 - haversine));
        return EARTH_RADIUS_METERS * c;
    }
}
