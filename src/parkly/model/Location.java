package parkly.model;

import java.util.Objects;

public final class Location {
    private final double lat;
    private final double lon;

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getMapUrl() {
        return "https://www.google.com/maps?q=" + lat + "," + lon;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Location other)) {
            return false;
        }
        return Double.compare(lat, other.lat) == 0 && Double.compare(lon, other.lon) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }

    @Override
    public String toString() {
        return String.format("Lat: %.6f, Lon: %.6f", lat, lon);
    }
}
