package viewControllers;

import Main.Main;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginView extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        LoginView.stage = stage;
        Parent root = Main.loadFXML("login-view");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
