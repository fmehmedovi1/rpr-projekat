package sample;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
    private UserModel model;
    public TextField fldFirstName;
    public TextField fldLastName;
    public TextField fldUsername;
    public PasswordField fldPassword;
    public PasswordField fldRePassword;

    public RegisterController(UserModel model) {
        this.model = model;
    }

}
