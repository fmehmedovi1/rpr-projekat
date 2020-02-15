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
    private String name;
    private WarehouseDAO warehouseDAO;
    private int id;

    public ProductModel(int id, String name, WarehouseDAO warehouseDAO) {
        this.id = id;
        this.name = name;
        this.warehouseDAO = warehouseDAO;
    }

    public void putData(){
        for(Product p : warehouseDAO.products(name)) {
            products.add(p);
        }
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
        warehouseDAO.addProduct(product);
        setCurrentProduct(product);
    }

    public void removeProduct(Product product){
        warehouseDAO.addChanges(warehouseDAO.changesInProduct(name).size() + 1, "removed", product.getId(), id);
        products.remove(product);
        warehouseDAO.deleteProduct(product);
    }

    public Product getCurrentProduct() {
        return currentProduct.get();
    }

    public SimpleObjectProperty<Product> currentProductProperty() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        if (this.getCurrentProduct() != null) {
            warehouseDAO.updateProducts(this.currentProduct.get());

            if (products.size() > warehouseDAO.changesInProduct(name).size())
                warehouseDAO.addChanges(warehouseDAO.changesInProduct(name).size() + 1, "added",
                    this.currentProduct.get().getId(), id);
        }
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
