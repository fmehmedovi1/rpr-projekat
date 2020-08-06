package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WarehouseDAO {

    private static WarehouseDAO instance = null;
    private static Connection conn = null;
    private PreparedStatement getProductsStm, getWarehouseStm, getUsersStm, deleteProductStm, updateProductStm,
            addUserStm, addProductStm, addWarehouseStm, warehouseIdStm, getUserStm,
            deleteProductsWarehouse, addProductsWarehouse, productIdStm, getWarehousesStm,
            addUpdateOnProductStm, getUpdatesOnProductStm, productUpdatesIdStm, userIdStm;

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

            getUsersStm = conn.prepareStatement("SELECT * FROM users");
            getUserStm = conn.prepareStatement("SELECT * FROM users WHERE user_id=?");
            addUserStm = conn.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?)");
            addUpdateOnProductStm = conn.prepareStatement("INSERT INTO product_updates VALUES(?,?,?,?)");

            addWarehouseStm = conn.prepareStatement("INSERT INTO warehouses VALUES(?,?,?,?)");
            getWarehousesStm = conn.prepareStatement("SELECT * FROM warehouses w");
            getWarehouseStm = conn.prepareStatement("SELECT * FROM warehouses WHERE responsible_preson_id=?");
            warehouseIdStm = conn.prepareStatement("SELECT MAX(warehouse_id)+1 FROM warehouses");
            getUpdatesOnProductStm = conn.prepareStatement("SELECT product_operation FROM product_updates WHERE warehouse_id = ?");

            addProductStm = conn.prepareStatement("INSERT INTO products VALUES(?,?,?,?,?)");
            deleteProductStm = conn.prepareStatement("DELETE FROM products WHERE product_id = ?");
            updateProductStm = conn.prepareStatement("UPDATE products SET name=?, price=?, quantity=?, expiration_date=? WHERE product_id=?");
            productIdStm = conn.prepareStatement("SELECT MAX(product_id)+1 FROM products");
            productUpdatesIdStm = conn.prepareStatement("SELECT MAX(product_updates_id)+1 FROM product_updates");
            userIdStm = conn.prepareStatement("SELECT MAX(user_id)+1 FROM users");

            addProductsWarehouse = conn.prepareStatement("INSERT INTO warehouse_products VALUES(?,?)");
            deleteProductsWarehouse = conn.prepareStatement("DELETE FROM warehouse_products WHERE product_id=?");
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

    public static Connection getConn() {
        return conn;
    }

    public void addUser(User user) {
        try {
            ResultSet rs = userIdStm.executeQuery();
            int id = rs.getInt(1);
            if (id == 0) id++;
            addUserStm.setInt(1, id);
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
            ResultSet rs = productIdStm.executeQuery();
            int id = rs.getInt(1);
            if (id == 0) id++;
            addProductStm.setInt(1, id);
            addProductStm.setString(2, product.getName());
            addProductStm.setInt(3, Integer.parseInt(product.getPrice()));
            addProductStm.setInt(4, product.getAmount());
            addProductStm.setInt(5, Integer.parseInt(product.getExpirationDate()));
            addProductStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addWarehouse(String name, String address, int id){
        try {
            ResultSet rs = warehouseIdStm.executeQuery();
            addWarehouseStm.setInt(1, rs.getInt(1));
            addWarehouseStm.setString(2, name);
            addWarehouseStm.setString(3, address);
            addWarehouseStm.setInt(4, id);
            addWarehouseStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProductsWarehouse(int warehouseId, int productId){
        try {
            addProductsWarehouse.setInt(1, warehouseId);
            addProductsWarehouse.setInt(2, productId);
            addProductsWarehouse.executeUpdate();
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

    public ArrayList<Product> products(Warehouse warehouse){
        ArrayList<Product> result = new ArrayList<>();
        try {
            getProductsStm.setString(1, warehouse.getName());
            ResultSet rs = getProductsStm.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt(1), rs.getString(2),
                        String.valueOf(rs.getInt(3)), rs.getInt(4), String.valueOf(rs.getInt(5)), warehouse);
                result.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Warehouse> warehouses(){
        ArrayList<Warehouse> result = new ArrayList<>();
        try {
            ResultSet rs = getWarehousesStm.executeQuery();
            while (rs.next()) {
                Warehouse warehouse = new Warehouse(rs.getInt(1), rs.getString(2), rs.getString(3), null);
                User user = getUser(rs.getInt(4));
                warehouse.setResponsiblePerson(user);
                result.add(warehouse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                rs.getString(4), rs.getString(5), rs.getString(6));
    }

    private User getUser(int id){
        try {
            getUserStm.setInt(1, id);
            ResultSet rs = getUserStm.executeQuery();
            if (!rs.next()) return null;
//            return getUserFromResultSet(rs);
            return new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Warehouse getWarehouse(int id){
        Warehouse w = null;
        try {
            getWarehouseStm.setInt(1, id);
            ResultSet rs = getWarehouseStm.executeQuery();
            boolean ja = true;
            if (rs.isClosed()) ja = false;
            w = new Warehouse(rs.getInt(1), rs.getString(2), rs.getString(3), null);
            User user = getUser(rs.getInt(4));
            w.setResponsiblePerson(user);
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

    public void deleteProductWarehouse(Product product){
        try {
            deleteProductsWarehouse.setInt(1, product.getId());
            deleteProductsWarehouse.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProducts(Product product){
        try {
            updateProductStm.setString(1, product.getName());
            updateProductStm.setInt(2, Integer.parseInt(product.getPrice()));
            updateProductStm.setInt(3, product.getAmount());
            updateProductStm.setInt(4, Integer.parseInt(product.getExpirationDate()));
            updateProductStm.setInt(5, product.getId());
            updateProductStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUpdatesOnProduct(String updateOperation, int productId, int warehouseId){
        try {
            ResultSet rs = productUpdatesIdStm.executeQuery();
            int id = rs.getInt(1);
            if (id == 0) id++;

            addUpdateOnProductStm.setInt(1, id);
            addUpdateOnProductStm.setString(2, updateOperation);
            addUpdateOnProductStm.setInt(3, productId);
            addUpdateOnProductStm.setInt(4, warehouseId);
            addUpdateOnProductStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public ArrayList<String> getUpdatesOnProduct(int warehouseId){
       ArrayList<String> result = new ArrayList<>();
       try {
           getUpdatesOnProductStm.setInt(1, warehouseId);
           ResultSet rs = getUpdatesOnProductStm.executeQuery();
           while (rs.next()) {
               result.add(rs.getString(1));
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return (result.size() == 0) ?  null : result;
   }

    private void regenrateDatabase() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("database.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.length()>1 && sqlUpit.charAt(sqlUpit.length()-1)==';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}









