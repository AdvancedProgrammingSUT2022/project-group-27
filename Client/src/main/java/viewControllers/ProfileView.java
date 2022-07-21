package viewControllers;

import Enum.MenusInProfile;
import Enum.Message;
import Enum.ProfileImages;
import controller.ProfileController;
import controller.UserController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ProfileView extends Application {
    private static Stage stage;
    private static MediaPlayer audio;
    private static String user = UserController.getInstance().getUserLoggedIn();
    private static MenusInProfile menus;

    private TextField newNickname;
    private TextField newPassword;
    private TextField oldPassword;
    private final Rectangle[] images = new Rectangle[ProfileImages.values().length];
    private final Button exploreFile = new Button("explore file");

    @FXML
    private AnchorPane pane;

    @FXML
    private Button password;

    @FXML
    private Button nickname;

    @FXML
    private Button profile;

    @FXML
    private Button submit;

    @FXML
    private Button back;

    @FXML
    private VBox box;

    @FXML
    private Label username;

    @FXML
    private Rectangle profileImage;

    @FXML
    public void initialize() {
        user = UserController.getInstance().getUserLoggedIn();
        audio.play();
        audio.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                audio.seek(Duration.ZERO);
            }
        });

        ProfileController.getInstance().settingProfile(profileImage, user);

        username.setText("current username: *" + UserController.getInstance().getUsername() + "*, and score: *" + UserController.getInstance().getScore() + "*");
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5);
        shadow.setColor(Color.WHITE);
        username.setEffect(shadow);
        username.setFont(new Font(14));
        back.setCursor(Cursor.HAND);
        submit.setCursor(Cursor.HAND);

        changePasswordButton();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ProfileView.stage = stage;
        audio = Main.loadingAudio("Revenge Is A Dish Best Eaten.mp3");
        Parent root = Main.loadFXML("profile-view");
        root.getStylesheets().add(Main.class.getResource("/css/profile.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ancient Civilization/ Profile Menu");
        stage.show();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        audio.stop();
        MainMenuView mainMenuView = new MainMenuView();
        mainMenuView.start(stage);
    }

    private void removingElementsFromBox() {
        box.getChildren().remove(newNickname);
        box.getChildren().remove(oldPassword);
        box.getChildren().remove(newPassword);
        box.getChildren().remove(exploreFile);
        for (int i = 0; i < ProfileImages.values().length; i++) {
            box.getChildren().remove(images[i]);
        }
        box.getChildren().remove(submit);
        box.getChildren().add(submit);
    }

    public void changePasswordButton() {
        menus = MenusInProfile.CHANGING_PASSWORD;
        password.setDisable(true);
        nickname.setDisable(false);
        profile.setDisable(false);
        removingElementsFromBox();
        oldPassword = new TextField();
        oldPassword.setPromptText("enter your old password");
        newPassword = new TextField();
        newPassword.setPromptText("enter your new password");
        box.getChildren().add(0,newPassword);
        box.getChildren().add(0, oldPassword);
        box.setLayoutX(320);
    }

    public void changeNicknameButton(MouseEvent mouseEvent) {
        menus = MenusInProfile.CHANGING_NICKNAME;
        password.setDisable(false);
        nickname.setDisable(true);
        profile.setDisable(false);
        removingElementsFromBox();
        newNickname = new TextField();
        newNickname.setPromptText("enter your new nickname");
        box.getChildren().add(0, newNickname);
        box.setLayoutX(320);
    }

    public void changeImageButton(MouseEvent mouseEvent) {
        menus = MenusInProfile.CHANGING_PROFILE_IMAGE;
        password.setDisable(false);
        nickname.setDisable(false);
        profile.setDisable(true);
        removingElementsFromBox();
        box.getChildren().remove(submit);
        box.setLayoutX(360);

        ProfileController.getInstance().settingAllImages(images);
        for (int i = 0; i < ProfileImages.values().length; i++) {
            box.getChildren().add(0, images[i]);
            images[i].setCursor(Cursor.HAND);
            Integer index = i;
            images[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    changingImageAction(ProfileImages.values()[index].toString());
                    UserController.getInstance().setCurrentImage(null);
                    ProfileController.getInstance().settingProfile(profileImage, user);
                }
            });
            Rectangle rectangle = new Rectangle();
            images[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    rectangle.setX(480);
                    rectangle.setY(400 - index * 60);
                    rectangle.setHeight(300);
                    rectangle.setWidth(300);
                    rectangle.setFill(images[index].getFill());
                    rectangle.setEffect(new DropShadow());
                    if (!pane.getChildren().contains(rectangle)) pane.getChildren().add(rectangle);
                }
            });
            images[i].setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    pane.getChildren().remove(rectangle);
                }
            });
        }

        box.getChildren().add(0, exploreFile);
        exploreFile.setCursor(Cursor.HAND);
        exploreFile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    changeImageFromUserComputer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void changeImageFromUserComputer() throws IOException {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Pictures");
        //fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null && (file.getPath().toString().contains(".jpg") ||file.getPath().toString().contains(".jpeg") ||
                file.getPath().toString().contains(".png"))) {
            UserController.getInstance().setCurrentImage(file.getPath());
            ProfileController.getInstance().settingProfile(profileImage, user);

            File newFile = new File("./src/main/resources/profile/" + UserController.getInstance().getUsername() + ".jpg");
            newFile.createNewFile();
            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            changingImageAction(UserController.getInstance().getUsername() + ".jpg");
        } else {
            changingImageAction(null);
        }
    }

    public void submitChanges(MouseEvent mouseEvent) {
        switch (menus) {
            case CHANGING_PASSWORD -> changePasswordAction();
            case CHANGING_NICKNAME -> changeNicknameAction();
        }
    }

    private void changingImageAction(String path) {
        if (path == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("invalid image");
            alert.setContentText("sorry but your file is not .jpg / .jpeg / .png");
            alert.show();
        } else {
            UserController.getInstance().setProfileImage(path);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success change");
            alert.setContentText("your profile image changed successfully");
            alert.show();
        }
    }

    private void changePasswordAction() {
        if (oldPassword.getLength() == 0 || newPassword.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("empty field");
            alert.setContentText("sorry, but one of your necessary fields is empty");
            alert.show();
            return;
        }

        String oldPasswordString = oldPassword.getText();
        String newPasswordString = newPassword.getText();

        String message = ProfileController.getInstance().changePassword(newPasswordString, oldPasswordString);
        if (!message.equals(Message.PASSWORD_CHANGED.toString())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("invalid field");
            alert.setContentText(message);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success change");
            alert.setContentText(message);
            alert.show();
            oldPassword.clear();
            newPassword.clear();
        }
    }

    private void changeNicknameAction() {
        if (newNickname.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("empty field");
            alert.setContentText("sorry, but one of your necessary fields is empty");
            alert.show();
            return;
        }

        String newNicknameString = newNickname.getText();

        String message = ProfileController.getInstance().changeNickname(newNicknameString);
        if (!message.equals(Message.NICKNAME_CHANGED.toString())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("invalid field");
            alert.setContentText(message);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success change");
            alert.setContentText(message);
            alert.show();
            newNickname.clear();
        }
    }
}
