package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LogController {
    private UserModel model;
    private Locale currentLanguage;
    public TextField fldUsername;
    public PasswordField fldPassword;
    public RadioButton englishButton, bosniaButton;

    public LogController(UserModel model) {
        this.model = model;
        currentLanguage = new Locale("en", "EN");
    }

    @FXML
    public void initialize() {
        if (currentLanguage == null) setRadioButtons(true, false);
         else {
            if (currentLanguage.getCountry().equals("EN")) setRadioButtons(true, false);
             else setRadioButtons(false, true);
        }

        englishButton.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.equals(true)) bosniaButton.setSelected(false);
            currentLanguage = new Locale("en", "EN");
            loadView();
        }));
        bosniaButton.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.equals(true)) englishButton.setSelected(false);
            currentLanguage = new Locale("bs", "BA");
            loadView();
        }));
    }

    private void loadView() {
        Stage primaryStage = (Stage) fldUsername.getScene().getWindow();
        Locale.setDefault(currentLanguage);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation", currentLanguage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), resourceBundle);
        loader.setController(this);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
    }

    public void loginAction(ActionEvent actionEvent) throws IOException {
        if (fldUsername.getText().equals("") || fldPassword.getText().equals("")) {
            warningAlert();
            return;
        }

        if (model.getUsers().containsKey(fldUsername.getText())){
            if (model.getUsers().get(fldUsername.getText()).getPassword().equals(fldPassword.getText())) {
                model.setCurrentUser(model.getUsers().get(fldUsername.getText()));

                ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation", currentLanguage);
                HomepageController controller = new HomepageController(model, currentLanguage);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homepage.fxml"), resourceBundle);
                loader.setController(controller);
                Parent root = loader.load();
                Stage stage = new Stage();
                if (currentLanguage.getLanguage() == "en") stage.setTitle("Home page");
                else stage.setTitle("Pocetna stranica");
                stage.setScene(new Scene(root, 590, 550));
                stage.show();

                Stage stage2 = (Stage) fldUsername.getScene().getWindow();
                stage2.close();

                stage.setOnHiding(windowEvent -> {
                    model.disconnect();
                });
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
        Stage stage = (Stage) fldUsername.getScene().getWindow();
        stage.close();
    }

    public void addUserAction(MouseEvent mouseEvent) throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation", currentLanguage);
        RegisterController controller = new RegisterController(model, currentLanguage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"), resourceBundle);
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        if (currentLanguage.getLanguage() == "en") stage.setTitle("Register form");
        else stage.setTitle("Forma za registraciju");
        stage.setScene(new Scene(root, 350, 400));
        stage.show();

        Stage stage2 = (Stage) fldUsername.getScene().getWindow();
        stage2.close();

        stage.setOnHiding(windowEvent -> {
            model.disconnect();
        });
    }

    private void setRadioButtons(boolean value1, boolean value2){
        englishButton.setSelected(value1);
        bosniaButton.setSelected(value2);
    }
}
