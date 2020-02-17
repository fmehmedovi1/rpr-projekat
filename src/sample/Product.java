package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product   {
    private int id;
    private SimpleStringProperty name, price, warranty;
    private SimpleIntegerProperty  amount;

    public Product(int id, String name, String price, int amount, String warranty) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.warranty = new SimpleStringProperty(warranty);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getWarranty() {
        return warranty.get();
    }

    public SimpleStringProperty warrantyProperty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty.set(warranty);
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

    @Override
    public String toString() {
        return name.getValue();
    }
}
