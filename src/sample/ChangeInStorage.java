package sample;

import javafx.beans.property.SimpleStringProperty;

public class ChangeInStorage {
    private int id, product_id, storage_id;
    private SimpleStringProperty typeOfChange;

    public ChangeInStorage(int id, String typeOfChange, int product_id, int storage_id) {
        this.id = id;
        this.product_id = product_id;
        this.storage_id = storage_id;
        this.typeOfChange = new SimpleStringProperty(typeOfChange);
    }


}
