package sample;

public class Warehouse {

    private String name, address;
    private int id;
    private User responsiblePerson;

    public Warehouse(String name, String address, int id, User responsiblePerson) {
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
