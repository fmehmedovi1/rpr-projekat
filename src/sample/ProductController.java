package sample;

import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class ProductController {

    public ListView<Product> listView;
    public TextField fldName, fldPrice;
    public Slider sliderAmount;
    private ProductModel model;

    public ProductController(ProductModel productModel){}



}
