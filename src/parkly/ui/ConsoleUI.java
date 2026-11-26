package parkly.ui;

import parkly.exception.ParklyException;
import parkly.model.ParkingRecord;
import parkly.service.ParkingService;

import java.util.Objects;

public class ConsoleUI {
    private final ParkingService service;
    private final InputHelper input;

    public ConsoleUI(ParkingService service, InputHelper input) {
        this.service = Objects.requireNonNull(service, "ParkingService cannot be null.");
        this.input = Objects.requireNonNull(input, "InputHelper cannot be null.");
    }

    public void start() {
        while (true) {
            printMenu();
            System.out.print("Choice: ");
            if (!input.hasNextLine()) {
                break;
            }
            String choice = input.readLine();
            if (choice == null) {
                break;
            }

            try {
                switch (choice.trim()) {
                    case "1" -> {
                        if (!handleSaveCar()) {
                            return;
                        }
                    }
                    case "2" -> {
                        if (!handleFindCar()) {
                            return;
                        }
                    }
                    case "3" -> {
                        if (!handleDeleteCar()) {
                            return;
                        }
                    }
                    case "4" -> {
                        return;
                    }
                    default -> System.out.println("Enter a valid option (1-4).");
                }
            } catch (ParklyException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: Coordinates must be numbers.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- PARKLY ---");
        System.out.println("Saved: " + service.getSavedVehicles());
        System.out.println("1. Save Car");
        System.out.println("2. Find Car");
        System.out.println("3. Delete Car");
        System.out.println("4. Exit");
    }

    private boolean handleSaveCar() throws ParklyException {
        String vehicleNumber = input.readLine("Vehicle No: ");
        if (vehicleNumber == null) {
            return false;
        }
        Double latitude = input.readDouble("Latitude: ");
        if (latitude == null) {
            return false;
        }
        Double longitude = input.readDouble("Longitude: ");
        if (longitude == null) {
            return false;
        }
        service.saveCar(vehicleNumber, latitude, longitude);
        System.out.println("Location saved.");
        return true;
    }

    private boolean handleFindCar() throws ParklyException {
        String vehicleNumber = input.readLine("Vehicle No: ");
        if (vehicleNumber == null) {
            return false;
        }
        ParkingRecord car = service.findCar(vehicleNumber);
        Double latitude = input.readDouble("Current Latitude: ");
        if (latitude == null) {
            return false;
        }
        Double longitude = input.readDouble("Current Longitude: ");
        if (longitude == null) {
            return false;
        }
        double distance = service.getDistanceMeters(car, latitude, longitude);
        System.out.println(car.getDetails());
        System.out.printf("Distance: %.1f m%n", distance);
        System.out.println("Map Link: " + car.getMapUrl());
        return true;
    }

    private boolean handleDeleteCar() throws ParklyException {
        String vehicleNumber = input.readLine("Vehicle No: ");
        if (vehicleNumber == null) {
            return false;
        }
        service.deleteCar(vehicleNumber);
        System.out.println("Location deleted.");
        return true;
    }
}
