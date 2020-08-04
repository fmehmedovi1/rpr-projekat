package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProductModel {
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private SimpleObjectProperty<Product> currentProduct = new SimpleObjectProperty<>();
    private Warehouse warehouse;
    private WarehouseDAO warehouseDAO;

    public ProductModel(Warehouse warehouse, WarehouseDAO warehouseDAO) {
        this.warehouse = warehouse;
        this.warehouseDAO = warehouseDAO;
    }

    public void putData(){
        for(Product p : warehouseDAO.products(warehouse)) {
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
        warehouseDAO.addUpdatesOnProduct(updateOnProduct(product, "+"), product.getId(), warehouse.getId());
        products.add(product);
        warehouseDAO.addProduct(product);
        warehouseDAO.addProductsWarehouse(product.getWarehouse().getId(), product.getId());
        setCurrentProduct(product);
    }

    public void alterProduct(Product product){
        warehouseDAO.addUpdatesOnProduct(updateOnProduct(product, "~"), product.getId(), warehouse.getId());
        products.remove(getCurrentProduct());
        products.add(product);
        setCurrentProduct(product);
        warehouseDAO.updateProducts(product);
    }

    public void removeProduct(Product product){
        warehouseDAO.addUpdatesOnProduct(updateOnProduct(product, "-"), product.getId(), warehouse.getId());
        products.remove(product);
        warehouseDAO.deleteProduct(product);
        warehouseDAO.deleteProductWarehouse(product);
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

    public void writeFile(File file){
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

    public ArrayList<String> getUpdates(){
        return warehouseDAO.getUpdatesOnProduct(warehouse.getId());
    }

    private String updateOnProduct(Product product, String operation){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return operation + product.getName() + ": " + product.getAmount() + "(" + product.getPrice() + " BAM), " + dateFormat.format(date);
    }
}
