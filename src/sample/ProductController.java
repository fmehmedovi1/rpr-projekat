package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;

public class ProductController {

    public TableView<Product> tableView;
    public TextField fldName, fldPrice, fldExpiration;
    public Slider sliderAmount;
    public TableColumn colName, colPrice, colAmount, colExpirationDate;
    public MenuBar menuBar;
    private ProductModel model;
    private Warehouse warehouse;
    public Label labelCounter;
    public ChoiceBox<ProductStatus> choiceExpiration;

    public ProductController(ProductModel productModel, Warehouse warehouse){
        this.model = productModel;
        this.warehouse = warehouse;
    }

    @FXML
    public void initialize() {
        ArrayList<ProductStatus> productStatuses = new ArrayList<>();
        productStatuses.add(ProductStatus.VALID);
        productStatuses.add(ProductStatus.EXPIRED);
        ObservableList<ProductStatus> productStatusObservableList = FXCollections.observableArrayList(productStatuses);

        choiceExpiration.setItems(productStatusObservableList);
        choiceExpiration.getSelectionModel().selectFirst();
        tableView.setItems(model.getProducts());
        colName.setCellValueFactory(new PropertyValueFactory("Name"));
        colPrice.setCellValueFactory(new PropertyValueFactory("Price"));
        colAmount.setCellValueFactory(new PropertyValueFactory("Amount"));
        colExpirationDate.setCellValueFactory(new PropertyValueFactory("expirationDate"));

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldProduct, newProduct) -> {
            model.setCurrentProduct(newProduct);
            if (model.getCurrentProduct() != null) {
                try {
                    model.getCurrentProduct().setAmount((int)sliderAmount.getValue());
                    model.getCurrentProduct().setProductStatus(choiceExpiration.getValue());
                } catch (WrongProductDataException e) {
                    e.printStackTrace();
                }
            }
            tableView.refresh();
        });

        model.currentProductProperty().addListener(((obs, oldProduct, newProduct) -> {
            if (oldProduct != null){
                fldName.textProperty().unbindBidirectional(oldProduct.nameProperty());
                fldPrice.textProperty().unbindBidirectional(oldProduct.priceProperty());
                sliderAmount.valueProperty().unbindBidirectional(oldProduct.amountProperty());
                labelCounter.setText(String.valueOf((int) sliderAmount.getValue()));
                fldExpiration.textProperty().unbindBidirectional((oldProduct.expirationDateProperty()));
            }
            if (newProduct == null){
                fldName.setText("");
                fldPrice.setText("0");
                fldExpiration.setText("0");
                labelCounter.setText("1");
            }
            else {
                fldName.textProperty().bindBidirectional(newProduct.nameProperty());
                fldPrice.textProperty().bindBidirectional(newProduct.priceProperty());
                sliderAmount.valueProperty().bindBidirectional(newProduct.amountProperty());
                labelCounter.setText(String.valueOf((int) sliderAmount.getValue()));
                fldExpiration.textProperty().bindBidirectional(newProduct.expirationDateProperty());
                choiceExpiration.setValue(newProduct.getProductStatus());
            }
        }));

        sliderAmount.valueProperty().addListener(((obs, oldValue, newValue) -> {
                if (oldValue != null) labelCounter.setText(String.valueOf(newValue.intValue()));
        }));
    }

    public void addAction(ActionEvent actionEvent) throws WrongProductDataException {
        if (!checkFields()) throw new WrongProductDataException("Wrong info about product");
        model.addProduct(new Product(1, fldName.getText(), fldPrice.getText(), (int) sliderAmount.getValue(),
                fldExpiration.getText(), warehouse, choiceExpiration.getValue().name()));

        tableView.setItems(model.getProducts());
    }

    public void alterAction(ActionEvent actionEvent){
        if (!checkFields()) return;
        model.getCurrentProduct().setProductStatus(choiceExpiration.getValue());
        model.alterProduct(model.getCurrentProduct());
        tableView.setItems(model.getProducts());
        tableView.getSelectionModel().select(model.getCurrentProduct());
    }

    public void removeAction(ActionEvent actionEvent){
        if (tableView.getSelectionModel().getSelectedItem() == null) return;
        Product p = tableView.getSelectionModel().getSelectedItem();
        model.removeProduct(tableView.getSelectionModel().getSelectedItem());
        tableView.setItems(model.getProducts());
        tableView.getSelectionModel().selectLast();
    }

    public void closeAction(ActionEvent actionEvent){
       Stage stage = (Stage) menuBar.getScene().getWindow();
       stage.close();
    }

    public void saveAction(ActionEvent actionEvent){
        JFileChooser fileChooser= new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "*.txt"));
        fileChooser.showSaveDialog(null);
        File file = fileChooser.getSelectedFile();
        model.writeFile(file);
    }

    private boolean checkFields(){
        return !(fldName.getText().equals("") || fldExpiration.getText().equals("") || !fldExpiration.getText().matches(".*\\d.*"));
    }
}
