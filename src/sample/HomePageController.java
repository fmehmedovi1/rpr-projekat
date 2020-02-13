package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    private UserModel model;
    private ProductModel productModel;
    public Label lblName, lblNameWH, lblAddress, lblMyProducts, lblAbout, lblContact;


    public HomePageController(UserModel model) {
        this.model = model;
        productModel = new ProductModel();
    }

    public void actionPrint(ActionEvent actionEvent){}

    public void actionProducts(MouseEvent mouseEvent) throws IOException {
        ProductController controller = new ProductController(productModel);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/products.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("My Products");
        stage.setScene(new Scene(root, 350, 450));
        stage.show();
    }


}
