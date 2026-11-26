package parkly.service;

import parkly.exception.ParklyException;
import parkly.exception.VehicleNotFoundException;
import parkly.model.Location;
import parkly.model.ParkingRecord;
import parkly.storage.Repository;
import parkly.util.DistanceCalculator;
import parkly.util.GeoValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ParkingService {
    private final HashMap<String, ParkingRecord> records;
    private final Repository<String, ParkingRecord> repository;

    public ParkingService(Repository<String, ParkingRecord> repository) throws ParklyException {
        this.repository = Objects.requireNonNull(repository, "Repository cannot be null.");
        this.records = new HashMap<>(repository.loadAll());
    }

    public void saveCar(String vehicleNumber, double latitude, double longitude) throws ParklyException {
        String key = GeoValidator.validateVehicleNumber(vehicleNumber);
        GeoValidator.validateCoordinates(latitude, longitude);
        ParkingRecord record = new ParkingRecord(key, new Location(latitude, longitude));
        ParkingRecord previous = records.put(key, record);
        try {
            repository.saveAll(records.values());
        } catch (ParklyException e) {
            if (previous == null) {
                records.remove(key);
            } else {
                records.put(key, previous);
            }
            throw e;
        }
    }

    public ParkingRecord findCar(String vehicleNumber) throws ParklyException {
        String key = GeoValidator.validateVehicleNumber(vehicleNumber);
        ParkingRecord record = records.get(key);
        if (record == null) {
            throw new VehicleNotFoundException("Vehicle not found.");
        }
        return record;
    }

    public void deleteCar(String vehicleNumber) throws ParklyException {
        String key = GeoValidator.validateVehicleNumber(vehicleNumber);
        ParkingRecord removed = records.remove(key);
        if (removed == null) {
            throw new VehicleNotFoundException("Vehicle not found.");
        }
        try {
            repository.saveAll(records.values());
        } catch (ParklyException e) {
            records.put(key, removed);
            throw e;
        }
    }

    public List<String> getSavedVehicles() {
        return records.keySet().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public double getDistanceMeters(ParkingRecord car, double latitude, double longitude) throws ParklyException {
        if (car == null) {
            throw new VehicleNotFoundException("Vehicle not found.");
        }
        GeoValidator.validateCoordinates(latitude, longitude);
        return DistanceCalculator.haversineMeters(new Location(latitude, longitude), car.getLocation());
    }
}
