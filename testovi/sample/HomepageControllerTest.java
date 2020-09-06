package sample;

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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class HomepageControllerTest {
    UserModel model;

    @Start
    public void start (Stage stage) throws Exception {
        model = new UserModel();
        model.regenerate();
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
    void otherUsersAction(FxRobot robot) {
        model = new UserModel();
        model.regenerate();
        model.putData();

        robot.clickOn("#fldUsername").write("korisnik1");
        robot.clickOn("#fldPassword").write("sifra123");
        robot.clickOn("#btnLogin");
        robot.clickOn("#btnOtherUsers");

        robot.clickOn("#btnAbout");

        robot.clickOn("#btnExit");
    }

    @Test
    void saveAsBinary(FxRobot robot) {
        robot.clickOn("#fldUsername").write("korisnik1");
        robot.clickOn("#fldPassword").write("sifra123");
        robot.clickOn("#btnLogin");

        robot.clickOn("#saveMenu");
        robot.clickOn("#menuItem1");

        ListView listView = robot.lookup("#listView").queryAs(ListView.class);
        int size1 =  listView.getItems().size();

        robot.clickOn("#btnProducts");
        robot.clickOn("Šibice");
        robot.clickOn("#btnRemove");
        robot.clickOn("#exitBtn");

        robot.clickOn("#loadMenu");
        robot.clickOn("#menuItem3");

        ListView listView2 = robot.lookup("#listView").queryAs(ListView.class);
        int size2 =  listView2.getItems().size();
        assertEquals(size1, size2 );
        robot.clickOn("#btnExit");
    }

    @Test
    void saveAsXml(FxRobot robot) {
        robot.clickOn("#fldUsername").write("korisnik1");
        robot.clickOn("#fldPassword").write("sifra123");
        robot.clickOn("#btnLogin");

        robot.clickOn("#saveMenu");
        robot.clickOn("#menuItem2");

        ListView listView = robot.lookup("#listView").queryAs(ListView.class);
        int size1 =  listView.getItems().size();

        robot.clickOn("#btnProducts");
        robot.clickOn("Šibice");
        robot.clickOn("#btnRemove");
        robot.clickOn("#exitBtn");

        robot.clickOn("#loadMenu");
        robot.clickOn("#menuItem4");

        ListView listView2 = robot.lookup("#listView").queryAs(ListView.class);
        int size2 =  listView2.getItems().size();
        assertEquals(size1, size2 );
        robot.clickOn("#btnExit");
    }

}