package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.sql.*;
import java.util.ResourceBundle;


@ExtendWith(ApplicationExtension.class)
class RegisterControllerTest {
    UserModel model;

    @Start
    public void start (Stage stage) throws Exception {
//        File dbfile = new File("database.db");
//        dbfile.delete();

        model = new UserModel();
//        model.regenerate();
//        model.putData();

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
    void registerAction(FxRobot robot) {
        File dbfile = new File("database.db");
        dbfile.delete();

        model = new UserModel();
        model.regenerate();
        model.putData();

        robot.clickOn("#lblNewAcc") ;
        robot.clickOn("#btnRegister");
        dealWithDialog(robot);

        robot.clickOn("#fldFirstName").write("Paul");
        robot.clickOn("#fldLastName").write("McCartney");
        robot.clickOn("#fldUsername").write("Macca1");

        robot.clickOn("#fldEMail").write("you know it");
        robot.clickOn("#btnRegister");
        dealWithDialog(robot);
        TextField fldEMail = robot.lookup("#fldEMail").queryAs(TextField.class);
        fldEMail.setText("");
        robot.clickOn("#fldEMail").write("macca@hotmail.com");


        robot.clickOn("#fldPassword").write("macca1");
        robot.clickOn("#btnRegister");

        dealWithDialog(robot);
        PasswordField fldPassword = robot.lookup("#fldPassword").queryAs(PasswordField.class);
        fldPassword.setText("");

        robot.clickOn("#fldPassword").write("macca1234");
        robot.clickOn("#fldRePassword").write("macca1233");
        robot.clickOn("#btnRegister");

        PasswordField fldRePassword = robot.lookup("#fldRePassword").queryAs(PasswordField.class);
        fldRePassword.setText("");

        robot.clickOn("#fldRePassword").write("macca1234");

        robot.clickOn("#fldPassword");
        assertEquals("text-input text-field password-field rightData rightData", fldRePassword.getStyleClass().toString());
        robot.clickOn("#btnRegister");

        robot.clickOn("#fldName").write("The Beatles");
        robot.clickOn("#fldAddress").write("Liverpool");
        robot.clickOn("#btnAdd");

        for (int i = 0; i < 100; i++) {
            try {
                // thread to sleep for 1000 milliseconds
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Test
    void registeredInDatabase(FxRobot robot) {
        model.disconnect();
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            try {
                PreparedStatement findPaul = conn.prepareStatement("SELECT first_name FROM users WHERE last_name='McCartney'");
                ResultSet rs = findPaul.executeQuery();
                rs.next();
                String name = rs.getString(1);
                assertEquals("Paul", name);
                conn.close();
            } catch (SQLException e) {
                fail("No user such as McCartney");
            }
        } catch (SQLException e) {
            fail("jdbc:sqlite:korisnici.db doesn't exist or unavailable ");
        }
    }

    private void dealWithDialog(FxRobot robot) {
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }
}