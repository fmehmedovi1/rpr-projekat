package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class UserModel {

    private ObservableMap<String, User> users = FXCollections.observableHashMap();

    public ObservableMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(ObservableMap<String, User> users) {
        this.users = users;
    }

    public void addUser(User user){
        this.users.put(user.getUsername(), user);
    }

    public void deleteUser(User user){
        this.users.remove(user.getUsername());
    }
}
