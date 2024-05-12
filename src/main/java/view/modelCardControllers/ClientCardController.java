package view.modelCardControllers;

import dataAccess.ClientDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;
import model.Product;
import view.Application;

import java.io.IOException;

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
    private TextField editNameTextFiled;
    @FXML
    private TextField editEmailAddressTextFiled;
    @FXML
    private TextField editPhoneNumberTextField;
    @FXML
    private TextField clientIdTextField;


    ClientDAO clientDAO = new ClientDAO();

    public void setClientData(Client client){

        clientID.setText(String.valueOf(client.getClientId()));
        clientName.setText(client.getName());
        clientPhoneNumber.setText(client.getPhoneNumber());
        clientEmail.setText(client.getEmail());
    }

    public void setDataInEditView(){
        editNameTextFiled.setText(clientName.getText());
        editPhoneNumberTextField.setText(clientPhoneNumber.getText());
        editEmailAddressTextFiled.setText(clientEmail.getText());
        clientIdTextField.setText(clientID.getText());
    }

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

    public void editClientInDataBase(ActionEvent event) {
        Client client = clientDAO.findById(Integer.parseInt(clientIdTextField.getText()));
        System.out.println(client.getName());

        client.setEmail(editEmailAddressTextFiled.getText());
        client.setPhoneNumber(editEmailAddressTextFiled.getText());
        client.setName(editNameTextFiled.getText());

        clientDAO.update(client);
    }

    public void deleteClientFromDataBase(ActionEvent event) {
        Client client = clientDAO.findById(Integer.parseInt(clientIdTextField.getText()));
        clientDAO.delete(client);
    }
}
