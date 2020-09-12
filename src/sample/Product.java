package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product implements Comparable<Product> {
    private int id;
    private SimpleStringProperty name, price, expirationDate;
    private SimpleIntegerProperty  amount;
    private Warehouse warehouse;
    private ProductStatus productStatus;

    public Product(int id, String name, String price, int amount, String expirationDate, Warehouse warehouse, String productStatus) throws WrongProductDataException {
        if (id == 0 || name.equals("") || amount == 0 || expirationDate.equals(""))
            throw new WrongProductDataException("Wrong info about product");
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.expirationDate = new SimpleStringProperty(expirationDate);
        this.warehouse = warehouse;
        if (productStatus.equals("VALID")) this.productStatus = ProductStatus.VALID;
        else this.productStatus = ProductStatus.EXPIRED;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws WrongProductDataException {
        if (id == 0) throw new WrongProductDataException("Wrong info about product");
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) throws WrongProductDataException {
        if (name.equals("")) throw new WrongProductDataException("Wrong info about product");
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

    public void setExpirationDate(String expirationDate) throws WrongProductDataException {
        if (expirationDate.equals("")) throw new WrongProductDataException("Wrong info about product");
        this.expirationDate.set(expirationDate);
    }

    public int getAmount() {
        return amount.get();
    }

    public SimpleIntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) throws WrongProductDataException {
        if (amount == 0) throw new WrongProductDataException("Wrong info about product");
        this.amount.set(amount);
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
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
