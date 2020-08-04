package sample;

import java.util.ArrayList;

public class ProductUpdates {
    private int id;
    private ArrayList<String> updatesOnProduct = new ArrayList<>();

    public ProductUpdates(int id, ArrayList<String> updatesOnProduct) {
        this.id = id;
        this.updatesOnProduct = updatesOnProduct;
    }
}
