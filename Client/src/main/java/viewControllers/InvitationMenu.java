package viewControllers;

import controller.NetworkController;
import controller.UserController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Player;
import model.Request;
import model.Response;

import java.io.IOException;
import java.net.Socket;

public class InvitationMenu extends Application {
    private static Stage stage;
    public Button back;
    public Button update;
    public VBox invites;

    @Override
    public void start(Stage stage) throws Exception {
        InvitationMenu.stage = stage;
        Parent root = Main.loadFXML("invitationMenu");
        root.getStylesheets().add(Main.class.getResource("/css/chatRoom.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ancient Civilization/ invitation menu");
        stage.show();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        MainMenuView mainMenuView = new MainMenuView();
        mainMenuView.start(stage);
    }

    public void update(MouseEvent mouseEvent) {
        invites.getChildren().clear();
        System.out.println(invites.getChildren().size()+ "size");
        Request request = new Request();
        request.setHeader("updateInvitationList");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        Response response = NetworkController.send(request);
        String s= (String) response.getData().get("list");
        System.out.println(s + "elrfkm");
        if (s.equals("")) return ;
        String[] arrayList=s.split(",");
        for (int i=0;i<arrayList.length;i++){
            HBox hBox=new HBox();
            hBox.setSpacing(20);
            Label label=new Label("   admin : "+arrayList[i]);
            String admin=arrayList[i];
            Button accept=new Button("accept");
            Button reject=new Button("reject");
            reject.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Request request=new Request();
                    request.setHeader("rejectInvitation");
                    request.addData("user",UserController.getInstance().getUserLoggedIn());
                    request.addData("token",admin);
                    System.out.println("token : "+ admin);
                    Response response=NetworkController.send(request);
                    System.out.println(response.getStatus());
                }
            });
            accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Request request=new Request();
                    request.setHeader("acceptInvitation");
                    request.addData("admin",UserController.getInstance().getUserLoggedIn());
                    request.addData("token",admin);
                    Response response=NetworkController.send(request);
                    System.out.println(response.getStatus());
                    if (response.getStatus() == 200) {
                        try {
                            NetworkController.listenForStartGameOthers(stage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else { //TODO if you change this change Network controller line 51
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("game is starting");
                        alert.show();
                        try {
                            Player player = new Player(UserController.getInstance().getUsername(), new Socket("localhost", 8000), stage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            reject.setTextFill(Color.RED);
            hBox.getChildren().add(label);
            hBox.getChildren().add(accept);
            hBox.getChildren().add(reject);
            invites.getChildren().add(hBox);
        }

    }
}
