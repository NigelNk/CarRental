package com.example.carrental.controllers;

import com.example.carrental.backend.Rental;
import com.example.carrental.backend.RentalDb;
import com.example.carrental.backend.ShowAlert;
import com.example.carrental.backend.customer.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.carrental.backend.customer.CustomerDatabase.loadCustomers;

public class CustomerController {
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> colID;
    @FXML
    private TableColumn<Customer, String> colNames;
    @FXML
    private TableColumn<Customer, String> colPhone;
    @FXML
    private TableColumn<Customer, String> colAddress;
    @FXML
    private TableColumn<Customer, Void> actionColumn;

    private static final ObservableList<Customer> customerData = FXCollections.observableArrayList();

    private static final String FILE_NAME = "customer_db.dat";

    @FXML
    public void initialize() {
        // Link columns with Customer getters
        colID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colNames.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Load customers
        customerData.clear();
        customerData.addAll(loadCustomers());
        customerTable.setItems(customerData);

        // Add Delete button in the Action column
        addActionButtons();
    }

    /**
     * Adds a Delete button to each row in the Action column.
     */
    private void addActionButtons() {
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Remove");

            {
                deleteButton.setStyle("-fx-background-color: #e63946; -fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;");
                deleteButton.setOnAction(e -> {
                    Customer customer = getTableView().getItems().get(getIndex());

                    int size = RentalDb.loadRentals().size();
                    ArrayList<String> customerWithRentals = new ArrayList<>();

                    for (int i = 0; i < size; i++) {
                        customerWithRentals.add(RentalDb.loadRentals().get(i).getCustomerID());
                    }


                    if (customerWithRentals.contains(customer.getCustomerID())) {
                        ShowAlert.warningAlert("Customer with Rent", "The selected customer has a rent", "Operation Failed");
                    }
                    else {
                        // Confirm deletion
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                        confirm.setTitle("Delete Customer");
                        confirm.setHeaderText("Confirm Deletion");
                        confirm.setContentText("Are you sure you want to delete customer: " + customer.getName() + "?");

                        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {

                            customerTable.getItems().remove(customer);
                            // remove from file or database here
                            saveUpdatedCustomers();
                        }
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(deleteButton);
                    box.setSpacing(5);
                    setGraphic(box);
                }
            }
        });
    }

    /**
     * Saves updated customer list back to the file.
     */
    private void saveUpdatedCustomers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            ArrayList<Customer> updatedList = new ArrayList<>(customerTable.getItems());
            out.writeObject(updatedList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int countCustomers() {
        int count = 0;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            ArrayList<Customer> list = (ArrayList<Customer>) in.readObject();
            count = list.size();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }
}
