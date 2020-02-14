package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Warehouse {

    private SimpleStringProperty name, address;
    private SimpleIntegerProperty id, responsiblePerson;

    public Warehouse(int id, String name, String address, int responsiblePerson) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.responsiblePerson = new SimpleIntegerProperty(responsiblePerson);
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

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getResponsiblePerson() {
        return responsiblePerson.get();
    }

    public SimpleIntegerProperty responsiblePersonProperty() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(int responsiblePerson) {
        this.responsiblePerson.set(responsiblePerson);
    }
}
