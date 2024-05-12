package view.modelMethodsControllers;

import dataAccess.ClientDAO;
import dataAccess.OrderDAO;
import dataAccess.ProductDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;
import model.Order_;
import model.Product;
import view.Application;

import java.io.IOException;

public class OrderController {

    @FXML
    private ChoiceBox<Client> clientChoiceBox;
    @FXML
    private ChoiceBox<Product> productChoiceBox;
    @FXML
    private TextField orderQuantityTextField;
    @FXML
    private TextField orderIDTextFiled;


    private  ProductDAO productDAO = new ProductDAO();
    private static ClientDAO clientDAO = new ClientDAO();
    private static OrderDAO orderDAO = new OrderDAO();


    public void initializeChoiceBoxes(Scene scene) {
        clientChoiceBox = (ChoiceBox<Client>) scene.lookup("#clientChoiceBox");
        productChoiceBox = (ChoiceBox<Product>) scene.lookup("#productChoiceBox");

        if (clientChoiceBox != null && productChoiceBox != null) {
            clientChoiceBox.getItems().addAll(clientDAO.findAll());
            productChoiceBox.getItems().addAll(productDAO.findAll());
        } else {
            System.err.println("One or both choice boxes not found in the scene.");
        }
    }

    public void placeOrder(){
        Order_ order = new Order_();
        order.setClientId(clientChoiceBox.getValue().getClientId());
        order.setProductId(productChoiceBox.getValue().getProductId());

        OrderDAO orderDAO = new OrderDAO();
        orderDAO.insert(order);
    }
}
