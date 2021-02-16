package com.javafx.crud;

import com.javafx.crud.Model.Persons;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.mongodb.client.model.Filters.eq;

public class ControllerTable implements Initializable {
    @FXML
    private Label status;
    @FXML
    private TableView<Persons> table;
    @FXML
    private TableColumn<Persons, String> id;
    @FXML
    private TableColumn<Persons, String> firstname;
    @FXML
    private TableColumn<Persons, String> lastname;
    @FXML
    private TableColumn<Persons, String> birthday;
    @FXML
    private TableColumn<Persons, String> street;
    @FXML
    private TableColumn<Persons, String> houseNr;
    @FXML
    private TableColumn<Persons, String> postCode;
    @FXML
    private TableColumn<Persons, String> city;
    @FXML
    private TableColumn<Persons, String> email;

    //  create a primary stage object
    Stage primaryStage = new Stage();

    //  create an observable list to hold the Persons object in the Persons class
    public ObservableList<Persons> list;
    public List<Persons> attend = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MongoDB.connection();
        // get all data from database
        getList(MongoDB.findAllItems());

        // make table columns auto resizable
        setTableAutoResizeColumns();

        // call the setTable method
        setTable();
    }

    public void addPerson() throws Exception {
        // load the form window
        Parent root = FXMLLoader.load(getClass().getResource("View/MainForm.fxml"));
        primaryStage.setTitle("Eintrag hinzufügen");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void getList(MongoCursor<Document> cursor) {
        try (cursor) {
            //  loop through the database and then populate the list
            for (int i = 0; i < MongoDB.mongoCollection.countDocuments(); i++) {
                int position = i + 1;

                Document doc = cursor.next();

                String _firstname = doc.getString("firstname");
                String _lastname = doc.getString("lastname");
                String _birthday = doc.getString("birthday");
                String _street = doc.getString("street");
                String _houseNr = doc.getString("houseNr");
                String _postCode = doc.getString("postCode");
                String _city = doc.getString("city");
                String _email = doc.getString("email");

                String _pos = Integer.toString(position);

                attend.add(new Persons(_pos, _firstname, _lastname, _birthday,
                        _street, _houseNr, _postCode, _city, _email));
            }
            list = FXCollections.observableArrayList(attend);
        }
    }

    public void setTable() {
        // set the values of each column to display on the table
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        firstname.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        lastname.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
        birthday.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
        street.setCellValueFactory(cellData -> cellData.getValue().streetProperty());
        houseNr.setCellValueFactory(cellData -> cellData.getValue().houseNrProperty());
        postCode.setCellValueFactory(cellData -> cellData.getValue().postCodeProperty());
        city.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        email.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        table.setItems(list);
    }

    public void editPerson() throws IOException {
        // get the selected row
        Persons selectedItem = table.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            // display an error message
            status.setText("Bitte wählen Sie eine Zeile aus");
        } else {
            // load and show form with filled values

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("View/MainForm.fxml"));
            Parent root = loader.load();
            // connect with form controller
            ControllerForm ctrl = loader.getController();
            // get values from selected item
            String firstname = selectedItem.getFirstname();
            String lastname = selectedItem.getLastname();
            String birthday = selectedItem.getBirthday();
            String street = selectedItem.getStreet();
            String houseNr = selectedItem.getHouseNr();
            String postCode = selectedItem.getPostCode();
            String city = selectedItem.getCity();
            String email = selectedItem.getEmail();
            // give values to fields in form
            ctrl.setEditTextField(firstname, lastname, birthday, street, houseNr, postCode, city, email);
            // show form with values
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            status.setText("");
            // get value for filter in database when record is updated
            Bson filter = Filters.eq("lastname", lastname);
            // give it to form control
            ctrl.updateDoc(filter);
        }
    }

    public void deletePerson() {
        // get the selected row
        Persons selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            // display an error message
            status.setText("Bitte wählen Sie eine Zeile aus");
        } else {
            // are you sure to delete window
            Alert alert = new Alert(null, "Möchten Sie diese Datei wirklich löschen?", ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("Löschen");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                // get the value of the selected lastname column
                String keyValue = selectedItem.getLastname();

                // use lastname as primary key to find each document to delete from the database
                MongoDB.mongoCollection.deleteOne(eq("lastname", keyValue));

                // refresh Table after delete
                updateTable();

                // hide the error message
                status.setText("");
            }
        }
    }

    public void updateTable() {
        // clears the attend list so that the previous data won't be displayed together with this new ones on the table
        attend.clear();
        status.setText("");

        getList(MongoDB.findAllItems());

        // call the setTable method
        setTable();
    }

    private void setTableAutoResizeColumns() {
        id.prefWidthProperty().bind(table.widthProperty().divide(18));
        firstname.prefWidthProperty().bind(table.widthProperty().divide(9));
        lastname.prefWidthProperty().bind(table.widthProperty().divide(9));
        birthday.prefWidthProperty().bind(table.widthProperty().divide(9));
        street.prefWidthProperty().bind(table.widthProperty().divide(6));
        houseNr.prefWidthProperty().bind(table.widthProperty().divide(18));
        postCode.prefWidthProperty().bind(table.widthProperty().divide(9));
        city.prefWidthProperty().bind(table.widthProperty().divide(9));
        email.prefWidthProperty().bind(table.widthProperty().divide(7));
    }
}
