package main;

import dataAccess.ClientDAO;
import dataAccess.OrderDAO;
import dataAccess.ProductDAO;
import javafx.scene.control.Label;
import model.Client;
import model.Order_;
import model.Product;

import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        Product product = new Product();
        product.setName("Apa Lina");
        productDAO.insert(product);

//        ClientDAO clientDAO = new ClientDAO();
//         Client client = clientDAO.findById(1);
//         clientDAO.delete(client);

        //System.out.println(productDAO.findAll());
    }
}
