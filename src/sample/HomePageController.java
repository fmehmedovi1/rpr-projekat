package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    private UserModel model;
    public Label lblName, lblNameWH, lblAddress, lblMyProducts, lblAbout, lblContact;


    public HomePageController(UserModel model) {
        this.model = model;
    }

    public void actionPrint(ActionEvent actionEvent){}

    public void actionOpenProducts(MouseEvent mouseEvent){}

}
