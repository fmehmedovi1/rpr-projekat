package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.sql.*;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ProductControllerTest {
    UserModel model;

    @Start
    public void start (Stage stage) throws Exception {
        model = new UserModel();
        model.putData();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation");
        LogController controller = new LogController(model);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), resourceBundle);
        loader.setController(controller);

        Parent root = loader.load();
        stage.setTitle("Login Form");
        stage.setScene(new Scene(root, 350, 300));
        stage.show();
    }

    @Test
    void action1(FxRobot robot) {
        File dbfile = new File("database.db");
        dbfile.delete();

        model = new UserModel();
        model.regenerate();
        model.putData();

        robot.clickOn("#fldUsername").write("korisnik1");
        robot.clickOn("#fldPassword").write("sifra123");
        robot.clickOn("#btnLogin");
        robot.clickOn("#btnProducts");

        robot.clickOn("#fldName").write("Stolica");
        robot.clickOn("#fldPrice").write("2");
        robot.clickOn("#fldExpiration").write("24");

        robot.clickOn("#btnAdd");
        robot.clickOn("#exitBtn");
        robot.clickOn("#btnExit");
    }

    @Test
    void action2(FxRobot robot) {
        robot.clickOn("#fldUsername").write("korisnik1");
        robot.clickOn("#fldPassword").write("sifra123");
        robot.clickOn("#btnLogin");
        robot.clickOn("#btnProducts");

        TableView tableView = robot.lookup("#tableView").queryAs(TableView.class);
        assertEquals(3, tableView.getItems().size());


        robot.clickOn("Stolica");
        robot.clickOn("#fldPrice").write("5");
        robot.clickOn("#btnAlter");
        robot.clickOn("#exitBtn");
        robot.clickOn("#btnExit");
    }

    @Test
    void action3(FxRobot robot) {
        model.disconnect();
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            try {
                PreparedStatement findPrice = conn.prepareStatement("SELECT price FROM products WHERE name='Stolica'");
                ResultSet rs = findPrice.executeQuery();
                rs.next();
                Integer price = rs.getInt(1);
                assertEquals(25, price);
                conn.close();
            } catch (SQLException e) {
                fail("No product such as Stolica");
            }
        } catch (SQLException e) {
            fail("jdbc:sqlite:korisnici.db doesn't exist or unavailable ");
        }
    }

    @Test
    void action4(FxRobot robot) {
        robot.clickOn("#fldUsername").write("korisnik1");
        robot.clickOn("#fldPassword").write("sifra123");
        robot.clickOn("#btnLogin");
        robot.clickOn("#btnProducts");

        robot.clickOn("Stolica");
        robot.clickOn("#btnRemove");

        TableView tableView = robot.lookup("#tableView").queryAs(TableView.class);
        assertEquals(2, tableView.getItems().size());

        robot.clickOn("#fileMenu");
        robot.clickOn("#menuItemClose");
        robot.clickOn("#btnExit");
    }
}