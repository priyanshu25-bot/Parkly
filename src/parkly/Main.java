package parkly;

import parkly.exception.ParklyException;
import parkly.model.ParkingRecord;
import parkly.service.ParkingService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ParkingService service = new ParkingService();

        while (true) {
            System.out.println("\n--- PARKLY ---");
            System.out.println("Saved: " + service.getSavedVehicles());
            System.out.println("1. Save Car");
            System.out.println("2. Find Car");
            System.out.println("3. Delete Car");
            System.out.println("4. Exit");
            System.out.print("Choice: ");

            try {
                String choice = sc.nextLine().trim();

                if (choice.equals("1")) {
                    System.out.print("Vehicle No: ");
                    String num = sc.nextLine();
                    System.out.print("Latitude: ");
                    double lat = Double.parseDouble(sc.nextLine().trim());
                    System.out.print("Longitude: ");
                    double lon = Double.parseDouble(sc.nextLine().trim());
                    service.saveCar(num, lat, lon);
                    System.out.println("Location saved.");
                } else if (choice.equals("2")) {
                    System.out.print("Vehicle No: ");
                    ParkingRecord car = service.findCar(sc.nextLine());
                    System.out.print("Current Latitude: ");
                    double lat = Double.parseDouble(sc.nextLine().trim());
                    System.out.print("Current Longitude: ");
                    double lon = Double.parseDouble(sc.nextLine().trim());
                    double dist = service.getDistanceMeters(car, lat, lon);
                    System.out.println(car.getDetails());
                    System.out.printf("Distance: %.1f m%n", dist);
                    System.out.println("Map Link: " + car.getMapUrl());
                } else if (choice.equals("3")) {
                    System.out.print("Vehicle No: ");
                    service.deleteCar(sc.nextLine());
                    System.out.println("Location deleted.");
                } else if (choice.equals("4")) {
                    sc.close();
                    break;
                } else {
                    System.out.println("Enter a valid option (1-4).");
                }
            } catch (ParklyException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: Coordinates must be numbers.");
            }
        }
    }
}
