package sample;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user = new User(0, "Fran", "Lampard", "frankie1", "frankie@hotmail.com", "ucl2012");

    @org.junit.jupiter.api.Test
    void getterAndSetter() {
        user.setId(1);
        assertEquals(1, user.getId());
        user.setFirstName("Frank");
        assertEquals("Frank", user.getFirstName());
        user.setLastName("Lampard Jr");
        assertEquals("Lampard Jr", user.getLastName());
        user.setUsername("superfrankie1");
        assertEquals("superfrankie1", user.getUsername());
        user.setEmail("superfrankie@hotmail.com");
        assertEquals("superfrankie@hotmail.com", user.getEmail());
        user.setPassword("ucl19052012");
        assertEquals("ucl19052012", user.getPassword());
    }

}