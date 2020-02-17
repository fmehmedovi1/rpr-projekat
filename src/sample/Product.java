package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product   {
    private int id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty price, amount, warranty;

    public Product(int id, String name, int price, int amount, int warranty) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.warranty = new SimpleIntegerProperty(warranty);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getAmount() {
        return amount.get();
    }

    public SimpleIntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public int getWarranty() {
        return warranty.get();
    }

    public SimpleIntegerProperty warrantyProperty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty.set(warranty);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getTotalValue(){
        return getPrice() * this.getAmount();
    }

    @Override
    public String toString() {
        return name.getValue();
    }
}
