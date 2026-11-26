# Parkly – Smart Vehicle Parking and Locator System (Java Console Core)

## Problem Statement

Drivers forget exact parked vehicle locations in large malls, university campuses, and crowded parking areas without carrying dedicated tracking hardware. Parkly provides a lightweight, tagless Java application to save, calculate distance to, and locate parked vehicles using GPS coordinates.

## Objectives & Scope

- **Current Scope (CA-3 Prototype):** Save vehicle GPS coordinates, view sorted saved vehicles, find a vehicle with exact Haversine distance (meters) and a Google Maps link, delete records, validate input, and persist records in `parkly_data.csv` (`vehicleNumber,lat,lon`).
- **Out of Scope / Future Work (CA-4 & Beyond):** JDBC/MySQL database migration, web/mobile UI, automatic live GPS capture, and optional NFC tag tap-to-save.

## Architecture & Package Structure (15 Files)

The root package and its six subpackages follow a clean layered architecture:

```text
src/parkly/
├── Main.java
├── ui/
│   ├── ConsoleUI.java
│   └── InputHelper.java
├── model/
│   ├── Locatable.java
│   ├── Location.java
│   ├── Vehicle.java
│   └── ParkingRecord.java
├── exception/
│   ├── ParklyException.java
│   ├── InvalidLocationException.java
│   └── VehicleNotFoundException.java
├── storage/
│   ├── Repository.java
│   └── FileStore.java
├── util/
│   ├── GeoValidator.java
│   └── DistanceCalculator.java
└── service/
    └── ParkingService.java
```

| File | Responsibility |
| --- | --- |
| `src/parkly/Main.java` | Composition root: wires `FileStore`, `ParkingService`, `InputHelper`, and `ConsoleUI`, then starts the app |
| `src/parkly/ui/InputHelper.java` | Wraps `Scanner` with safe `readLine()`, `readDouble()`, and `hasNextLine()` EOF checks |
| `src/parkly/ui/ConsoleUI.java` | Menu loop, `switch (1-4)`, formatted output, and `try-catch` for `ParklyException` and `NumberFormatException` |
| `src/parkly/model/Locatable.java` | Interface defining `getLocation()` and `getMapUrl()` |
| `src/parkly/model/Location.java` | Immutable GPS coordinate value object (`lat`, `lon`, getters, `getMapUrl()`) |
| `src/parkly/model/Vehicle.java` | Abstract base class holding trimmed, uppercased `vehicleNumber` and abstract `getDetails()` |
| `src/parkly/model/ParkingRecord.java` | Extends `Vehicle` and implements `Locatable` with `Location`, `getDetails()`, `toCSV()`, and `fromCSV()` |
| `src/parkly/exception/ParklyException.java` | Base checked exception extending `Exception` |
| `src/parkly/exception/InvalidLocationException.java` | Checked `ParklyException` for invalid GPS coordinates or empty vehicle numbers |
| `src/parkly/exception/VehicleNotFoundException.java` | Checked `ParklyException` for missing vehicle lookups or deletes |
| `src/parkly/storage/Repository.java` | Generic `Repository<K, V>` interface (`saveAll`, `loadAll`) |
| `src/parkly/storage/FileStore.java` | Implements `Repository<String, ParkingRecord>` using synchronized `BufferedWriter`/`BufferedReader` on `parkly_data.csv` |
| `src/parkly/util/GeoValidator.java` | Static validation for vehicle numbers and GPS coordinates (`InvalidLocationException`) |
| `src/parkly/util/DistanceCalculator.java` | Static Haversine distance calculation `haversineMeters(Location a, Location b)` using Earth radius `6371000` m |
| `src/parkly/service/ParkingService.java` | Business service using `Repository<String, ParkingRecord>`, `HashMap<String, ParkingRecord>`, Stream `.sorted()`, `GeoValidator`, and `DistanceCalculator` |

Data flow: `Main -> ConsoleUI (InputHelper) -> ParkingService -> GeoValidator / DistanceCalculator / Repository (FileStore) -> parkly_data.csv`

## Course Concepts Integrated

| Course concept | Application |
| --- | --- |
| OOP & Interfaces | Abstract `Vehicle`, `Locatable` interface, immutable `Location`, inherited `ParkingRecord`, and `Repository<K, V>` |
| Collections & Generics | Generic `Repository<K, V>` and `HashMap<String, ParkingRecord>` keyed by normalized vehicle number |
| Streams & Lambdas | `.stream().sorted().collect(Collectors.toList())` returns sorted saved vehicle numbers |
| Exception Handling | Checked hierarchy (`ParklyException`, `InvalidLocationException`, `VehicleNotFoundException`) and `NumberFormatException` handling in `ConsoleUI` |
| File I/O | `BufferedReader`/`FileReader` loads CSV records; `BufferedWriter`/`FileWriter` saves them |
| Concurrency | Synchronized `saveAll()` and `loadAll()` in `FileStore` |

## Setup, Compile & Run Instructions (PowerShell)

Install JDK 21 and run these commands from the repository root (`C:\python\Parkly`) in PowerShell:

```powershell
javac -Xlint:all -d out (Get-ChildItem -Path src/parkly -Recurse -Filter *.java).FullName
java -cp out parkly.Main
```

Or compile explicitly by package:

```powershell
javac -Xlint:all -d out src/parkly/exception/*.java src/parkly/model/*.java src/parkly/storage/*.java src/parkly/util/*.java src/parkly/service/*.java src/parkly/ui/*.java src/parkly/*.java
java -cp out parkly.Main
```

## Sample Test Evidence

Automated terminal run verifying Save (`HR26AB1234`), lowercase Find (`hr26ab1234`), invalid GPS (`999`), non-numeric input (`abc`), missing vehicle (`UNKNOWN`), and clean exit:

```text
--- PARKLY ---
Saved: []
1. Save Car
2. Find Car
3. Delete Car
4. Exit
Choice: Vehicle No: Latitude: Longitude: Location saved.

--- PARKLY ---
Saved: [HR26AB1234]
1. Save Car
2. Find Car
3. Delete Car
4. Exit
Choice: Vehicle No: Current Latitude: Current Longitude: HR26AB1234 | Lat: 30.516100, Lon: 76.659800
Distance: 193.5 m
Map Link: https://www.google.com/maps?q=30.5161,76.6598

Error: Invalid GPS coordinates.
Error: Coordinates must be numbers.
Error: Vehicle not found.
```

A second process reloaded `Saved: [HR26AB1234]` from `parkly_data.csv`, deleted `HR26AB1234` (`Location deleted.`), displayed `Saved: []`, and left `parkly_data.csv` empty. An EOF-during-prompt run also exited with status `0` and no `NoSuchElementException`. `javac -Xlint:all -d out` compiled all 15 Java source files with zero errors and zero warnings.

## Current Status & Pending Work

**Completed:** Layered 15-file Java console architecture; `Locatable`/`Location` value model; `InvalidLocationException` and `VehicleNotFoundException`; generic `Repository<K, V>`; `InputHelper` and `ConsoleUI` separation; normalized vehicle lookup; coordinate validation; Haversine distance and Maps URL; CSV persistence and reload; EOF-safe CLI; and warning-free JDK 21 compilation.

**Pending for CA-4 and beyond:** JDBC/MySQL storage option, a Java unit-test suite, and a parking history/audit log. Web/mobile interface, automatic live GPS capture, and NFC support are also future work.
