package com.example.carrental.controllers;

import com.example.carrental.backend.vehicle.Vehicle;
import com.example.carrental.backend.vehicle.VehicleDb;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;

public class VehicleController {
    @FXML
    private FlowPane vehiclesContainer;

    public static int count=0;


    @FXML
    public void initialize() {
        displayVehicles(VehicleDb.vehicles);
    }

    public void displayVehicles(List<Vehicle> vehicles) {
        vehiclesContainer.getChildren().clear();

        for (Vehicle v : vehicles) {
            count++;

            VBox card = new VBox(5);
            card.setAlignment(Pos.TOP_CENTER);
            card.setPrefSize(334, 441);
            card.setStyle("-fx-background-color: #ffffff; -fx-padding: 12; -fx-background-radius: 12; "
                    + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0.4, 0, 1);");

            // Image
            ImageView imageView = new ImageView(new Image(new File(v.getImagePath()).toURI().toString()));
            imageView.setFitHeight(193);
            imageView.setFitWidth(309);
            imageView.setPreserveRatio(true);

            Label nameLbl = new Label(v.getBrand());
            nameLbl.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

            Label modelLbl = new Label("Model: " + v.getModel());
            modelLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");

            Label speedLbl = new Label("Speed: " + v.getSpeed());
            speedLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");

            Label transmissionLbl = new Label(v.getTransmission());
            transmissionLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");

            Label rateLbl = new Label("Daily Rate: " + v.getDailyRateFormatted());
            rateLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #0bd5ac;");

            Label availableLbl = new Label("Is Available: " + v.getIsAvailable());
            availableLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");

            // Add components to card
            card.getChildren().addAll(imageView, nameLbl, modelLbl, speedLbl, transmissionLbl, availableLbl, rateLbl);

            vehiclesContainer.getChildren().add(card);
        }
    }
}
