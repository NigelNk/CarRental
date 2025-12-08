package com.example.carrental.controllers;

import com.example.carrental.backend.Rental;
import com.example.carrental.backend.RentalDb;
import com.example.carrental.backend.ShowAlert;
import com.example.carrental.backend.vehicle.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

public class RentalController {
    @FXML
    public TableColumn<Rental, Date> rentedDate;
    @FXML
    public TableColumn<Rental, Date> returnDate;
    @FXML
    private TableView<Rental> customerRentedTable;
    @FXML
    private TableColumn<Rental, String> customerID;
    @FXML
    private TableColumn<Rental, String> customerName;
    @FXML
    private TableColumn<Rental, String> vehicleID;
    @FXML
    private TableColumn<Rental, String> brand;
    @FXML
    private TableColumn<Rental, String> rentalFee;

    static ObservableList<Rental> rentalData = FXCollections.observableArrayList();
    private Vehicle recreatedVehicleObj;

    @FXML
    protected void initialize() {
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        vehicleID.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        rentedDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        rentalFee.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));

        rentalData.clear();
        rentalData.addAll(RentalDb.loadRentals());
        customerRentedTable.setItems(rentalData);

    }

    @FXML
    private void returnVehicle(ActionEvent actionEvent) {
        Rental selectedRental = customerRentedTable.getSelectionModel().getSelectedItem();
        // get data
        String vehicleID = selectedRental.getVehicle().getVehicleID();
        String brand = selectedRental.getVehicle().getBrand();
        String model = selectedRental.getVehicle().getModel();
        String type = selectedRental.getVehicle().getType();
        String speed = selectedRental.getVehicle().getSpeed();
        String imagePath = selectedRental.getVehicle().getImagePath();
        String transmission = selectedRental.getVehicle().getTransmission();
        String formatedTransmission = transmission.replace("Transmission: ", "");
        System.out.println(formatedTransmission);
        double dailyRate = selectedRental.getVehicle().getDailyRate();

        // alert dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Return");
        alert.setHeaderText("Return Vehicle");
        alert.setContentText("Are you sure you want to mark this vehicle borrowed by " + selectedRental.getCustomer().getName() + " as returned?");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial';");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // TODO: Recreate vehicle object

                if (type.equals("Car")) {
                    Car obj = (Car) selectedRental.getVehicle();
                    int capacity = obj.getCapacity();
                    recreatedVehicleObj = new Car(vehicleID, brand, model, formatedTransmission, speed, dailyRate, capacity, imagePath, type);

                }
                else if (type.equals("Truck")) {
                    Truck obj = (Truck) selectedRental.getVehicle();
                    double weight = obj.getCargoWeight();
                    recreatedVehicleObj = new Truck(vehicleID, brand, model, formatedTransmission, speed, dailyRate, weight, imagePath,type);
                }
                else {
                    recreatedVehicleObj = new Bike(vehicleID, brand, model, formatedTransmission, speed, dailyRate, imagePath, type);
                }
                // save object
                VehicleDb.vehicles.add(recreatedVehicleObj);
                VehicleDb.saveVehicles();
                ShowAlert.successAlert("Successfully returned " + brand, type);

            }
        });
    }
}
