package sample;

public class Warehouse {

    private String name, address;
    private int id, responsiblePerson;

    public Warehouse(int id, String name, String address, int responsiblePerson) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.responsiblePerson = responsiblePerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(int responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }
}
