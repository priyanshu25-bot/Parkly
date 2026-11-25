package parkly.service;

import parkly.exception.ParklyException;
import parkly.model.ParkingRecord;
import parkly.storage.FileStore;
import parkly.util.GeoUtil;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingService {
    private final Map<String, ParkingRecord> records;
    private final FileStore store;

    public ParkingService() {
        this.store = new FileStore("parkly_data.csv");
        this.records = store.load();
    }

    public void saveCar(String num, double lat, double lon) throws ParklyException {
        if (num == null || num.trim().isEmpty()) {
            throw new ParklyException("Vehicle number cannot be empty.");
        }
        GeoUtil.validateCoords(lat, lon);
        ParkingRecord record = new ParkingRecord(num, lat, lon);
        records.put(record.getVehicleNumber(), record);
        store.save(records.values());
    }

    public ParkingRecord findCar(String num) throws ParklyException {
        ParkingRecord record = records.get(num.trim().toUpperCase());
        if (record == null) {
            throw new ParklyException("Vehicle not found.");
        }
        return record;
    }

    public void deleteCar(String num) throws ParklyException {
        if (records.remove(num.trim().toUpperCase()) == null) {
            throw new ParklyException("Vehicle not found.");
        }
        store.save(records.values());
    }

    public List<String> getSavedVehicles() {
        return records.keySet().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public double getDistanceMeters(ParkingRecord car, double curLat, double curLon) throws ParklyException {
        GeoUtil.validateCoords(curLat, curLon);
        return GeoUtil.distanceMeters(curLat, curLon, car.getLat(), car.getLon());
    }
}
