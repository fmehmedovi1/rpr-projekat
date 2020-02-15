package sample;

import javafx.scene.control.TextField;

public class AddWHController {
    public TextField fldName, fldAddress;
    private UserModel model;

    public AddWHController(UserModel model) {
        this.model = model;
    }
}
