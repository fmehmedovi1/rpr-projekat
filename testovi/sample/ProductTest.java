package sample;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    User user = new User(1, "Sead", "Salihovic", "sejo", "sejo@hotmail.com", "sejo123");
    Warehouse warehouse = new Warehouse(1, "Hoffenheim warehouse", "Hoffenheim", user);
    Product lopte;

    {
        try {
            lopte = new Product(5, "lopte", "40", 40, "36", null);
        } catch (WrongProductDataException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        Product cunjevi = null;
        try {
            cunjevi = new Product(1, "cunjevi", "5", 50, "12", warehouse);
        } catch (WrongProductDataException e) {
            e.printStackTrace();
        }
        assertEquals(cunjevi.nameProperty().getValue() + ": " + cunjevi.amountProperty().getValue() + "(" + cunjevi.priceProperty().getValue() + ")", cunjevi.toString());
    }

    @org.junit.jupiter.api.Test
    void getTotalValue() {
        Product umjetnaTrava = null;
        try {
            umjetnaTrava = new Product(1, "umjetnaTrava", "40", 1000, "24", warehouse);
        } catch (WrongProductDataException e) {
            e.printStackTrace();
        }
        assertNotEquals(4000, umjetnaTrava.getTotalValue());
    }

    @org.junit.jupiter.api.Test
    void compareTo() {
        try {
            Product product1 = new Product(1, "dresovi", "10", 5, "24", warehouse),
                    product2 = new Product(1, "kopačke", "40", 20, "24", warehouse);
            assertEquals(-1, product1.compareTo(product2));
        } catch (WrongProductDataException e) {
            e.printStackTrace();
        }

    }

    @org.junit.jupiter.api.Test
    void getWarehouse() {
        lopte.setWarehouse(warehouse);
        assertNotNull(lopte.getWarehouse());
        try {
            lopte.setId((lopte.getId() == 0) ? 1 : 2);
            lopte.setName("Jabulani");
            lopte.setAmount(lopte.getAmount() / 2);
            lopte.setPrice(String.valueOf(Integer.parseInt(lopte.getPrice()) / 2));
            lopte.setExpirationDate(String.valueOf(Integer.parseInt(lopte.getExpirationDate()) + Integer.parseInt(lopte.expirationDateProperty().getValue()) - 1));
        } catch (WrongProductDataException e) {
            e.printStackTrace();
        }
        assertEquals("2 Jabulani 20 20 71", lopte.getId() + " " + lopte.getName() + " " + lopte.getPrice() + " " + lopte.getPrice() + " " + lopte.getExpirationDate());
    }

    @org.junit.jupiter.api.Test
    void constructor() {
        try {
            Product product1 = new Product(1, "dresovi", "10", 5, "24", warehouse);
            product1.setId(0);
            assertThrows(WrongProductDataException.class, () -> product1.setId(0), "Wrong info about product");
        } catch (WrongProductDataException e) {
            e.printStackTrace();
        }

    }

}