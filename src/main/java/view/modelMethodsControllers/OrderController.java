package view.modelMethodsControllers;

import bussiness.validators.QuantityValidator;
import dataAccess.BillDAO;
import dataAccess.ClientDAO;
import dataAccess.OrderDAO;
import dataAccess.ProductDAO;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Bill;
import model.Client;
import model.Order_;
import model.Product;
import view.MessagePrinter;

/**
 * Controller class for placing orders.
 */
public class OrderController {

    @FXML
    private ChoiceBox<Client> clientChoiceBox;
    @FXML
    private ChoiceBox<Product> productChoiceBox;
    @FXML
    private TextField orderQuantityTextField;
    @FXML
    private Label popupLabel;

    private  ProductDAO productDAO = new ProductDAO();
    private static ClientDAO clientDAO = new ClientDAO();

    /**
     * Initializes the choice boxes with data from the database.
     * @param scene The scene in which the choice boxes are located.
     */
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

    /**
     * Places a new order with the selected client and product.
     * Checks if the quantity is valid and updates the database.
     */
    public void placeOrder(){
        Order_ order = new Order_();
        order.setClientId(clientChoiceBox.getValue().getClientId());
        order.setProductId(productChoiceBox.getValue().getProductId());

        Integer quantity = Integer.valueOf(orderQuantityTextField.getText());
        QuantityValidator quantityValidator = new QuantityValidator();
        if(!quantityValidator.isValid(quantity) || (quantity > productChoiceBox.getValue().getQuantity())){
            popupLabel.setTextFill(Color.RED);
            new MessagePrinter().showMessage("Invalid quantity!", popupLabel);
        }
        else {
            order.setProductQuantity(quantity);
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.insert(order);

            Client client = clientChoiceBox.getValue();
            Product product = productChoiceBox.getValue();

            Bill bill = new Bill(order.getOrder_Id(), client.getName(), client.getEmail(), client.getPhoneNumber(), product.getName(), order.getProductQuantity());
            BillDAO billDAO = new BillDAO();
            billDAO.insert(bill);
            popupLabel.setTextFill(Color.WHITE);
            new MessagePrinter().showMessage("Order added successfully!", popupLabel);
        }
    }
}
