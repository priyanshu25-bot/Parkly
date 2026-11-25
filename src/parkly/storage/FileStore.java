package parkly.storage;

import parkly.exception.ParklyException;
import parkly.model.ParkingRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileStore {
    private final String filePath;

    public FileStore(String filePath) {
        this.filePath = filePath;
    }

    public synchronized void save(Collection<ParkingRecord> records) throws ParklyException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (ParkingRecord r : records) {
                bw.write(r.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ParklyException("Could not save data: " + filePath, e);
        }
    }

    public Map<String, ParkingRecord> load() throws ParklyException {
        Map<String, ParkingRecord> map = new HashMap<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return map;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    ParkingRecord record = ParkingRecord.fromCSV(line.trim());
                    map.put(record.getVehicleNumber(), record);
                } catch (IllegalArgumentException e) {
                    throw new ParklyException("Could not load data: invalid record in " + filePath, e);
                }
            }
        } catch (IOException e) {
            throw new ParklyException("Could not load data: " + filePath, e);
        }
        return map;
    }
}
