package com.example.carrental.controllers;

import com.example.carrental.backend.ShowAlert;
import com.example.carrental.backend.customer.Customer;
import com.example.carrental.backend.customer.CustomerDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class AddCustomerController {

    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerIdField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;

    @FXML
    protected void nameFieldToolTipShow(MouseEvent mouseEvent) {
        nameLabel.setVisible(true);
    }

    @FXML
    protected void nameFieldToolTipHide(MouseEvent mouseEvent) {
        nameLabel.setVisible(false);
    }

    @FXML
    protected void idFieldToolTipShow(MouseEvent mouseEvent) {
        idLabel.setVisible(true);
    }

    @FXML
    protected void idFieldToolTipHide(MouseEvent mouseEvent) {
        idLabel.setVisible(false);
    }

    @FXML
    protected void phoneFieldToolTipShow(MouseEvent mouseEvent) {
        phoneLabel.setVisible(true);
    }

    @FXML
    protected void phoneFieldToolTipHide(MouseEvent mouseEvent) {
        phoneLabel.setVisible(false);
    }

    @FXML
    protected void addressFieldToolTipShow(MouseEvent mouseEvent) {
        addressLabel.setVisible(true);
    }

    @FXML
    protected void addressFieldToolTipHide(MouseEvent mouseEvent) {
        addressLabel.setVisible(false);
    }

    @FXML
    protected void saveBtn(ActionEvent actionEvent) {
        String customerName = customerNameField.getText();
        String customerID = customerIdField.getText();
        String customerPhone = customerPhoneField.getText();
        String customerAddress = customerAddressField.getText();

        if (customerName.isEmpty() || customerID.isEmpty() || customerPhone.isEmpty() || customerAddress.isEmpty()) {
            ShowAlert.warningAlert("Warning", "Please fill all the fields", "Incomplete Form");
        }
        else {

            Customer customer = new Customer(customerName, customerID, customerPhone, customerAddress);
            CustomerDatabase.addCustomer(customer);

            //clear the text fields
            customerNameField.setText("");
            customerIdField.setText("");
            customerPhoneField.setText("");
            customerAddressField.setText("");
        }
    }
}
