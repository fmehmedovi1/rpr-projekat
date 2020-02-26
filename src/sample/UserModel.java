package sample;

import java.util.HashMap;
import java.util.Map;

public class UserModel {
    private Map<String, User> users = new HashMap<>();
    private User currentUser;
    private WarehouseDAO warehouseDAO;

    public UserModel() {
        warehouseDAO = WarehouseDAO.getInstance();
    }

    public void putData(){
        for(User u: warehouseDAO.users()){
            users.put(u.getUsername(), u);
        }
        currentUser = null;
    }

    public void disconnect(){
        warehouseDAO.removeInstance();
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void addUser(User user){
        this.users.put(user.getUsername(), user);
        warehouseDAO.addUser(user);
    }

    public void deleteUser(User user){
        this.users.remove(user.getUsername());
    }

    public Warehouse getUserWarehouse(){
        return warehouseDAO.getWarehouse(currentUser.getUsername());
    }

    public WarehouseDAO getWarehouseDAO(){return warehouseDAO;}
}
