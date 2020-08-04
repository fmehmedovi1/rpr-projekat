package sample;

import java.util.ArrayList;

public class ProductUpdates {
    private int id;
    private ArrayList<String> updatesOnProduct = new ArrayList<>();

    public ProductUpdates(int id, ArrayList<String> updatesOnProduct) {
        this.id = id;
        this.updatesOnProduct = updatesOnProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
