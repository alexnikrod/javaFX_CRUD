package com.javafx.crud;

import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ControllerForm implements Initializable {
    private boolean isEdit;
    private Bson filter;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private DatePicker birthday;
    @FXML
    private TextField street;
    @FXML
    private TextField houseNr;
    @FXML
    private TextField postCode;
    @FXML
    private TextField city;
    @FXML
    private TextField email;
    @FXML
    private Label status;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init");
    }

    // "Speichern" button on click
    public void getFieldValues() {
        try {
            MongoDB.connection();

            // convert Date to String
            String date = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // get the values of the fields
            Document doc = new Document("firstname", firstname.getText())
                    .append("lastname", lastname.getText())
                    .append("birthday", date)
                    .append("street", street.getText())
                    .append("houseNr", houseNr.getText())
                    .append("postCode", postCode.getText())
                    .append("city", city.getText())
                    .append("email", email.getText());

            // validation of lastname, one field should be at least be filled
            if (lastname.getText().isEmpty()) {
                status.setTextFill(Color.web("#eb2020"));
                status.setText("Bitte geben Sie den Namen ein");
                lastname.setStyle("-fx-text-box-border: #eb2020; -fx-focus-color: #eb2020;");
                return;
            } else {
                // check if is a new or edit
                if (!isEdit) {
                    // save the document into database collection
                    MongoDB.mongoCollection.insertOne(doc);
                    // get the current window
                    // Stage stage = (Stage) add.getScene().getWindow();
                    // close the current window
                    //stage.close();

                } else {
                    // update the document in database
                    MongoDB.mongoCollection.updateOne(filter, new Document("$set", doc));

                    isEdit = false;
                }

                lastname.setStyle("");
            }
            // display a success message
            status.setTextFill(Color.web("#28a745"));
            status.setText("Datei wurde erfolgreich gespeichert");

            // set the fields to null or empty
            firstname.setText("");
            lastname.setText("");
            birthday.setValue(null);
            street.setText("");
            houseNr.setText("");
            postCode.setText("");
            city.setText("");
            email.setText("");
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
            // display the error message
            status.setTextFill(Color.web("#eb2020"));
            status.setText("Etwas ist schief gelaufen");
        }
    }

    // on edit click set values from table item to form fields
    public void setEditTextField(String firstnameValue, String lastnameValue,
                                 String toLocalDate, String streetValue,
                                 String houseNrValue, String postCodeValue,
                                 String cityValue, String emailValue) {
        firstname.setText(firstnameValue);
        lastname.setText(lastnameValue);
        birthday.setValue(convert(toLocalDate));
        street.setText(streetValue);
        houseNr.setText(houseNrValue);
        postCode.setText(postCodeValue);
        city.setText(cityValue);
        email.setText(emailValue);

        isEdit = true;
    }
    // get filter from table for selected item
    public void updateDoc(Bson filter) {
        this.filter = filter;
    }

    // convert String to Date
    static LocalDate convert(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
