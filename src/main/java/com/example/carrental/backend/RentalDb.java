package com.example.carrental.backend;

import com.example.carrental.backend.vehicle.VehicleDb;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDb {
    private static final String FILE_NAME = "rentals_db.dat";

    public static void saveRental(List<Rental> rentals) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(rentals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Rental> loadRentals() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Rental>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void addRental(Rental rental) {
        List<Rental> rentalObj = loadRentals(); // Load old ones
        rentalObj.add(rental);                    // Add new
        saveRental(rentalObj);                      // Save all

    }
}
