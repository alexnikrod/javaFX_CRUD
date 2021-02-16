module app.javafx {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires mongo.java.driver;
    requires java.logging;

    opens com.javafx.crud;
}