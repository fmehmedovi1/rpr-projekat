package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddWHController {
    public TextField fldName, fldAddress;
    private UserModel model;

    public AddWHController(UserModel model) {
        this.model = model;
    }

    public void addWarehouse(ActionEvent actionEvent) throws IOException {

        model.getWarehouseDAO().addWarehouse(fldName.getText(), fldAddress.getText(), model.getCurrentUser().getId());
        HomePageController controller = new HomePageController(model);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
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
