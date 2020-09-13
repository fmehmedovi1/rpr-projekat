package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddWHController {
    public TextField fldName, fldAddress;
    private UserModel userModel;
    private Locale currentLanguage;
    public ProgressBar progressBar;

    public AddWHController(UserModel model, Locale currentLanguage) {
        this.userModel = model;
        this.currentLanguage = currentLanguage;
    }

    @FXML
    public void initialize() {
        progressBar.setProgress(0.0);
        progressBar.setVisible(false);
    }

    public void addWarehouse(ActionEvent actionEvent) {

        for(Warehouse w : userModel.getWarehouseDAO().warehouses()) if (w.getName().equals(fldName.getText())){
            warningAlert("Warehouse with that names already exists");
            return;
        }

        progressBar.setVisible(true);
        updateProgressBar();

        progressBar.progressProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 1) {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation", currentLanguage);
                userModel.getWarehouseDAO().addWarehouse(fldName.getText(), fldAddress.getText(), userModel.getCurrentUser().getId());
                HomepageController controller = new HomepageController(userModel, currentLanguage);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homepage.fxml"), resourceBundle);
                loader.setController(controller);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Home Page");
                stage.setScene(new Scene(root, 590, 550));
                stage.show();

                Stage stage2 = (Stage) fldName.getScene().getWindow();
                stage2.close();

                stage.setOnHiding(windowEvent -> {
                    userModel.disconnect();
                });
            }
        }));
    }

    private void warningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(message);
        alert.setContentText(null);
        alert.showAndWait();
    }

    private void setProgressBar(int number){
        progressBar.setProgress((double) number / 100.0);
    }

    private void updateProgressBar() {
        new Thread(() -> {
                for(int i = 0; i <= 100; i++) {
                    try {
                        int finalI = i;
                        Platform.runLater(() -> setProgressBar(finalI));
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }).start();
    }
}
