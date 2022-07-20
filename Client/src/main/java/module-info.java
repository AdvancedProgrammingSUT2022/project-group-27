module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;


    opens view to javafx.fxml;
    exports view;
}