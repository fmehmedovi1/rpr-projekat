package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WarehouseDAO {

    private static WarehouseDAO instance;
    private static Connection conn;
    private PreparedStatement getProducts, getWarehouse, deleteProduct, updateProduct,
            addUser, addProduct;

    private WarehouseDAO(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            getProducts = conn.prepareStatement("SELECT * FROM products p, warehouse_products wp, warehouses w " +
                    "WHERE warehouse_id = wp_warehouse_id AND wp.product_id = p.product_id AND w.warehouse.name = ?");
            getWarehouse = conn.prepareStatement("SELECT * FROM warehouses w, users u " +
                    "WHERE w.responsible_preson_id = u.user_id AND u.username = ?");

            deleteProduct = conn.prepareStatement("DELETE FROM products WHERE id = ?");
            updateProduct = conn.prepareStatement("UPDATE product SET name=?, price=?, quantity=? WHERE id=?");

            addUser = conn.prepareStatement("INSERT INTO product VALUES(?,?,?,?,?,?)");
            addProduct = conn.prepareStatement("INSERT INTO product VALUES(?,?,?,?)");

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

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
