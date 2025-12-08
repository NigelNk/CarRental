package com.example.carrental.controllers;

import com.example.carrental.backend.*;
import com.example.carrental.backend.customer.Customer;
import com.example.carrental.backend.customer.CustomerDatabase;
import com.example.carrental.backend.vehicle.Vehicle;
import com.example.carrental.backend.vehicle.VehicleDb;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class RentController implements Rentable{
    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private ComboBox<Vehicle> vehicleComboBox;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ImageView vehicleImage;


    @FXML
    public void initialize() {
        new VehicleDb();
        customerComboBox.getItems().addAll(CustomerDatabase.loadCustomers());
        vehicleComboBox.getItems().addAll(VehicleDb.vehicles);
    }

    @FXML
    public void setPicture(ActionEvent actionEvent) {
        Vehicle selectedVehicle = vehicleComboBox.getValue();

        if (selectedVehicle != null && selectedVehicle.getImagePath() != null) {
            try {
                // Load image from file path or resources
                Image image = new Image("file:" + selectedVehicle.getImagePath());
                vehicleImage.setImage(image);
            } catch (Exception e) {
                ShowAlert.errorAlert("Error adding rental: " + e.getMessage());
            }
        } else {
            // Clear image if no selection or path null
            vehicleImage.setImage(null);
        }
    }

    @FXML
    private void handleAddRental() {
        try {
            Customer customer = customerComboBox.getSelectionModel().getSelectedItem();
            Vehicle vehicle = vehicleComboBox.getValue();
            LocalDate start = LocalDate.now();
            LocalDate end = endDatePicker.getValue();

            if (customer == null || vehicle == null || end == null) {
                ShowAlert.errorAlert("Please fill in all fields before adding rental.");
            }

            else {

                // Convert LocalDate to Date
                Date startDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());

                if (endDate.compareTo(startDate) < 0) {
                    ShowAlert.errorAlert("End date cannot be earlier than start date.");
                }
                else {

                    rentVehicle(vehicle, customer, startDate, endDate);
                    // Remove the rented vehicle for the list of available vehicles
                    VehicleDb.vehicles.remove(vehicle);
                    VehicleDb.saveVehicles();

                    customerComboBox.getSelectionModel().clearSelection();
                    vehicleComboBox.getSelectionModel().clearSelection();

                    ShowAlert.rentalSuccessAlert("Operation Success", vehicle.getBrand(), customer.getName());

                }
            }

        } catch (Exception e) {
            ShowAlert.errorAlert("Error adding rental: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void rentVehicle(Vehicle vehicle, Customer customer, Date startDate, Date endDate) {
        Rental rental = new Rental(customer, vehicle, startDate, endDate);
        // Save rented vehicles to file
        RentalDb.addRental(rental);

    }

    @FXML
    public void saveBtn(ActionEvent actionEvent) {
        handleAddRental();
    }
}

