package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductUpdates implements Serializable {
    private ArrayList<String> updatesOnProduct = null;

    public ProductUpdates(ArrayList<String> updatesOnProduct) {
        this.updatesOnProduct = updatesOnProduct;
    }

    public ProductUpdates() {}

    public ArrayList<String> getUpdatesOnProduct() {
        return updatesOnProduct;
    }

    public void setUpdatesOnProduct(ArrayList<String> updatesOnProduct) {
        this.updatesOnProduct = updatesOnProduct;
    }
}
