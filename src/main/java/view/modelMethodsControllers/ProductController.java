package view.modelMethodsControllers;

import dataAccess.ProductDAO;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Product;
import view.MessagePrinter;

public class ProductController {

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField productPriceTextField;

    @FXML
    private TextField productQuantityTextField;

    @FXML
    private Label popupLabel;

    public void addProduct(){
        ProductDAO productDAO = new ProductDAO();
        Product product = new Product();

        product.setName(productNameTextField.getText());
        product.setPrice(Float.valueOf(productPriceTextField.getText()));
        product.setQuantity(Integer.valueOf(productQuantityTextField.getText()));

        productDAO.insert(product);
        new MessagePrinter().showMessage("Product added successfully!", popupLabel);
    }

}
