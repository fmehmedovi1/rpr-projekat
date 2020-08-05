package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product implements Comparable<Product> {
    private int id;
    private SimpleStringProperty name, price, expirationDate;
    private SimpleIntegerProperty  amount;
    private Warehouse warehouse;

    public Product(int id, String name, String price, int amount, String expirationDate, Warehouse warehouse) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.expirationDate = new SimpleStringProperty(expirationDate);
        this.warehouse = warehouse;
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

    public String getExpirationDate() {
        return expirationDate.get();
    }

    public SimpleStringProperty expirationDateProperty() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate.set(expirationDate);
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

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public String toString() {
        return getName() + ": " + getAmount() + "(" + getPrice() + ")";
    }

    public int getTotalValue() {
        return getAmount() * Integer.parseInt(getPrice());
    }

    @Override
    public int compareTo(Product o) {
        if (getAmount() > o.getAmount() && getTotalValue() < o.getTotalValue()) return 1;
        if (getAmount() == o.getAmount() && getTotalValue() == o.getTotalValue()) return 1;
        return -1;
    }
}
