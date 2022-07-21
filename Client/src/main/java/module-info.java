module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires java.desktop;


    opens viewControllers to javafx.fxml;
    opens model to com.google.gson, javafx.base;
    exports viewControllers;
}