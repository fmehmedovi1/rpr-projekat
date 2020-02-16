package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ProductController {

    public TableView<Product> tableView;
    public TextField fldName;
    public Slider sliderAmount, sliderPrice;
    public TableColumn colName, colPrice, colAmount;
    public MenuBar menuBar;
    private ProductModel model;

    public ProductController(ProductModel productModel){
        this.model = productModel;
    }

    @FXML
    public void initialize() {
        tableView.setItems(model.getProducts());
        colName.setCellValueFactory(new PropertyValueFactory("Name"));
        colPrice.setCellValueFactory(new PropertyValueFactory("Price"));
        colAmount.setCellValueFactory(new PropertyValueFactory("Amount"));

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldProduct, newProduct) -> {
            model.setCurrentProduct(newProduct);
            model.getCurrentProduct().setPrice((int)sliderPrice.getValue());
            model.getCurrentProduct().setAmount((int)sliderAmount.getValue());
            tableView.refresh();
        });

        model.currentProductProperty().addListener(((obs, oldProduct, newProduct) -> {
            if (oldProduct != null){
                fldName.textProperty().unbindBidirectional(oldProduct.nameProperty());
                sliderPrice.valueProperty().unbindBidirectional(oldProduct.priceProperty());
                sliderAmount.valueProperty().unbindBidirectional(oldProduct.amountProperty());
            }
            if (newProduct == null){
                fldName.setText("");
            }
            else {
                fldName.textProperty().bindBidirectional(newProduct.nameProperty());
                sliderPrice.valueProperty().bindBidirectional(newProduct.priceProperty());
                sliderAmount.valueProperty().bindBidirectional(newProduct.amountProperty());
            }
        }));
    }

    public void addAction(ActionEvent actionEvent){
        int max = 0;
        if (model.getProducts().size() != 0)
            for(Product p : model.getProducts()) if (p.getId() > max) max = p.getId();
        model.addProduct(new Product(max + 1, "", 0, 0));
        tableView.getSelectionModel().selectLast();
    }

    public void removeAction(ActionEvent actionEvent){
        model.removeProduct(model.getCurrentProduct());
        tableView.getSelectionModel().selectLast();
    }

    public void closeAction(ActionEvent actionEvent) throws WrongProductDataException {

        for(Product p : model.getProducts()) {
            if (p.getAmount() == 0) throw new WrongProductDataException("Wrong info about product");
        }
       Stage stage = (Stage) menuBar.getScene().getWindow();
       stage.close();
    }

    public void saveAction(ActionEvent actionEvent){
        JFileChooser fileChooser= new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "*.txt"));
        fileChooser.showSaveDialog(null);
        File file = fileChooser.getSelectedFile();
        model.zapisiDatoteku(file);
    }
}
