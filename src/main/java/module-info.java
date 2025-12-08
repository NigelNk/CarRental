module com.example.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires javafx.graphics;

    opens com.example.carrental.backend to javafx.fxml;
    opens com.example.carrental.controllers to javafx.fxml;
    opens com.example.carrental.backend.customer to javafx.base;
    opens com.example.carrental.backend.vehicle to javafx.base;

    exports com.example.carrental;
    exports com.example.carrental.controllers;
    exports com.example.carrental.backend;
    exports com.example.carrental.backend.customer;
    exports com.example.carrental.backend.vehicle;

}