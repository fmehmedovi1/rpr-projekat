package sample;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
    User exKapiten = new User(1, "Emir", "Spahić", "spaha", "spaha@hotmail.com", "spaha123");


    @org.junit.jupiter.api.Test
    void testConstructor() {
        Warehouse warehouse = new Warehouse(1, "Bayer WH", "Gacko", null);
        warehouse.setResponsiblePerson(exKapiten);
        assertEquals("spaha", warehouse.getResponsiblePerson().getUsername());

        warehouse.setId(2);
        warehouse.setName("Gacko warehouse");
        warehouse.setAddress("Dubrovnik");
        assertEquals("2 Gacko warehouse Dubrovnik", warehouse.getId() + " " + warehouse.getName() + " " + warehouse.getAddress());
    }
}