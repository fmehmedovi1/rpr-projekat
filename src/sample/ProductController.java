package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class ProductController {

    public ListView<Product> listView;
    public TextField fldName, fldPrice;
    public Slider sliderAmount;
    private ProductModel model;

    public ProductController(ProductModel productModel){
        this.model = productModel;
    }

    @FXML
    public void initialize() {

        listView.setItems(model.getProducts());
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldProduct, newProduct) -> {
            model.setCurrentProduct(newProduct);
            model.getCurrentProduct().setAmount((int)sliderAmount.getValue());
            listView.refresh();
        });

        model.currentProductProperty().addListener((obs, oldProduct, newProduct) -> {
            if (oldProduct != null){
                fldName.textProperty().unbindBidirectional(oldProduct.nameProperty());
                fldPrice.textProperty().unbindBidirectional(oldProduct.priceProperty());
                sliderAmount.valueProperty().unbindBidirectional(oldProduct.amountProperty());
            }

            if (newProduct == null){
                fldName.setText("");
                fldPrice.setText("");
            }
            else {
                fldName.textProperty().bindBidirectional(newProduct.nameProperty());
                fldPrice.textProperty().bindBidirectional(newProduct.priceProperty());
                sliderAmount.valueProperty().bindBidirectional(newProduct.amountProperty());
            }

        });

    }
}
