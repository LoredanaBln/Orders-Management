package view.modelMethodsControllers;

import bussiness.validators.StringValidator;
import bussiness.validators.QuantityValidator;
import dataAccess.ProductDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Product;
import view.MessagePrinter;

/**
 * Controller class for adding new products.
 */
public class ProductController {

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField productPriceTextField;

    @FXML
    private TextField productQuantityTextField;

    @FXML
    private Label popupLabel;

    /**
     * Adds a new product to the database.
     * Validates input fields and displays error messages if necessary.
     */
    public void addProduct() {
        ProductDAO productDAO = new ProductDAO();
        Product product = new Product();

        Integer quantity = Integer.valueOf(productQuantityTextField.getText());
        QuantityValidator quantityValidator = new QuantityValidator();
        StringValidator stringValidator = new StringValidator();

        if (!quantityValidator.isValid(quantity)) {
            popupLabel.setTextFill(Color.RED);
            new MessagePrinter().showMessage("The quantity is not valid!", popupLabel);
        }
        else if(!stringValidator.isValid(productNameTextField.getText()) || !stringValidator.isValid(productPriceTextField.getText())){
            popupLabel.setTextFill(Color.RED);
            new MessagePrinter().showMessage("The input cannot be empty", popupLabel);
        }else {
            product.setName(productNameTextField.getText());
            product.setPrice(Float.valueOf(productPriceTextField.getText()));
            product.setQuantity(quantity);
            productDAO.insert(product);
            new MessagePrinter().showMessage("Product added successfully!", popupLabel);
        }
    }

}
