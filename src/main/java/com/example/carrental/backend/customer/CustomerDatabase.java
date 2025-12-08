package com.example.carrental.backend.customer;

import com.example.carrental.backend.ShowAlert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDatabase {
    private static final String FILE_NAME = "customer_db.dat";

    public static void saveCustomers(List<Customer> customers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(customers);

            // Show success alert
            ShowAlert.successAlert("Added New Customer", "Customer");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Customer> loadCustomers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Customer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void addCustomer(Customer customer) {
        List<Customer> customerObj = loadCustomers(); // Load old ones
        customerObj.add(customer);                    // Add new
        saveCustomers(customerObj);                      // Save all
    }
}
