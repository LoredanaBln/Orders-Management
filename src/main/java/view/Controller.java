    package view;

    import dataAccess.ClientDAO;
    import dataAccess.ProductDAO;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.geometry.Insets;
    import javafx.scene.Node;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Button;
    import javafx.scene.control.ScrollPane;
    import javafx.scene.input.MouseEvent;
    import javafx.scene.layout.*;
    import javafx.stage.Stage;
    import model.Client;
    import model.Product;
    import view.modelCardControllers.ClientCardController;
    import view.modelCardControllers.ProductCardController;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

    public class Controller {

        private Stage stage;
        private Scene scene;
        private Parent root;


        @FXML
        private Button ordersButton;

        @FXML
        private Button clientsButton;

        @FXML
        private Button productsButton;

        @FXML
        private Button homeButtonHome;




        @FXML
        private Button addButton;


        @FXML
        private ScrollPane viewAllScrollPane;
        @FXML
        private GridPane productsGridPane;

        @FXML
        private ScrollPane viewAllClientsScrollPane;
        @FXML
        private GridPane clientsGridPane;


        private static Controller homePageController;
        private static Controller productsPageController;

        private List<Product> listViewAllProducts;
        private List<Client> listViewAllClients;

        private static ProductDAO productDAO = new ProductDAO();
        private static ClientDAO clientDAO = new ClientDAO();


        public static void setHomePageController(Controller controller) {
            homePageController = controller;
        }

        public static void setProductsPageController(Controller controller) {
            productsPageController = controller;
        }


        public void switchToHome(MouseEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/homePage.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Home");
            stage.show();


            Controller homePageController = loader.getController();
            setHomePageController(homePageController);
            homeButtonHome = (Button) scene.lookup("#homeButtonHome");
            homeButtonHome.setStyle("-fx-background-color: #4a4a4a");
        }

        public void switchToProducts(MouseEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/productsPage.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Products");
            stage.show();

            viewAllScrollPane = (ScrollPane) root.lookup("#viewAllScrollPane");

            if (viewAllScrollPane != null) {
                productsGridPane = (GridPane) viewAllScrollPane.getContent();
                if (productsGridPane != null) {
                    addProducts();
                } else {
                    System.err.println("viewAllGridPane is null. Check your FXML file.");
                }
            } else {
                System.err.println("viewAllScroll is null. Check your FXML file.");
            }
        }

        public void switchToClients(MouseEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/clientPage.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Clients");
            stage.show();

            viewAllClientsScrollPane = (ScrollPane) root.lookup("#viewAllClientsScrollPane");

            if (viewAllClientsScrollPane != null) {
                clientsGridPane = (GridPane) viewAllClientsScrollPane.getContent();
                if (clientsGridPane != null) {
                    addClients();
                } else {
                    System.err.println("viewAllClientsScrollPane is null. Check your FXML file.");
                }
            } else {
                System.err.println("viewAllClientsScrollPane is null. Check your FXML file.");
            }
        }

        public void switchToProductAddPane() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pagesContents/addProductPane.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1035, 605);
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.show();
        }

        public List<Product> viewAllProducts() {
            listViewAllProducts = productDAO.findAll();
            return listViewAllProducts;
        }

        public List<Client> viewAllClients() {
            listViewAllClients = clientDAO.findAll();
            return listViewAllClients;
        }

        public void addProducts() {
            listViewAllProducts = new ArrayList<>(viewAllProducts());
            int column = 1;
            int row = 1;
            try {
                FXMLLoader fxmlLoaderNew = new FXMLLoader();
                fxmlLoaderNew.setLocation(getClass().getResource("/view/modelCards/addProductCard.fxml"));
                AnchorPane cardBoxNew = fxmlLoaderNew.load();
                productsGridPane.add(cardBoxNew, 0,1);

                GridPane.setMargin(cardBoxNew, new Insets(15, 15, 15, 15));
                for (Product product : listViewAllProducts) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/view/modelCards/productCard.fxml"));
                    AnchorPane cardBox = fxmlLoader.load();
                    ProductCardController productCardController = fxmlLoader.getController();
                    productCardController.setData(product);

                    if (column == 3) {
                        column = 0;
                        ++row;
                    }
                    productsGridPane.add(cardBox, column++, row);
                    GridPane.setMargin(cardBox, new Insets(15, 15, 15, 15));
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void addClients() {
            listViewAllClients = new ArrayList<>(viewAllClients());
            int column = 1;
            int row = 1;
            try {
                FXMLLoader fxmlLoaderNew = new FXMLLoader();
                fxmlLoaderNew.setLocation(getClass().getResource("/view/modelCards/addClientCard.fxml"));
                AnchorPane cardBoxNew = fxmlLoaderNew.load();
                clientsGridPane.add(cardBoxNew, 0,1);
                GridPane.setMargin(cardBoxNew, new Insets(15, 25, 15, 25));

                for (Client client : listViewAllClients) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/view/modelCards/clientCard.fxml"));
                    AnchorPane cardBox = fxmlLoader.load();
                    ClientCardController clientCardController = fxmlLoader.getController();
                    clientCardController.setClientData(client);

                    if (column == 5) {
                        column = 0;
                        ++row;
                    }
                    clientsGridPane.add(cardBox, column++, row);
                    GridPane.setMargin(cardBox, new Insets(15, 25, 15, 25));
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void switchToAddClient(MouseEvent mouseEvent) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pagesContents/addClientPane.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1035, 605);
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.show();
        }
    }