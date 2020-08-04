package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductUpdates implements Serializable {
    private ArrayList<String> updatesOnProduct = new ArrayList<>();

    public ProductUpdates(ArrayList<String> updatesOnProduct) {
        this.updatesOnProduct = updatesOnProduct;
    }

    public ArrayList<String> getUpdatesOnProduct() {
        return updatesOnProduct;
    }

    public void setUpdatesOnProduct(ArrayList<String> updatesOnProduct) {
        this.updatesOnProduct = updatesOnProduct;
    }

    public void addUpdates(String text){
        updatesOnProduct.add(text);
    }
}
