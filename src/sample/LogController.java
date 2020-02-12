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

    public void loginAction(ActionEvent actionEvent) {
        if (fldUsername.getText().equals("") || fldPassword.getText().equals("")) {
            warningAlert();
            return;
        }

        if (model.getUsers().containsKey(fldUsername)){
            if (model.getUsers().get(fldUsername).getPassword().equals(fldPassword.getText())) {}
            else {
                warningAlert();
                return;
            }
        } else {
            warningAlert();
            return;
        }
        openNewWindow("/fxml/main.fxml", "Home Page", 550, 550);
    }

    private void warningAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("The username or password you’ve entered doesn’t match any account.");
        alert.setContentText("Sign up for an account.");
        alert.showAndWait();
    }

    public void cancelAction(ActionEvent actionEvent){
        Platform.exit();
    }

    public void addUserAction(MouseEvent mouseEvent) throws IOException {
        openNewWindow("/fxml/register.fxml", "Register Form", 350, 400);
    }

    private void openNewWindow(String nameOfFXML, String stageTitle, int stageWidth, int stageHeight){
        RegisterController controller = new RegisterController(model);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameOfFXML));
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle(stageTitle);
        stage.setScene(new Scene(root, stageWidth, stageHeight));
        stage.show();
    }
}
