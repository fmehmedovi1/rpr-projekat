package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProductModel {
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private SimpleObjectProperty<Product> currentProduct = new SimpleObjectProperty<>();

    public ProductModel() {}

    public void napuni(){
        products.add(new Product(1, "Jabuke", 10, "10"));
        products.add(new Product(1, "Breskve", 12, "15"));
        currentProduct.setValue(null);
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }

    public Product getCurrentProduct() {
        return currentProduct.get();
    }

    public SimpleObjectProperty<Product> currentProductProperty() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct.set(currentProduct);
    }

    public void zapisiDatoteku(File file){
        if (file == null) return;
        try {
            String text = "";
            FileWriter fileWriter = new FileWriter(file);
            for(Product p : getProducts())
                text += "::" + p.getName().toLowerCase() + ":" + p.getPrice() + ":" + p.getAmount() + "::\n";
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
