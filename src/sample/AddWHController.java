package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddWHController {
    public TextField fldName, fldAddress;
    private UserModel model;
    private Locale currentLanguage;

    public AddWHController(UserModel model, Locale currentLanguage) {
        this.model = model;
        this.currentLanguage = currentLanguage;
    }

    public void addWarehouse(ActionEvent actionEvent) throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation", currentLanguage);
        model.getWarehouseDAO().addWarehouse(fldName.getText(), fldAddress.getText(), model.getCurrentUser().getId());
        HomepageController controller = new HomepageController(model, currentLanguage);
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
}
