package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    public void regenerate() {

        Connection conn = warehouseDAO.getConn();
        try {
            PreparedStatement deleteWarehouseProducts = conn.prepareStatement("drop table warehouse_products");
            deleteWarehouseProducts.executeUpdate();
            PreparedStatement deleteProductUpdates = conn.prepareStatement("drop table product_updates");
            deleteProductUpdates.executeUpdate();
            PreparedStatement deleteWarehouses = conn.prepareStatement("drop table warehouses");
            deleteWarehouses.executeUpdate();
            PreparedStatement deleteProducts = conn.prepareStatement("drop table products");
            deleteProducts.executeUpdate();
            PreparedStatement deleteUsers = conn.prepareStatement("drop table users");
            deleteUsers.executeUpdate();


            Scanner ulaz = new Scanner(new FileInputStream("database.db.sql"));
            StringBuilder upit = new StringBuilder();
            while (ulaz.hasNext()) {
                upit.append(ulaz.nextLine());
                if (upit.length() > 1) {
                    if (upit.charAt(upit.length() - 1) == ';') {
                        PreparedStatement stmt = conn.prepareStatement(upit.toString());
                        stmt.execute();
                        upit = new StringBuilder();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
