package sample;

import javafx.beans.property.SimpleStringProperty;

public class User extends Person{
    private int id;

    public User(int user_id, String firstName, String lastName, String username, String email, String password) {
        this.id = user_id;
        super.firstName = firstName;
        super.lastName = lastName;
        super.username = username;
        super.email = email;
        super.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
