package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WarehouseDAO {

    private static WarehouseDAO instance;
    private static Connection conn;
    private PreparedStatement getProductsStm, getWarehouseStm, deleteProductStm, updateProductStm,
            addUserStm, addProductStm;

    private WarehouseDAO(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            getProductsStm = conn.prepareStatement("SELECT * FROM products p, warehouse_products wp, warehouses w " +
                    "WHERE warehouse_id = wp_warehouse_id AND wp.product_id = p.product_id AND w.warehouse.name = ?");
            getWarehouseStm = conn.prepareStatement("SELECT * FROM warehouses w, users u " +
                    "WHERE w.responsible_preson_id = u.user_id AND u.username = ?");

            deleteProductStm = conn.prepareStatement("DELETE FROM products WHERE id = ?");
            updateProductStm = conn.prepareStatement("UPDATE product SET name=?, price=?, quantity=? WHERE id=?");

            addUserStm = conn.prepareStatement("INSERT INTO product VALUES(?,?,?,?,?,?)");
            addProductStm = conn.prepareStatement("INSERT INTO product VALUES(?,?,?,?)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static WarehouseDAO getInstance(){
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

    public void addUser(User user){
        try {
            addUserStm.setInt(1, user.getId());
            addUserStm.setString(2,user.getFirstName());
            addUserStm.setString(3,user.getLastName());
            addUserStm.setString(4,user.getUsername());
            addUserStm.setString(5,user.getEmail());
            addUserStm.setString(6,user.getPassword());
            addUserStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(Product product){
        try {
            addProductStm.setInt(1, product.getId());
            addProductStm.setString(2, product.getName());
            addProductStm.setString(3, product.getPrice());
            addProductStm.setInt(4, product.getAmount());
            addProductStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
