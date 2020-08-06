package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class HomepageControllerTest {
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
    void actionPrint(FxRobot robot) {
        File dbfile = new File("database.db");
        dbfile.delete();

        model = new UserModel();
        model.regenerate();
        model.putData();

        robot.clickOn("#fldUsername").write("korisnik1");
        robot.clickOn("#fldPassword").write("sifra123");
        robot.clickOn("#btnLogin");
        robot.clickOn("#btnOtherUsers");

        robot.clickOn("#btnExit");
    }

    @Test
    void actionAbout(FxRobot robot) {
    }

    @Test
    void otherUsersAction(FxRobot robot) {
    }

    @Test
    void saveAsBinary(FxRobot robot) {
    }

    @Test
    void saveAsXml() {
    }

    private void dealWithDialog(FxRobot robot) {
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }
}