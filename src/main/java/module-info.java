module apProjectGroup27 {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    //requires yagson;

    opens viewControllers to javafx.fxml;
    opens model to com.google.gson, javafx.base;
    opens Main to javafx.fxml;
    opens Enum to com.google.gson;

    exports Main;
    exports viewControllers;
    exports viewControllers.Info;
    opens viewControllers.Info to javafx.fxml;
}