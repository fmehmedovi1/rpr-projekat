package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LogController {
    private UserModel model;
    public TextField fldUsername;
    public PasswordField fldPassword;

    public LogController(UserModel model) {
        this.model = model;
    }

    public void loginAction(ActionEvent actionEvent) throws IOException {
        if (fldUsername.getText().equals("") || fldPassword.getText().equals("")) {
            warningAlert();
            return;
        }

        if (model.getUsers().containsKey(fldUsername.getText())){
            if (model.getUsers().get(fldUsername.getText()).getPassword().equals(fldPassword.getText())) {
                model.setCurrentUser(model.getUsers().get(fldUsername.getText()));
                HomePageController controller = new HomePageController(model);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
                loader.setController(controller);
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Home Page");
                stage.setScene(new Scene(root, 550, 550));
                stage.show();

                Stage stage2 = (Stage) fldUsername.getScene().getWindow();
                stage2.close();
            }
            else {
                warningAlert();
                return;
            }
        } else {
            warningAlert();
            return;
        }
    }

    private void warningAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("The username or password you’ve entered doesn’t match any account.");
        alert.setContentText("Sign up for an account.");
        alert.showAndWait();
    }

    public void cancelAction(ActionEvent actionEvent){
        model.disconnect();
        Platform.exit();
    }

    public void addUserAction(MouseEvent mouseEvent) throws IOException {
        RegisterController controller = new RegisterController(model);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Register Form");
        stage.setScene(new Scene(root, 350, 400));
        stage.show();

        Stage stage2 = (Stage) fldUsername.getScene().getWindow();
        stage2.close();
    }

}
