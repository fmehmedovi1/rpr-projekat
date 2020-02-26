package sample;

public class Warehouse {
    private int id;
    private String name, address;
    private User responsiblePerson;

    public Warehouse(int id, String name, String address,  User responsiblePerson) {
        this.name = name;
        this.address = address;
        this.id = id;
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

    public User getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(User responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }
}
