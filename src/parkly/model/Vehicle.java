package parkly.model;

public abstract class Vehicle {
    protected String vehicleNumber;

    public Vehicle(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber.trim().toUpperCase();
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public abstract String getDetails();
}
