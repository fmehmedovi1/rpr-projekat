package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class UserModel {
    private ObservableMap<String, User> users = FXCollections.observableHashMap();
    private SimpleObjectProperty<User> currentUser = new SimpleObjectProperty<>();
    private WarehouseDAO warehouseDAO;

    public UserModel() {
        warehouseDAO = WarehouseDAO.getInstance();
    }

    public void napuni(){
        for(User u: warehouseDAO.users()){
            users.put(u.getUsername(), u);
        }
        currentUser.setValue(null);
    }

    public ObservableMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(ObservableMap<String, User> users) {
        this.users = users;
    }

    public User getCurrentUser() {
        return currentUser.get();
    }

    public SimpleObjectProperty<User> currentUserProperty() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser.set(currentUser);
    }

    public void addUser(User user){
        this.users.put(user.getUsername(), user);
    }

    public void deleteUser(User user){
        this.users.remove(user.getUsername());
    }

    public Warehouse getUserWarehouse(){
        return warehouseDAO.warehouse(currentUser.get().getUsername());
    }

    public WarehouseDAO getWarehouseDAO(){return warehouseDAO;}
}
