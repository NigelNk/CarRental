package com.example.carrental.backend.vehicle;

public class Truck extends Vehicle {
    private final double cargoWeight;

    public Truck (String vehicleID, String brand, String model, String transmission, String speed, double dailyRate, double cargoWeight, String image, String type) {
        super(vehicleID, brand, model, transmission, speed, dailyRate, image, type);
        this.cargoWeight = cargoWeight;
    }

    public double getCargoWeight() {
        return cargoWeight;
    }
}
