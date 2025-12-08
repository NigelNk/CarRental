package com.example.carrental.controllers;

import com.example.carrental.backend.ShowAlert;
import com.example.carrental.backend.vehicle.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;


public class AddVehicleController {
    @FXML
    private ComboBox speedTypeCombo;
    @FXML
    private TextField vehicleIDField;

    @FXML
    private Label vehicleIdLabel;

    @FXML
    private TextField modelField;
    @FXML
    private Label modelLabel;

    @FXML
    private TextField speedField;
    @FXML
    private Label speedLabel;

    @FXML
    private TextField dailyRateField;
    @FXML
    private Label rateLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label transmissionLabel;
    @FXML
    private ComboBox vehicleTypeBox;
    @FXML
    private ComboBox transmissionCombo;

    @FXML
    private TextField brandField;

    @FXML
    private Label brandLabel;
    @FXML
    private ImageView vehicleImageView;

    private File selectedFile;
    private String storedImagePath;


    @FXML
    protected void brandTooltipShow(MouseEvent mouseEvent) {
        brandLabel.setVisible(true);
    }

    @FXML
    protected void brandTooltipHide(MouseEvent mouseEvent) {
        brandLabel.setVisible(false);
    }

    @FXML
    protected void modelTooltipShow(MouseEvent mouseEvent) {
        modelLabel.setVisible(true);
    }
    @FXML
    protected void modelTooltipHide(MouseEvent mouseEvent) {
        modelLabel.setVisible(false);
    }

    @FXML
    protected void speedTooltipShow(MouseEvent mouseEvent) {
        speedLabel.setVisible(true);
    }
    @FXML
    protected void speedTooltipHide(MouseEvent mouseEvent) {
        speedLabel.setVisible(false);
    }
    @FXML
    protected void rateTooltipShow(MouseEvent mouseEvent) {
        rateLabel.setVisible(true);
    }
    @FXML
    protected void rateTooltipHide(MouseEvent mouseEvent) {
        rateLabel.setVisible(false);
    }
    @FXML
    protected void transmissionTooltipShow(MouseEvent mouseEvent) {
        transmissionLabel.setVisible(true);
    }
    @FXML
    protected void transmissionTooltipHide(MouseEvent mouseEvent) {
        transmissionLabel.setVisible(false);
    }

    @FXML
    protected void typeTooltipShow(MouseEvent mouseEvent) {
        typeLabel.setVisible(true);
    }
    @FXML
    protected void typeTooltipHide(MouseEvent mouseEvent) {
        typeLabel.setVisible(false);
    }

    @FXML
    protected void vehicleIdTooltipShow(MouseEvent mouseEvent) {
        vehicleIdLabel.setVisible(true);
    }
    @FXML
    protected void vehicleIdTooltipHide(MouseEvent mouseEvent) {
        vehicleIdLabel.setVisible(false);
    }


    @FXML
    protected void selectedType(ActionEvent actionEvent) {
        if (vehicleTypeBox.getSelectionModel().getSelectedItem().equals("Bike")) {
            transmissionCombo.getSelectionModel().select(0);
            transmissionCombo.disableProperty().set(true);
        }
        else {
            transmissionCombo.disableProperty().set(false);
        }
    }

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Vehicle Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) vehicleImageView.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {

                File storageDir = new File("src/main/resources/com/example/carrental/images/");
                if (!storageDir.exists()) storageDir.mkdirs(); //

                String uniqueFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                File destination = new File(storageDir, uniqueFileName);


                Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

                storedImagePath = "src/main/resources/com/example/carrental/images/" + uniqueFileName;

                vehicleImageView.setImage(new Image(destination.toURI().toString()));

            } catch (IOException e) {
                ShowAlert.errorAlert("Failed to save image to storage folder.");
            }
        }
    }

    //    Save button
    @FXML
    private void handleSaveVehicle() {
        String vehicleID = vehicleIDField.getText();
        String brand = brandField.getText();
        String model = modelField.getText();
        String spd = speedField.getText();
        String type = (vehicleTypeBox.getValue() != null) ? vehicleTypeBox.getValue().toString() : "Unknown";
        String rate = dailyRateField.getText();

        // initialize VehicleDB
        new VehicleDb();

        if (vehicleID.isEmpty() || brand.isEmpty() || model.isEmpty() || transmissionCombo.getSelectionModel().isEmpty() || spd.isEmpty() || dailyRateField.getText().isEmpty()) {
            ShowAlert.warningAlert("Warning", "Please fill all the fields", "Incomplete Form");
        } else {
            try {

                String trans = (transmissionCombo.getSelectionModel().getSelectedItem()).toString();
                String speedType = (speedTypeCombo.getSelectionModel().getSelectedItem()).toString();
                spd += " " + speedType;
                double dailyRate = Double.parseDouble(rate);

                Vehicle newVehicle = null;

                if ("Car".equals(type)) {

                    String c = askForCapacity();
                    int capacity = Integer.parseInt(c);

                    newVehicle = new Car(vehicleID, brand, model, trans, spd, dailyRate, capacity, storedImagePath, "Car");
                    VehicleDb.vehicles.add(newVehicle);
                    VehicleDb.saveVehicles();
                    ShowAlert.successAlert("Successfully added new", "Car");


                } else if ("Truck".equals(type)) {
                    double cargoWeight = Double.parseDouble(askWeights());
                    newVehicle = new Truck(vehicleID, brand, model, trans, spd, dailyRate, cargoWeight, storedImagePath, "Truck");
                    VehicleDb.vehicles.add(newVehicle);
                    VehicleDb.saveVehicles();
                    ShowAlert.successAlert("Successfully added new", "Truck");
                } else if ("Bike".equals(type)) {
                    newVehicle = new Bike(vehicleID, brand, model, trans, spd, dailyRate, storedImagePath, "Bike");
                    VehicleDb.vehicles.add(newVehicle);
                    VehicleDb.saveVehicles();
                    ShowAlert.successAlert("Successfully added new", "Bike");
                }
                vehicleIDField.clear();
                brandField.clear();
                modelField.clear();
                speedField.clear();
                dailyRateField.clear();
                vehicleImageView.setImage(null);


            } catch (Exception e) {
                ShowAlert.errorAlert("Failed to save vehicle: " + e.getMessage());
            }
        }
    }

    private String askWeights() {
        TextInputDialog cargoWeight = new TextInputDialog();

        cargoWeight.setTitle("Truck Weight Required in Tons");
        cargoWeight.setHeaderText("Enter Weight");
        cargoWeight.setContentText("Please enter the Cargo Weight of the Truck:");


        cargoWeight.getDialogPane().setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-background-color: #f9f9f9;"
        );

        Optional<String> result = cargoWeight.showAndWait();

        // If user entered something, return it
        return result.orElse(null);
    }

    private String askForCapacity() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Vehicle Capacity Required");
        dialog.setHeaderText("Enter Vehicle Capacity");
        dialog.setContentText("Please enter the seating capacity of the vehicle:");

        dialog.getDialogPane().setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-background-color: #f9f9f9;"
        );

        Optional<String> result = dialog.showAndWait();

        // If user entered something, return it
        return result.orElse(null);
    }

}
