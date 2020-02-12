package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {

    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName, lastName, username, email, password;

    public User(int user_id, String firstName, String lastName, String username, String email, String password) {
        this.id.set(user_id);
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.username.setValue(username);
        this.email.setValue(email);
        this.password.setValue(username);
    }

    public int getUser_id() {
        return id.get();
    }

    public SimpleIntegerProperty user_idProperty() {
        return id;
    }

    public void setUser_id(int user_id) {
        this.id.set(user_id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
