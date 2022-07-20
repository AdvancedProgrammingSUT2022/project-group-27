module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;


    opens viewControllers to javafx.fxml;
    opens model to com.google.gson;
    exports viewControllers;
}