package com.example.carrental.backend.vehicle;


import java.io.*;
        import java.util.ArrayList;

public class VehicleDb {

    public  static ArrayList<Vehicle> vehicles;

    public VehicleDb() {
        vehicles = new ArrayList<>();
        populateArrayList();
    }

    public void populateArrayList() {
        try {
            FileInputStream file = new FileInputStream("vehicle_db.dat");
            ObjectInputStream inputFile = new ObjectInputStream(file);

            boolean endOfFile = false;
            while (!endOfFile) {
                try {
                    vehicles.add((Vehicle) inputFile.readObject());
                } catch(EOFException e) {
                    endOfFile = true;
                } catch (Exception  e) {
                }
            }
            inputFile.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveVehicles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("vehicle_db.dat"))) {
            for (Vehicle v : vehicles) {
                out.writeObject(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


