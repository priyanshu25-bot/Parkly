# Parkly - Smart Vehicle Parking and Locator System

## Problem Statement

People often forget where they parked their vehicle in big malls, college campuses, or crowded markets. Dedicated GPS trackers cost extra money, so Parkly solves this as a simple tagless software utility that stores and locates parked vehicles using GPS coordinates.

## Objectives and Scope

Current prototype (Console version):

- Save a vehicle's parked latitude and longitude.
- Find a saved vehicle, calculate distance from current coordinates using the Haversine formula, and generate a Google Maps link.
- Delete saved locations.
- Persist saved vehicles locally using a CSV file.

Future scope:

- Web or mobile interface with automatic GPS reading.
- Database storage using JDBC.
- Optional NFC tag support for quick tap-to-save.

## Java Concepts Used

- OOP: Abstract class (Vehicle), Inheritance (ParkingRecord), Method overriding (getDetails).
- Collections and Streams: HashMap for storing vehicle records and Stream API for sorting vehicle numbers.
- Exception Handling: Custom ParklyException and try-catch validation for invalid inputs.
- File I/O and Synchronization: BufferedReader and BufferedWriter for CSV storage with synchronized save.

## How to Compile and Run

javac -d out src/parkly/model/*.java src/parkly/exception/*.java src/parkly/util/*.java src/parkly/storage/*.java src/parkly/service/*.java src/parkly/Main.java

java -cp out parkly.Main

## Current Status and Pending Work

Done:

- Package structure, OOP model, coordinate validation, Haversine distance calculation, CSV storage, and terminal menu.

Pending:

- Database migration with JDBC and UI layer for final submission.
