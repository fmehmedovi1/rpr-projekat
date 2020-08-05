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
    public TextField fldName, fldPrice, fldWarranty;
    public Slider sliderAmount;
    public TableColumn colName, colPrice, colAmount, colWarranty;
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
        colWarranty.setCellValueFactory(new PropertyValueFactory("Warranty"));

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldProduct, newProduct) -> {
            model.setCurrentProduct(newProduct);
            if (model.getCurrentProduct() != null) model.getCurrentProduct().setAmount((int)sliderAmount.getValue());
            tableView.refresh();
        });

        model.currentProductProperty().addListener(((obs, oldProduct, newProduct) -> {
            if (oldProduct != null){
                fldName.textProperty().unbindBidirectional(oldProduct.nameProperty());
                fldPrice.textProperty().unbindBidirectional(oldProduct.priceProperty());
                sliderAmount.valueProperty().unbindBidirectional(oldProduct.amountProperty());
                labelCounter.setText(String.valueOf((int) sliderAmount.getValue()));
                fldWarranty.textProperty().unbindBidirectional((oldProduct.warrantyProperty()));
            }
            if (newProduct == null){
                fldName.setText("");
                fldPrice.setText("0");
                fldWarranty.setText("0");
                labelCounter.setText("1");
            }
            else {
                fldName.textProperty().bindBidirectional(newProduct.nameProperty());
                fldPrice.textProperty().bindBidirectional(newProduct.priceProperty());
                sliderAmount.valueProperty().bindBidirectional(newProduct.amountProperty());
                labelCounter.setText(String.valueOf((int) sliderAmount.getValue()));
                fldWarranty.textProperty().bindBidirectional(newProduct.warrantyProperty());
            }
        }));

        sliderAmount.valueProperty().addListener(((obs, oldValue, newValue) -> {
                if (oldValue != null) labelCounter.setText(String.valueOf(newValue.intValue()));
        }));
    }

    public void addAction(ActionEvent actionEvent) throws WrongProductDataException {
        if (!checkFields()) return;;

        int max = 0;
        if (model.getProducts().size() != 0)
            for(Product p : model.getProducts()) if (p.getId() > max) max = p.getId();

        model.addProduct(new Product(max + 1, fldName.getText(), fldPrice.getText(), (int) sliderAmount.getValue(),
                fldWarranty.getText(), warehouse));
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
        if (fldName.getText().equals("") || fldWarranty.getText().equals("") || !fldWarranty.getText().matches(".*\\d.*")) {
            exceptionDialog();
            return false;
        }
        return true;
    }

    private void exceptionDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Wrong info about product");

        WrongProductDataException ex = new WrongProductDataException("Wrong info about product");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }
}
