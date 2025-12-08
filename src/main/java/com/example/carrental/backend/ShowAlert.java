package com.example.carrental.backend;

import com.example.carrental.backend.customer.CustomerDatabase;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.util.Objects;

final public class ShowAlert {

    public static void successAlert(String title, String objType){
        // Create alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION, title, ButtonType.OK);
        alert.setTitle("Success Info.");
        alert.setHeaderText("New " + objType +" added successfully");

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        // some styles
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(CustomerDatabase.class.getResource("/com/example/carrental/alert-style.css")).toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-alert");

        alert.showAndWait();
    }
    public static void rentalSuccessAlert(String title, String objType, String customer){
        // Create alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION, title, ButtonType.OK);
        alert.setTitle("Success Info.");
        alert.setHeaderText("Successfully Rented " + objType +" To " + customer);

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        // some styles
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(CustomerDatabase.class.getResource("/com/example/carrental/alert-style.css")).toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-alert");

        alert.showAndWait();
    }

    public static void warningAlert(String title, String msg, String headerText){
        // Create alert
        Alert alert = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(headerText);

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        // some styles
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(CustomerDatabase.class.getResource("/com/example/carrental/alert-style.css")).toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-alert");

        alert.showAndWait();
    }

    public static void errorAlert(String contextText){
        // Create alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(contextText);

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        // some styles
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(CustomerDatabase.class.getResource("/com/example/carrental/alert-style.css")).toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-alert");

        alert.showAndWait();
    }
}
