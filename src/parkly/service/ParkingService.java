package parkly.service;

import parkly.exception.ParklyException;
import parkly.model.ParkingRecord;
import parkly.storage.FileStore;
import parkly.util.GeoUtil;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingService {
    private final HashMap<String, ParkingRecord> records;
    private final FileStore store;

    public ParkingService() throws ParklyException {
        this.store = new FileStore("parkly_data.csv");
        this.records = new HashMap<>(store.load());
    }

    public void saveCar(String vehicleNumber, double latitude, double longitude) throws ParklyException {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new ParklyException("Vehicle number cannot be empty.");
        }
        GeoUtil.validate(latitude, longitude);
        ParkingRecord record = new ParkingRecord(vehicleNumber, latitude, longitude);
        String key = record.getVehicleNumber();
        ParkingRecord previous = records.put(key, record);
        try {
            store.save(records.values());
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
        ParkingRecord record = records.get(vehicleNumber.trim().toUpperCase());
        if (record == null) {
            throw new ParklyException("Vehicle not found.");
        }
        return record;
    }

    public void deleteCar(String vehicleNumber) throws ParklyException {
        String key = vehicleNumber.trim().toUpperCase();
        ParkingRecord removed = records.remove(key);
        if (removed == null) {
            throw new ParklyException("Vehicle not found.");
        }
        try {
            store.save(records.values());
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
        GeoUtil.validate(latitude, longitude);
        return GeoUtil.distanceMeters(latitude, longitude, car.getLat(), car.getLon());
    }
}
