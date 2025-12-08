package com.example.carrental.backend;

import com.example.carrental.backend.customer.Customer;
import com.example.carrental.backend.vehicle.Vehicle;

import java.util.Date;

public interface Rentable {
    void rentVehicle(Vehicle vehicle, Customer customer, Date startDate, Date endDate);
}
