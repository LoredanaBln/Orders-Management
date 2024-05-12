module com.example.hw3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;

    opens view to javafx.fxml;
    exports view;
    exports view.modelCardControllers;
    opens view.modelCardControllers to javafx.fxml;
    opens view.modelPageController to javafx.fxml;
    exports view.modelPageController;

    exports view.modelMethodsControllers;
    opens view.modelMethodsControllers to javafx.fxml;

}