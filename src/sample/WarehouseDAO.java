package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WarehouseDAO {

    private static WarehouseDAO instance;
    private static Connection conn;

    private PreparedStatement dajProizvode;

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
