package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogController {
    private UserModel model;
    public TextField fldUsername;
    public PasswordField fldPassword;

    public LogController(UserModel model) {
        this.model = model;
    }


    public void loginAction(ActionEvent actionEvent){
        if (fldUsername.getText().equals("") || fldPassword.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("The username or password you’ve entered doesn’t match any account.");
            alert.setContentText("Sign up for an account.");
            alert.showAndWait();
        }


    }


    public void cancelAction(ActionEvent actionEvent){
        Platform.exit();
    }

    public void addUserAction(ActionEvent actionEvent){}
}
