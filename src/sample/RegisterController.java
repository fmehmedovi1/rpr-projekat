package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RegisterController {
    private UserModel model;
    public TextField fldFirstName;
    public TextField fldLastName;
    public TextField fldUsername;
    public TextField fldEMail;
    public PasswordField fldPassword;
    public PasswordField fldRePassword;

    public RegisterController(UserModel model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        checkFieldData(fldFirstName);
        checkFieldData(fldLastName);
        checkFieldData(fldUsername);
        checkFieldData(fldEMail);
        checkFieldData(fldPassword);
        checkPasswordData(fldRePassword, fldPassword);
    }

    public void loginAction(ActionEvent actionEvent) throws IOException {

        if (model.getUsers().containsKey(fldUsername.getText())) {
            infoAlert("Username is already taken by another user");
            return;
        }

        if (!emailValidation()){
            warningAlert("Invalid e-mail");
            return;
        }

        if (passwordValidation(fldPassword.getText())){
            infoAlert("Password must contain at least 9 characters with one number at least");
            return;
        }

        if (!fldPassword.getText().equals(fldRePassword)) return;

        User user = new User(model.getUsers().size(), fldFirstName.getText(), fldLastName.getText(),
                fldUsername.getText(), fldEMail.getText(), fldPassword.getText());

        model.getUsers().put(fldUsername.getText(), user);
        model.getWarehouseDAO().addUser(user);
        model.setCurrentUser(user);
        openHomepage();
    }

    private void checkFieldData(TextField textField){
        textField.textProperty().addListener((obs, oldName, newName) -> {
            if (!newName.isEmpty()) rightInfo(textField);
            else wrongInfo(textField);
        });
    }

    private void checkPasswordData(TextField textField1, TextField textField2){
        textField1.textProperty().addListener((obs, oldName, newName) -> {
            if (!newName.isEmpty() && textField1.getText().equals(textField2.getText())) rightInfo(textField1);
            else wrongInfo(textField1);
        });
    }

    private void rightInfo(TextField textField){
        fldRePassword.getStyleClass().removeAll("wrongData");
        fldRePassword.getStyleClass().add("rightData");
    }

    private void wrongInfo(TextField textField){
        textField.getStyleClass().removeAll("rightData");
        textField.getStyleClass().add("wrongData");
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

    private void warningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(message);
        alert.setContentText(null);
        alert.showAndWait();
    }

    private void openHomepage() throws IOException {
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

    public void cancelAction(ActionEvent actionEvent){
        Stage stage = (Stage) fldFirstName.getScene().getWindow();
        stage.close();
    }

    private boolean emailValidation(){

        if (fldEMail.getText().indexOf('@') == -1) return false;
        String text = fldEMail.getText();
        text = fldEMail.getText().substring(text.indexOf('@') + 1, text.length());

        try {
            URL url = new URL("https://www." + text);
            return true;
        } catch (MalformedURLException e) {
                return false;
            }
        }

}
