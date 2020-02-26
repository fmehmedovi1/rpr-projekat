package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddWHController {
    public TextField fldName, fldAddress;
    private UserModel userModel;
    private Locale currentLanguage;

    public AddWHController(UserModel model, Locale currentLanguage) {
        this.userModel = model;
        this.currentLanguage = currentLanguage;
    }

    public void addWarehouse(ActionEvent actionEvent) throws IOException {
        for(Warehouse w : userModel.getWarehouseDAO().warehouses()) if (w.getName().equals(fldName.getText())){
            warningAlert("Warehouse with that names already exists");
            return;
        }

        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation", currentLanguage);
        userModel.getWarehouseDAO().addWarehouse(fldName.getText(), fldAddress.getText(), userModel.getCurrentUser().getId());

        HomepageController controller = new HomepageController(userModel, currentLanguage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), resourceBundle);
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Home Page");
        stage.setScene(new Scene(root, 550, 550));
        stage.show();

        Stage stage2 = (Stage) fldName.getScene().getWindow();
        stage2.close();
    }

    private void warningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(message);
        alert.setContentText(null);
        alert.showAndWait();
    }
}
