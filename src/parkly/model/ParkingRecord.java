package parkly.model;

import java.util.Objects;

public class ParkingRecord extends Vehicle implements Locatable {
    private final Location location;

    public ParkingRecord(String vehicleNumber, Location location) {
        super(vehicleNumber);
        this.location = Objects.requireNonNull(location, "Location cannot be null.");
    }

    public ParkingRecord(String vehicleNumber, double lat, double lon) {
        this(vehicleNumber, new Location(lat, lon));
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public String getMapUrl() {
        return location.getMapUrl();
    }

    @Override
    public String getDetails() {
        return String.format("%s | Lat: %.6f, Lon: %.6f",
                vehicleNumber, location.getLat(), location.getLon());
    }

    public String toCSV() {
        return vehicleNumber + "," + location.getLat() + "," + location.getLon();
    }

    public static ParkingRecord fromCSV(String line) {
        if (line == null) {
            throw new IllegalArgumentException("CSV line cannot be null.");
        }
        String[] parts = line.split(",", -1);
        if (parts.length != 3) {
            throw new IllegalArgumentException("A parking record must have three fields.");
        }
        String vehicle = parts[0].trim();
        if (vehicle.isEmpty()) {
            throw new IllegalArgumentException("Vehicle number cannot be empty.");
        }
        return new ParkingRecord(
                vehicle,
                new Location(
                        Double.parseDouble(parts[1].trim()),
                        Double.parseDouble(parts[2].trim())
                )
        );
    }
}
