package parkly;

import parkly.exception.ParklyException;
import parkly.model.ParkingRecord;
import parkly.service.ParkingService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ParkingService service;
            try {
                service = new ParkingService();
            } catch (ParklyException e) {
                System.err.println("Error: " + e.getMessage());
                return;
            }

            while (true) {
                System.out.println("\n--- PARKLY ---");
                System.out.println("Saved: " + service.getSavedVehicles());
                System.out.println("1. Save Car");
                System.out.println("2. Find Car");
                System.out.println("3. Delete Car");
                System.out.println("4. Exit");
                System.out.print("Choice: ");
                if (!sc.hasNextLine()) {
                    break;
                }

                String choice = sc.nextLine().trim();
                try {
                    switch (choice) {
                        case "1" -> {
                            String vehicleNumber = readInput(sc, "Vehicle No: ");
                            if (vehicleNumber == null) {
                                return;
                            }
                            String latitudeInput = readInput(sc, "Latitude: ");
                            if (latitudeInput == null) {
                                return;
                            }
                            double latitude = Double.parseDouble(latitudeInput.trim());
                            String longitudeInput = readInput(sc, "Longitude: ");
                            if (longitudeInput == null) {
                                return;
                            }
                            double longitude = Double.parseDouble(longitudeInput.trim());
                            service.saveCar(vehicleNumber, latitude, longitude);
                            System.out.println("Location saved.");
                        }
                        case "2" -> {
                            String vehicleNumber = readInput(sc, "Vehicle No: ");
                            if (vehicleNumber == null) {
                                return;
                            }
                            ParkingRecord car = service.findCar(vehicleNumber);
                            String latitudeInput = readInput(sc, "Current Latitude: ");
                            if (latitudeInput == null) {
                                return;
                            }
                            double latitude = Double.parseDouble(latitudeInput.trim());
                            String longitudeInput = readInput(sc, "Current Longitude: ");
                            if (longitudeInput == null) {
                                return;
                            }
                            double longitude = Double.parseDouble(longitudeInput.trim());
                            double distance = service.getDistanceMeters(car, latitude, longitude);
                            System.out.println(car.getDetails());
                            System.out.printf("Distance: %.1f m%n", distance);
                            System.out.println("Map Link: " + car.getMapUrl());
                        }
                        case "3" -> {
                            String vehicleNumber = readInput(sc, "Vehicle No: ");
                            if (vehicleNumber == null) {
                                return;
                            }
                            service.deleteCar(vehicleNumber);
                            System.out.println("Location deleted.");
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
    }

    private static String readInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }
}
