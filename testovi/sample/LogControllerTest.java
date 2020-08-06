package sample;

import static org.junit.jupiter.api.Assertions.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ResourceBundle;

@ExtendWith(ApplicationExtension.class)
class LogControllerTest {
    UserModel model = new UserModel();

    @Start
    public void start (Stage stage) throws Exception {
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
    void logCancel(FxRobot robot){
        TextField fldUsername = robot.lookup("#fldUsername").queryAs(TextField.class);
        robot.clickOn("#fldUsername").write("korisnik1");
        assertEquals("korisnik1", fldUsername.getText());

        robot.clickOn("#btnLogin");

        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("#fldPassword").write("sifra1");
        robot.clickOn("#btnLogin");

        robot.lookup(".dialog-pane").tryQuery().isPresent();
        dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

    robot.clickOn("#lblNewAcc") ;
    robot.clickOn("#btnCancel");
    }

    @Test
    void changeLanguange(FxRobot robot){
        robot.clickOn("#bosniaButton");
        RadioButton englishButton = robot.lookup("#englishButton").queryAs(RadioButton.class);
        assertEquals(false, englishButton.isSelected());
        robot.clickOn("#btnLogCancel");
    }
}