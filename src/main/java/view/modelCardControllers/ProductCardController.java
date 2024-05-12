package view.modelCardControllers;

import dataAccess.ProductDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Product;
import view.Application;

import java.awt.event.ActionEvent;
import java.io.IOException;

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

    ProductDAO productDAO = new ProductDAO();


    public void setData(Product product){

        productId.setText(String.valueOf(product.getProductId()));
        productName.setText(product.getName());
        productQuantity.setText(String.valueOf(product.getQuantity()));
        productPrice.setText(String.valueOf(product.getPrice()));
    }

    public void retrieveProductData(){
        editNameTextFiled.setText(productName.getText());
        editPriceTextField.setText(productPrice.getText());
        editQuantityTextFiled.setText(productQuantity.getText());
        productIdTextField.setText(productId.getText());
    }

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


    public void editProductInDataBase(){
        Product product = productDAO.findById(Integer.parseInt(productIdTextField.getText()));
        System.out.println(product.getName());

        product.setPrice(Float.valueOf(editPriceTextField.getText()));
        product.setQuantity(Integer.valueOf(editQuantityTextFiled.getText()));
        product.setName(editNameTextFiled.getText());

        productDAO.update(product);
    }

    public void deleteProductFromDataBase(){
        Product product = productDAO.findById(Integer.parseInt(productIdTextField.getText()));
        productDAO.delete(product);
    }
}
