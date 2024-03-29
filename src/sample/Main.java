package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        UserModel model = new UserModel();
        model.putData();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation");
        LogController controller = new LogController(model);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), resourceBundle);
        loader.setController(controller);

        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 350, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {launch(args);}
}
