package view.modelCardControllers;

import dataAccess.ClientDAO;
import dataAccess.OrderDAO;
import dataAccess.ProductDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;
import model.Order_;
import model.Product;
import view.Application;

import java.io.IOException;

/**
 * Controller class for managing order information displayed on a card in the UI.
 */
public class OrderCardController {
    @FXML
    private Label clientEmailLabel;
    @FXML
    private Label clientNameLabel;
    @FXML
    private Label orderIdLabel;
    @FXML
    private Label clientIdLabel;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productIdLabel;
    @FXML
    private Label productQuantityLabel;

    @FXML
    private ChoiceBox<Client> editClientChoiceBox;
    @FXML
    private TextField editOrderQuantityTextField;
    @FXML
    private TextField orderIDTextField;
    @FXML
    private ChoiceBox<Product> editProductChoiceBox;

    private static ClientDAO clientDAO = new ClientDAO();
    private static ProductDAO productDAO = new ProductDAO();
    private static OrderDAO orderDAO = new OrderDAO();

    /**
     * Sets the data of the order in the UI.
     * @param order The order object containing the data to be displayed.
     */
    public void setOrderData(Order_ order){
        Client client = clientDAO.findById(order.getClientId());
        clientEmailLabel.setText(client.getEmail());
        clientNameLabel.setText(client.getName());
        clientIdLabel.setText(client.getClientId().toString());

        Product product = productDAO.findById(order.getProductId());
        productNameLabel.setText(product.getName());
        productIdLabel.setText(product.getProductId().toString());

        productQuantityLabel.setText(String.valueOf(order.getProductQuantity()));
        orderIdLabel.setText(String.valueOf(order.getOrder_Id()));
    }

    /**
     * Populates the edit view with data for editing the order.
     */
    public void getDataForEditPage(){
        editClientChoiceBox.setValue(clientDAO.findById(Integer.parseInt(clientIdLabel.getText())));
        editProductChoiceBox.setValue(productDAO.findById(Integer.parseInt(productIdLabel.getText())));
        editOrderQuantityTextField.setText(productQuantityLabel.getText());

        editClientChoiceBox.getItems().addAll(clientDAO.findAll());
        editProductChoiceBox.getItems().addAll(productDAO.findAll());

        orderIDTextField.setText(orderIdLabel.getText());
    }

    /**
     * Opens a new window for editing order information.
     * @throws IOException If an I  O error occurs.
     */
    public void editOrder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pagesContents/editOrderPane.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1035, 605);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.show();
        editClientChoiceBox = (ChoiceBox<Client>) scene.lookup("#editClientChoiceBox");
        editProductChoiceBox = (ChoiceBox<Product>) scene.lookup("#editProductChoiceBox");
        editOrderQuantityTextField = (TextField)scene.lookup("#editOrderQuantityTextField");
        orderIDTextField = (TextField)scene.lookup("#orderIDTextField");

        getDataForEditPage();
    }

    /**
     * Updates order information in the database based on changes made in the edit view.
     */
    public void updateOrder(){
        Order_ order = orderDAO.findById(Integer.parseInt(orderIDTextField.getText()));
        order.setProductId(editProductChoiceBox.getValue().getProductId());
        order.setClientId(editClientChoiceBox.getValue().getClientId());
        order.setProductQuantity(Integer.valueOf(editOrderQuantityTextField.getText()));
        orderDAO.update(order);
    }

    /**
     * Deletes the order from the database.
     */
    public void deleteOrderFromDataBase(){
        Order_ order = orderDAO.findById(Integer.parseInt(orderIdLabel.getText()));
        orderDAO.delete(order);
    }
}
