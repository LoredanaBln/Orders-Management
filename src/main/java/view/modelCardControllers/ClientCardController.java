package view.modelCardControllers;

import dataAccess.ClientDAO;
import dataAccess.OrderDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Client;
import model.Order_;
import view.Application;
import view.MessagePrinter;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for managing client information displayed on a card in the UI.
 */
public class ClientCardController {

    @FXML
    private Label clientEmail;
    @FXML
    private Label clientName;
    @FXML
    private Label clientPhoneNumber;
    @FXML
    private Label clientID;
    @FXML
    private Label popupLabel;

    @FXML
    private TextField editNameTextFiled;
    @FXML
    private TextField editEmailAddressTextFiled;
    @FXML
    private TextField editPhoneNumberTextField;
    @FXML
    private TextField clientIdTextField;

    ClientDAO clientDAO = new ClientDAO();
    OrderDAO orderDAO = new OrderDAO();

    /**
     * Sets the data of the client in the UI.
     * @param client The client object containing the data to be displayed.
     */
    public void setClientData(Client client){
        clientID.setText(String.valueOf(client.getClientId()));
        clientName.setText(client.getName());
        clientPhoneNumber.setText(client.getPhoneNumber());
        clientEmail.setText(client.getEmail());
    }

    /**
     * Copies client data from display view to edit view for editing.
     */
    public void setDataInEditView(){
        editNameTextFiled.setText(clientName.getText());
        editPhoneNumberTextField.setText(clientPhoneNumber.getText());
        editEmailAddressTextFiled.setText(clientEmail.getText());
        clientIdTextField.setText(clientID.getText());
    }

    /**
     * Opens a new window for editing client information.
     * @throws IOException If an IO error occurs.
     */
    public void editClient() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pagesContents/editClient.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1035, 605);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.show();
        editNameTextFiled = (TextField) root.lookup("#editNameTextFiled");
        editPhoneNumberTextField = (TextField) root.lookup("#editPhoneNumberTextField");
        editEmailAddressTextFiled = (TextField) root.lookup("#editEmailAddressTextFiled");
        clientIdTextField = (TextField) root.lookup("#clientIdTextField");

        setDataInEditView();
    }

    /**
     * Updates client information in the database based on changes made in the edit view.
     */
    public void editClientInDataBase(ActionEvent event) {
        Client client = clientDAO.findById(Integer.parseInt(clientIdTextField.getText()));
        client.setEmail(editEmailAddressTextFiled.getText());
        client.setPhoneNumber(editPhoneNumberTextField.getText());
        client.setName(editNameTextFiled.getText());
        clientDAO.update(client);
    }

    /**
     * Checks if a client is associated with any orders in the database.
     * @param clientId The ID of the client to check.
     * @return True if the client is associated with orders, otherwise false.
     */
    public boolean containsClient(Integer clientId){
        List<Order_> ordersList = orderDAO.findAll();
        for(Order_ order : ordersList){
            if(order.getClientId().equals(clientId)){
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a client from the database if not associated with any orders, and displays a message accordingly.
     */
    public void deleteClientFromDataBase() {
        Client client = clientDAO.findById(Integer.parseInt(clientIdTextField.getText()));
        if(containsClient(client.getClientId())){
            popupLabel.setTextFill(Color.RED);
            new MessagePrinter().showMessage("The client is associated with an order", popupLabel);
        }
        else {
            clientDAO.delete(client);
            new MessagePrinter().showMessage("Client deleted successfully", popupLabel);
        }
    }
}
