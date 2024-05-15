package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class for launching the Orders Management Application.
 */
public class Application extends javafx.application.Application {

    /**
     * Starts the application by loading the home page.
     * @param stage The primary stage for this application, onto which the application scene can be set.
     * @throws IOException If an error occurs during loading of the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pages/homePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1255, 755);
        stage.setTitle("Orders Management Application!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method to launch the application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}
