package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    private UserModel model;
    public TextField fldFirstName;
    public TextField fldLastName;
    public TextField fldUsername;
    public PasswordField fldPassword;
    public PasswordField fldRePassword;

    public RegisterController(UserModel model) {
        this.model = model;
    }

    public void loginAction(ActionEvent actionEvent) {
        if (fldFirstName.getText().equals("") || fldLastName.getText().equals("") || fldUsername.getText().equals("")
                || fldPassword.getText().equals("") || fldRePassword.getText().equals("")) {
            warningAlert();
            return;
        }

        if (model.getUsers().containsKey(fldUsername.getText())) {
            infoAlert("Username is already taken by another user");
            return;
        }

        if (passwordValidation(fldPassword.getText())){
            infoAlert("Password must contain at least 9 characters with one number at least");
            return;
        }

        if (fldPassword.getText().equals(fldRePassword)){
            fldPassword.getStyleClass().removeAll("PoljeNijeIspravno");
            fldRePassword.getStyleClass().removeAll("PoljeNijeIspravno");
            return;
        }
        else {
            fldPassword.getStyleClass().add("PoljeNijeIspravno");
            fldRePassword.getStyleClass().add("PoljeNijeIspravno");
        }
    }

    private boolean passwordValidation(String password){
        return password.contains("[a-zA-Z]+") == false && password.length() > 9;
    }

    private void infoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void warningAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("The username you’ve entered doesn’t match any account.");
        alert.setContentText(null);
        alert.showAndWait();
    }

    public void cancelAction(ActionEvent actionEvent){
        Stage stage = (Stage) fldFirstName.getScene().getWindow();
        stage.close();
    }
}
