package com.example.carrental.backend.vehicle;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Vehicle implements Serializable {
    private final String vehicleID;
    private final String brand;
    private final String model;
    private final double dailyRate;
    public boolean isAvailable;
    private final String imagePath;
    private final String speed;
    private final String transmission;
    private final String type;


    public Vehicle(String vehicleID, String brand, String model, String transmission, String speed, double dailyRate, String imagePath, String type) {
        this.vehicleID = vehicleID;
        this.brand = brand;
        this.model = model;
        this.transmission = transmission;
        this.speed = speed;
        this.dailyRate = dailyRate;
        this.imagePath = imagePath;
        this.type = type;
        this.isAvailable = true;
    }

    public boolean setAvailable(boolean bool) {
        return bool;
    }
    public String getVehicleID() {
        return vehicleID;
    }
    public String getImagePath() {
        return imagePath;
    }

    public String getBrand() {
        return brand;
    }
    public boolean getIsAvailable() {
        return isAvailable;
    }

    public String getModel() {
        return model;
    }

    public String getSpeed() {
        return speed;
    }
    public String getType() {
        return type;
    }

    public String getTransmission() {
        return "Transmission: " + transmission;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public String getDailyRateFormatted() {
        DecimalFormat df = new DecimalFormat("#,###");
        return "Mk " + df.format(dailyRate);
    }

    @Override
    public String toString() {
        return "Brand=" + brand + ", Model=" + model + ", DailyRate=" + getDailyRateFormatted();
    }
}

