package parkly.model;

public class ParkingRecord extends Vehicle {
    private final double lat;
    private final double lon;

    public ParkingRecord(String vehicleNumber, double lat, double lon) {
        super(vehicleNumber);
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
    public String getDetails() {
        return String.format("%s | Lat: %.6f, Lon: %.6f", vehicleNumber, lat, lon);
    }

    public String toCSV() {
        return vehicleNumber + "," + lat + "," + lon;
    }

    public static ParkingRecord fromCSV(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            return null;
        }
        return new ParkingRecord(
            parts[0],
            Double.parseDouble(parts[1]),
            Double.parseDouble(parts[2])
        );
    }
}
