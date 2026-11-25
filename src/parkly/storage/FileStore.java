package parkly.storage;

import parkly.model.ParkingRecord;
import java.io.*;
import java.util.*;

public class FileStore {
    private final String filePath;

    public FileStore(String filePath) {
        this.filePath = filePath;
    }

    public synchronized void save(Collection<ParkingRecord> records) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (ParkingRecord r : records) {
                bw.write(r.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    public Map<String, ParkingRecord> load() {
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
                ParkingRecord r = ParkingRecord.fromCSV(line.trim());
                if (r != null) {
                    map.put(r.getVehicleNumber(), r);
                }
            }
        } catch (Exception e) {
            System.out.println("Could not load data: " + e.getMessage());
        }
        return map;
    }
}
