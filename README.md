# Parkly – Smart Vehicle Parking and Locator System (Java Console Core)

## Problem Statement (3 marks)

Drivers forget exact parked vehicle locations in large malls, university campuses, and crowded parking areas without carrying dedicated tracking hardware. Parkly provides a lightweight, tagless Java application to save, calculate distance to, and locate parked vehicles using GPS coordinates.

## Objectives & Scope (2 marks)

- **Current Scope (CA-3 Prototype):** Save vehicle GPS coordinates, view sorted saved vehicles, find a vehicle with exact Haversine distance (meters) and a clickable Google Maps link, delete records, validate input, and persist records in a CSV file.
- **Out of Scope / Future Work (CA-4 & Beyond):** JDBC/MySQL database migration, web/mobile UI, automatic live GPS capture, and optional NFC tag tap-to-save.

## Architecture & Package Structure (9 marks)

The root package and its five subpackages contain the seven Java source files:

```text
src/parkly/
├── Main.java
├── model/
│   ├── Vehicle.java
│   └── ParkingRecord.java
├── exception/
│   └── ParklyException.java
├── service/
│   └── ParkingService.java
├── storage/
│   └── FileStore.java
└── util/
    └── GeoUtil.java
```

| Package | Responsibility |
| --- | --- |
| `parkly` | Console menu and user input/output |
| `parkly.model` | Vehicle and saved parking record models |
| `parkly.exception` | Checked application exception |
| `parkly.service` | Parking operations and in-memory records |
| `parkly.storage` | CSV file persistence |
| `parkly.util` | GPS validation and Haversine calculations |

Data flow: `Main -> ParkingService -> GeoUtil / FileStore -> parkly_data.csv`

`ParkingService` loads records when the application starts and writes updates after saves and deletes. `GeoUtil` validates coordinates and calculates distance using the Haversine formula.

## Course Concepts Integrated (6 marks)

| Course concept | Application |
| --- | --- |
| OOP | Abstract `Vehicle`, inherited `ParkingRecord`, overridden `getDetails()`, and encapsulated latitude/longitude fields |
| Collections | `HashMap<String, ParkingRecord>` stores current records by normalized vehicle number |
| Streams & Lambdas | `.stream().sorted().collect(Collectors.toList())` returns sorted saved vehicle numbers |
| Exception Handling | Checked `ParklyException`, `try-catch` in the CLI, and `NumberFormatException` handling for coordinate input |
| File I/O | `BufferedReader`/`FileReader` loads CSV records; `BufferedWriter`/`FileWriter` saves them |
| Concurrency | Synchronized file writes in `FileStore.save()` |

## Setup, Compile & Run Instructions

Install JDK 21 and run these commands from the repository root in PowerShell:

```powershell
javac -d out src/parkly/model/*.java src/parkly/exception/*.java src/parkly/storage/*.java src/parkly/util/*.java src/parkly/service/*.java src/parkly/Main.java
java -cp out parkly.Main
```

To enable all compiler lint checks:

```powershell
javac -Xlint:all -d out src/parkly/model/*.java src/parkly/exception/*.java src/parkly/storage/*.java src/parkly/util/*.java src/parkly/service/*.java src/parkly/Main.java
```

## Sample Test Evidence

Automated terminal run with a saved vehicle, a lowercase lookup, invalid GPS, non-numeric input, a missing vehicle, and a clean exit:

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

A fresh second process loaded `HR26AB1234` from `parkly_data.csv`, deleted it, and displayed `Saved: []`. The CSV contained `HR26AB1234,30.5161,76.6598` after the first run and was empty after the second. `javac -Xlint:all` compiled all seven sources with zero errors and zero warnings. An EOF-during-prompt check also exited without `NoSuchElementException`.

## Current Status & Pending Work

**Completed:** CA-3 Java console core; seven-file package structure; normalized vehicle lookup; coordinate validation; Haversine distance and Maps URL; sorted saved-vehicle listing; checked exception handling; CSV persistence and reload; delete operation; EOF-safe CLI; and warning-free JDK 21 compilation.

**Pending for CA-4 and beyond:** JDBC/MySQL storage option, a Java unit-test suite, and a parking history/audit log. Web/mobile interface, automatic live GPS capture, and NFC support are also future work.
