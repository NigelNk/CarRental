package com.example.carrental;

import com.example.carrental.backend.vehicle.Vehicle;
import com.example.carrental.backend.vehicle.VehicleDb;
import com.example.carrental.controllers.CustomerController;
import com.example.carrental.controllers.DashboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HelloApplication extends Application {
    public static int countAvailableVehicle = 0;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("controllers/dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 600);

        stage.getIcons().add(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/com/example/carrental/icons/bg.png"))
        ));

        stage.setTitle("Car Rental System");

        countAvailableCars(VehicleDb.vehicles);

        // load fxml for initial dashboard
        DashboardController controller = fxmlLoader.getController();

        controller.numberOfCarsAvailable.setText("" + countAvailableVehicle);
        controller.customersAvailable.setText("" + CustomerController.countCustomers());

        stage.setScene(scene);
        stage.show();
    }
    public static void countAvailableCars (List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            countAvailableVehicle += 1;
        }
    }
}
