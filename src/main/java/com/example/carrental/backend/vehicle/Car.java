package com.example.carrental.backend.vehicle;

public class Car extends Vehicle {
    private final int capacity;

    public Car(String vehicleID, String brand, String model, String transmission, String speed, double dailyRate, int capacity, String image, String type) {
        super(vehicleID, brand, model, transmission, speed, dailyRate, image, type);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}

