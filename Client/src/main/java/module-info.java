module client.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens client.client.view to javafx.fxml;
    exports client.client.view;
}