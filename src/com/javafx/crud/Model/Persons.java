package com.javafx.crud.Model;

import javafx.beans.property.SimpleStringProperty;

public class Persons {
    private final SimpleStringProperty id;
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty lastname;
    private final SimpleStringProperty birthday;
    private final SimpleStringProperty street;
    private final SimpleStringProperty houseNr;
    private final SimpleStringProperty postCode;
    private final SimpleStringProperty city;
    private final SimpleStringProperty email;

    // Constructor
    public Persons(String id, String firstname, String lastname, String birthday, String street, String houseNr,
                   String postCode, String city, String email) {
        this.id = new SimpleStringProperty(id);
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.birthday = new SimpleStringProperty(birthday);
        this.street = new SimpleStringProperty(street);
        this.houseNr = new SimpleStringProperty(houseNr);
        this.postCode = new SimpleStringProperty(postCode);
        this.city = new SimpleStringProperty(city);
        this.email = new SimpleStringProperty(email);
    }

    // Getters
    public String getId() {
        return id.get();
    }

    public String getFirstname() {
        return firstname.get();
    }

    public String getLastname() {
        return lastname.get();
    }

    public String getBirthday() {
        return birthday.get();
    }

    public String getStreet() {
        return street.get();
    }

    public String getHouseNr() {
        return houseNr.get();
    }

    public String getPostCode() {
        return postCode.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getEmail() {
        return email.get();
    }

    // Setters
    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public void setHouseNr(String houseNr) {
        this.houseNr.set(houseNr);
    }

    public void setPostCode(String postCode) {
        this.postCode.set(postCode);
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    // properties for cells in TableView
    public SimpleStringProperty idProperty() {
        return id;
    }

    public SimpleStringProperty firstnameProperty() {
        return firstname;
    }

    public SimpleStringProperty lastnameProperty() {
        return lastname;
    }

    public SimpleStringProperty birthdayProperty() {
        return birthday;
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public SimpleStringProperty houseNrProperty() {
        return houseNr;
    }

    public SimpleStringProperty postCodeProperty() {
        return postCode;
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }
}
