package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ProductController {

    public TableView<Product> tableView;
    public TextField fldName, fldPrice, fldExpiration;
    public Slider sliderAmount;
    public TableColumn colName, colPrice, colAmount, colExpirationDate;
    public MenuBar menuBar;
    private ProductModel model;
    private Warehouse warehouse;
    public Label labelCounter;

    public ProductController(ProductModel productModel, Warehouse warehouse){
        this.model = productModel;
        this.warehouse = warehouse;
    }

    @FXML
    public void initialize() {
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
            }
        }));

        sliderAmount.valueProperty().addListener(((obs, oldValue, newValue) -> {
                if (oldValue != null) labelCounter.setText(String.valueOf(newValue.intValue()));
        }));
    }

    public void addAction(ActionEvent actionEvent) throws WrongProductDataException {
        if (!checkFields()) throw new WrongProductDataException("Wrong info about product");

        model.addProduct(new Product(1, fldName.getText(), fldPrice.getText(), (int) sliderAmount.getValue(),
                fldExpiration.getText(), warehouse));
        tableView.getSelectionModel().selectLast();
    }

    public void alterAction(ActionEvent actionEvent){
        if (!checkFields()) return;
        model.alterProduct(model.getCurrentProduct());
        tableView.getSelectionModel().select(model.getCurrentProduct());
    }

    public void removeAction(ActionEvent actionEvent){
        if (!checkFields()) return;
        model.removeProduct(model.getCurrentProduct());
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
