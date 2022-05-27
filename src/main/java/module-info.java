module apProjectGroup27 {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    opens view to javafx.fxml;
    opens model to com.google.gson;
    opens Main to javafx.fxml;

    exports Main;
    exports viewControllers;
}