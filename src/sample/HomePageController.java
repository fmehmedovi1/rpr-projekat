package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    private UserModel model;
    private ProductModel productModel;
    public Label lblName, lblNameWH, lblAddress, lblNumber;


    public HomePageController(UserModel model) {
        this.model = model;
        productModel = new ProductModel();
        productModel.napuni();
    }

    @FXML
    public void initialize() {
        lblName.setText("Faris");
        lblNumber.setText(String.valueOf(productModel.getProducts().size()));
    }

    public void actionPrint(ActionEvent actionEvent){}

    public void actionProducts(ActionEvent actionEvent) throws IOException {
        ProductController controller = new ProductController(productModel);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/products.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("My Products");
        stage.setScene(new Scene(root, 350, 450));
        stage.show();

        stage.setOnHiding(windowEvent -> {
            lblNumber.setText(String.valueOf(productModel.getProducts().size()));
        });
    }


}
