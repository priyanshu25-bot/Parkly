package parkly;

import parkly.exception.ParklyException;
import parkly.service.ParkingService;
import parkly.storage.FileStore;
import parkly.ui.ConsoleUI;
import parkly.ui.InputHelper;

public class Main {
    private static final String DATA_FILE = "parkly_data.csv";

    public static void main(String[] args) {
        try (InputHelper input = new InputHelper(System.in)) {
            FileStore store = new FileStore(DATA_FILE);
            ParkingService service = new ParkingService(store);
            ConsoleUI ui = new ConsoleUI(service, input);
            ui.start();
        } catch (ParklyException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
