package view.modelCardControllers;

import dataAccess.OrderDAO;
import dataAccess.ProductDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Order_;
import model.Product;
import view.Application;
import view.MessagePrinter;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

/**
 * Controller class for managing product information displayed on a card in the UI.
 */
public class ProductCardController {

    @FXML
    private Label productId;
    @FXML
    private Label productName;
    @FXML
    private Label productQuantity;
    @FXML
    private Label productPrice;

    @FXML
    private TextField editNameTextFiled;
    @FXML
    private TextField editPriceTextField;
    @FXML
    private TextField editQuantityTextFiled;
    @FXML
    private TextField productIdTextField;
    @FXML
    private Label popupLabel;

    ProductDAO productDAO = new ProductDAO();
    OrderDAO orderDAO = new OrderDAO();

    /**
     * Sets the data of the product in the UI.
     * @param product The product object containing the data to be displayed.
     */
    public void setData(Product product){
        productId.setText(String.valueOf(product.getProductId()));
        productName.setText(product.getName());
        productQuantity.setText(String.valueOf(product.getQuantity()));
        productPrice.setText(String.valueOf(product.getPrice()));
    }

    /**
     * Retrieves product data and populates the edit view.
     */
    public void retrieveProductData(){
        editNameTextFiled.setText(productName.getText());
        editPriceTextField.setText(productPrice.getText());
        editQuantityTextFiled.setText(productQuantity.getText());
        productIdTextField.setText(productId.getText());
    }

    /**
     * Opens a new window for editing product information.
     * @throws IOException If an IO error occurs.
     */
    public void editProduct() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pagesContents/editProductPane.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1035, 605);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.show();
        editNameTextFiled = (TextField) root.lookup("#editNameTextFiled");
        editPriceTextField = (TextField) root.lookup("#editPriceTextField");
        editQuantityTextFiled = (TextField) root.lookup("#editQuantityTextFiled");
        productIdTextField = (TextField) root.lookup("#productIdTextField");

        retrieveProductData();
    }

    /**
     * Updates product information in the database based on changes made in the edit view.
     */
    public void editProductInDataBase(){
        Product product = productDAO.findById(Integer.parseInt(productIdTextField.getText()));
        product.setPrice(Float.valueOf(editPriceTextField.getText()));
        product.setQuantity(Integer.valueOf(editQuantityTextFiled.getText()));
        product.setName(editNameTextFiled.getText());
        productDAO.update(product);
    }

    /**
     * Checks if the product is associated with any order.
     * @param productId The ID of the product to check.
     * @return True if the product is associated with an order, otherwise false.
     */
    public boolean containsProduct(Integer productId){
        List<Order_> ordersList = orderDAO.findAll();
        for(Order_ order : ordersList){
            if(order.getProductId().equals(productId)){
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes the product from the database.
     */
    public void deleteProductFromDataBase(){
        Product product = productDAO.findById(Integer.parseInt(productIdTextField.getText()));
        if(containsProduct(product.getProductId())){
            popupLabel.setTextFill(Color.RED);
            new MessagePrinter().showMessage("The product is associated with an order", popupLabel);
        }
        else {
            productDAO.delete(product);
            new MessagePrinter().showMessage("Product deleted successfully", popupLabel);
        }
    }
}
