package parkly.ui;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class InputHelper implements AutoCloseable {
    private final Scanner scanner;

    public InputHelper(InputStream in) {
        this(new Scanner(Objects.requireNonNull(in, "Input stream cannot be null.")));
    }

    public InputHelper(Scanner scanner) {
        this.scanner = Objects.requireNonNull(scanner, "Scanner cannot be null.");
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public String readLine() {
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return readLine();
    }

    public Double readDouble() {
        String line = readLine();
        if (line == null) {
            return null;
        }
        return Double.valueOf(line.trim());
    }

    public Double readDouble(String prompt) {
        System.out.print(prompt);
        return readDouble();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
