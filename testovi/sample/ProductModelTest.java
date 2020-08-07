package sample;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ProductModelTest {
    UserModel model;

    @Test
    void writeFile() {
        File dbfile = new File("database.db");
        dbfile.delete();

        model = new UserModel();
        model.regenerate();
        model.putData();
        model.setCurrentUser(model.getUsers().get("korisnik1"));
        ProductModel productModel = new ProductModel(model.getUserWarehouse(), model.getWarehouseDAO());

        File file = new File("test.txt");
        productModel.putData();
        productModel.writeFile(file);

        try {
            Scanner scanner = new Scanner(file);
            assertEquals("::cigle:10:5::", scanner.nextLine().trim());
            assertEquals("::šibice:1:20::", scanner.nextLine().trim());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            close(file);
        }
        close(file);
    }

    private void close(File file){
        model.disconnect();
        file.delete();
    }
}