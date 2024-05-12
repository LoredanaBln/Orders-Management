package view.modelMethodsControllers;

import dataAccess.ClientDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Client;
import view.MessagePrinter;
import javafx.scene.control.Label;


public class ClientController {
    @FXML
    private TextField clientEmailTextField;

    @FXML
    private TextField clientNameTextField;

    @FXML
    private TextField clientPhoneNumberTextField;

    @FXML
    private Label popupLabel;


    public void addClient(){
        ClientDAO clientDAO = new ClientDAO();
        Client client = new Client();

        client.setName(clientNameTextField.getText());
        client.setEmail(clientEmailTextField.getText());
        client.setPhoneNumber(clientPhoneNumberTextField.getText());

        clientDAO.insert(client);
        new MessagePrinter().showMessage("Client added successfully!", popupLabel);
    }
}
