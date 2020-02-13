package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductModel {
    private ObservableList<Product> products = FXCollections.observableArrayList();

    public ProductModel() {}

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }
}
