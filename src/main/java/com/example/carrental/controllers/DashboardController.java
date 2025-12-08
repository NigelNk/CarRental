package com.example.carrental.controllers;

import com.example.carrental.backend.vehicle.Vehicle;
import com.example.carrental.backend.vehicle.VehicleDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.example.carrental.HelloApplication.countAvailableCars;
import static com.example.carrental.HelloApplication.countAvailableVehicle;

public class DashboardController {
    @FXML
    private HBox vehicleContainer;
    @FXML
    private Button availableVehicles;
    @FXML
    private Button customers;
    @FXML
    private Button rentals;
    @FXML
    public Label customersAvailable;
    @FXML
    private Label rentalsLabel;
    @FXML
    private Label availableCarsLabel;
    @FXML
    private Label customerLabel;
    @FXML
    private StackPane contentArea;
    @FXML
    public Label numberOfCarsAvailable;
    @FXML
    private VBox sidebar;
    @FXML
    private Button dashboard;
    @FXML
    private Button notificationButton;


    @FXML
    protected void initialize() {

        new VehicleDb();
        displayVehicles(VehicleDb.vehicles);

        // set the dashboard button initialized upon launching the program
        setActiveButton(dashboard);

    }

    @FXML
    protected void addVehicle(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-vehicle-view.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton((Button) event.getSource());

    }

    @FXML
    protected void addCustomer(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-customer-view.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton((Button) event.getSource());
    }

    @FXML
    protected void customerView(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("customer-view.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton((Button) event.getSource());

    }

    @FXML
    protected void rent(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("rent.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton((Button) event.getSource());
    }

    @FXML
    protected void viewVehicles(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("vehicles-view.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton((Button) event.getSource());

    }

    @FXML
    public void rentedVehicle(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("rented-vehicle-view.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton((Button) event.getSource());
    }

    private void setActiveButton(Button activeBtn) {
        for (Node node : sidebar.getChildren()) {
            if (node instanceof Button button) {
                button.getStyleClass().remove("active");
            }
        }
        activeBtn.getStyleClass().add("active");
    }

    @FXML
    public void dashboardView(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            boolean wasMaximized = stage.isMaximized(); // store the current state
            double width = stage.getWidth();
            double height = stage.getHeight();

            Scene scene = new Scene(loader.load(), width, height);

            // load fxml for initial dashboard
            DashboardController controller = loader.getController();

            controller.numberOfCarsAvailable.setText("" + countAvailableVehicle);
            controller.customersAvailable.setText("" + CustomerController.countCustomers());

            stage.setScene(scene);

            stage.setMaximized(wasMaximized);

            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setActiveButton((Button) actionEvent.getSource());
    }

    @FXML
    public void viewRentalToolTipShow(MouseEvent mouseEvent) {
        rentalsLabel.setVisible(true);
    }

    @FXML
    public void viewRentalToolTipHide(MouseEvent mouseEvent) {
        rentalsLabel.setVisible(false);
    }

    @FXML
    public void availableCarsToolTipShow(MouseEvent mouseEvent) {
        availableCarsLabel.setVisible(true);
    }

    @FXML
    public void availableCarsToolTipHide(MouseEvent mouseEvent) {
        availableCarsLabel.setVisible(false);
    }

    @FXML
    public void viewCustomersToolTipShow(MouseEvent mouseEvent) {
        customerLabel.setVisible(true);
    }

    @FXML
    public void viewCustomersToolTipHide(MouseEvent mouseEvent) {
        customerLabel.setVisible(false);
    }

    @FXML
    public void viewRentals(MouseEvent mouseEvent) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("rented-vehicle-view.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton(rentals);
    }
    @FXML
    public void viewCustomer(MouseEvent mouseEvent) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("customer-view.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton(customers);
    }
    @FXML
    public void viewAvailableCars(MouseEvent mouseEvent) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("vehicles-view.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton(availableVehicles);
    }

    public void displayVehicles(List<Vehicle> vehicles) {
        try {
            new VehicleDb();
            vehicleContainer.getChildren().clear();

            for (int i = 0; i < 3; i++) {

                VBox card = new VBox(5);
                card.setAlignment(Pos.TOP_CENTER);
                card.setPrefSize(334, 440);
                card.setStyle("-fx-background-color: #ffffff; -fx-padding: 12; -fx-background-radius: 12; "
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0.4, 0, 1);");

                // Image
                ImageView imageView = new ImageView(new Image(new File(vehicles.get(i).getImagePath()).toURI().toString()));
                imageView.setFitHeight(193);
                imageView.setFitWidth(309);
                imageView.setPreserveRatio(true);

                Label nameLbl = new Label(vehicles.get(i).getBrand());
                nameLbl.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

                Label modelLbl = new Label("Model: " + vehicles.get(i).getModel());
                modelLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");

                Label speedLbl = new Label("Speed: " + vehicles.get(i).getSpeed());
                speedLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");

                Label transmissionLbl = new Label(vehicles.get(i).getTransmission());
                transmissionLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");

                Label rateLbl = new Label("Daily Rate: " + vehicles.get(i).getDailyRateFormatted());
                rateLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #0bd5ac;");

                Label availableLbl = new Label("Is Available: " + vehicles.get(i).getIsAvailable());
                availableLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");


                // Add components to card
                card.getChildren().addAll(imageView, nameLbl, modelLbl, speedLbl, transmissionLbl, availableLbl, rateLbl);

                vehicleContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
