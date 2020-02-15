package sample;

import java.sql.*;
import java.util.ArrayList;

public class WarehouseDAO {

    private static WarehouseDAO instance = null;
    private static Connection conn = null;
    private PreparedStatement getProductsStm, getWarehouseStm, getUsersStm, deleteProductStm, updateProductStm,
            addUserStm, addProductStm, getChangesInWarehouse, addChangesStm, addWarehouseStm, countWarehouseStm;

    public WarehouseDAO() {

        try {
            if (conn != null) conn.close();

            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            getProductsStm = conn.prepareStatement("SELECT * FROM products p, warehouse_products wp, warehouses w " +
                    "WHERE w.warehouse_id = wp.warehouse_id AND wp.product_id = p.product_id AND w.name = ?");
            getWarehouseStm = conn.prepareStatement("SELECT * FROM warehouses w, users u " +
                    "WHERE w.responsible_preson_id = u.user_id AND u.username = ?");
            getUsersStm = conn.prepareStatement("SELECT * FROM users");
            getChangesInWarehouse = conn.prepareStatement("SELECT cw.type_of_change, p.name, p.price, p.quantity " +
                    "FROM changes_in_warehouse cw, products p, warehouses w " +
                    "WHERE cw.product_id = p.product_id AND cw.warehouse_id = w.warehouse_id AND w.name=?");

            deleteProductStm = conn.prepareStatement("DELETE FROM products WHERE product_id = ?");
            updateProductStm = conn.prepareStatement("UPDATE products SET name=?, price=?, quantity=? WHERE product_id=?");

            addUserStm = conn.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?)");
            addProductStm = conn.prepareStatement("INSERT INTO products VALUES(?,?,?,?)");
            addChangesStm = conn.prepareStatement("INSERT INTO changes_in_warehouse VALUES(?,?,?,?)");
            addWarehouseStm = conn.prepareStatement("INSERT INTO warehouses VALUES(?,?,?,?)");
            countWarehouseStm = conn.prepareStatement("SELECT COUNT(warehouse_id)+1 FROM warehouses");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static WarehouseDAO getInstance() {
        if (instance == null) instance = new WarehouseDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    private void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            addUserStm.setInt(1, user.getId());
            addUserStm.setString(2, user.getFirstName());
            addUserStm.setString(3, user.getLastName());
            addUserStm.setString(4, user.getUsername());
            addUserStm.setString(5, user.getEmail());
            addUserStm.setString(6, user.getPassword());
            addUserStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(Product product) {
        try {
            addProductStm.setInt(1, product.getId());
            addProductStm.setString(2, product.getName());
            addProductStm.setString(3, String.valueOf(product.getPrice()));
            addProductStm.setInt(4, product.getAmount());
            addProductStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addChanges(int id, String text, int product_id, int warehouse_id){
        try {
            addChangesStm.setInt(1, id);
            addChangesStm.setString(2, text);
            addChangesStm.setInt(3, product_id);
            addChangesStm.setInt(4, warehouse_id);
            addChangesStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addWarehouse(String name, String address, int id){
        try {
            ResultSet rs = countWarehouseStm.executeQuery();
            addWarehouseStm.setInt(1, rs.getInt(1) + 1);
            addWarehouseStm.setString(2, name);
            addWarehouseStm.setString(3, address);
            addWarehouseStm.setInt(4, id);
            addWarehouseStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> users() {
        ArrayList<User> result = new ArrayList<>();
        try {
            ResultSet rs = getUsersStm.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                result.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Product> products(String name){
        ArrayList<Product> result = new ArrayList<>();
        try {
            getProductsStm.setString(1, name);
            ResultSet rs = getProductsStm.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
                result.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Warehouse warehouse(String username){
        Warehouse w = null;
        try {
            getWarehouseStm.setString(1, username);
            ResultSet rs = getWarehouseStm.executeQuery();
            w = new Warehouse(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getInt(4));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return w;
    }

    public void deleteProduct(Product product){
        try {
            deleteProductStm.setInt(1, product.getId());
            deleteProductStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProducts(Product product){
        try {
            updateProductStm.setString(1, product.getName());
            updateProductStm.setString(2, product.getPrice());
            updateProductStm.setInt(3, product.getAmount());
            updateProductStm.setInt(4, product.getId());
            updateProductStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> changesInProduct(String name){
        ArrayList<String> result = new ArrayList<>();
        try {
            getChangesInWarehouse.setString(1, name);
            ResultSet rs = getChangesInWarehouse.executeQuery();
            while(rs.next()){
                String text = rs.getString(2) + " : " + rs.getString(1) + ", value: "
                        + rs.getString(3) + ", quantity: " + rs.getString(4);
                result.add(text);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


}









