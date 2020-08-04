package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomepageController implements ProductOperations {
    private UserModel model;
    private ProductModel productModel;
    private Locale currentLanguage;
    private Warehouse warehouse;
    public Label lblHeader, lblNameWH, lblAddress, lblNumber, lblValue, lblBest, lblWorst;
    public Button btnExit;
    public MenuButton saveMenu;
    public MenuItem menuItem1, menuItem2;
    @FXML
    public ListView<String> listView;

    public HomepageController(UserModel model, Locale currentLanguage) {
        this.model = model;
        this.currentLanguage = currentLanguage;
        productModel = new ProductModel(model.getUserWarehouse(), model.getWarehouseDAO());
        productModel.putData();
        warehouse = model.getUserWarehouse();
    }

    @FXML
    public void initialize() {
        lblHeader.setText(lblHeader.getText() + " " + model.getCurrentUser().getFirstName());
        lblNumber.setText(String.valueOf(productModel.getProducts().size()));
        lblAddress.setText(warehouse.getAddress());
        lblNameWH.setText(warehouse.getName());
        btnExit.setGraphic(new ImageView(new Image("icons/door.png")));
        menuItem1.setGraphic(new ImageView("icons/binaryFileIcon.png"));
        menuItem2.setGraphic(new ImageView("icons/xmlFileIcon.jpg"));

        setUpdatesOnScreen();
        addGrossValue();
        addBestValue();
        addWorstValue();
    }

    public void actionPrint(ActionEvent actionEvent){}

    public void actionProducts(ActionEvent actionEvent) throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation", currentLanguage);
        ProductController controller = new ProductController(productModel, warehouse);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/products.fxml"), resourceBundle);
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("My Products");
        stage.setScene(new Scene(root, 450, 500));
        stage.show();

        stage.setOnHiding(windowEvent -> {
            lblNumber.setText(String.valueOf(productModel.getProducts().size()));
            addGrossValue();
            setUpdatesOnScreen();
        });
    }

    public void actionExit(ActionEvent actionEvent){
        model.disconnect();
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    public void actionAbout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(new Scene(root, 385, 275));
        stage.show();
    }

    public void otherUsersAction(ActionEvent actionEvent){
        try {
            new WarehousesReport().showReport(model.getWarehouseDAO().getConn());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void saveAsBinary(ActionEvent actionEvent){
    }

    public void saveAsXml(ActionEvent actionEvent){
    }

    @Override
    public void addGrossValue(){
        if (productModel.getProducts().size() == 0) return;
        int sum = productModel.getProducts().stream().mapToInt(Product::getTotalValue).sum();
        lblValue.setText(String.valueOf(sum));
    }

    @Override
    public void addWorstValue() {
        if (productModel.getProducts().size() == 0) return;
        Optional<Product> min = productModel.getProducts().stream().min(Product::compareTo);
        lblWorst.setText(String.valueOf(min.get().getTotalValue()));
    }

    @Override
    public void addBestValue() {
        if (productModel.getProducts().size() == 0) return;
        Optional<Product> max = productModel.getProducts().stream().max(Product::compareTo);
        lblBest.setText(String.valueOf(max.get().getTotalValue()));
    }

    private void setUpdatesOnScreen(){
        listView.setItems(null);
    }

}
