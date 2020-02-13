package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    private UserModel model;
    private ProductModel productModel;
    public Label lblName, lblNameWH, lblAddress, lblNumber;
    public Button btnExit;

    public HomePageController(UserModel model) {
        this.model = model;
        productModel = new ProductModel(model.getCurrentUser().getUsername());
        productModel.napuni();
    }

    @FXML
    public void initialize() {
        lblName.setText(model.getCurrentUser().getFirstName());
        lblNumber.setText(String.valueOf(productModel.getProducts().size()));
        btnExit.setGraphic(new ImageView(new Image("icons/door.png")));
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

    public void actionExit(ActionEvent actionEvent){
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
}
