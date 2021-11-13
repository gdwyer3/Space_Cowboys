package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class StartMain extends Application {

    protected static Stage primary;

    private String soundFile = "src/sample/assets/space.mp3";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setFullScreen(false);
        Parent root = FXMLLoader.load(getClass().getResource("newStart.fxml"));
        primaryStage.setTitle("Space Trader");
        primaryStage.setScene(new Scene(root, 1100, 600));
        primaryStage.setMaximized(false);
        primaryStage.show();
        primary = primaryStage;
        //calling music method -> plays background music
        music(soundFile);

    }

    public void music(String filepath) {
        try {
            Media sound = new Media(new File(filepath).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error, file not found!");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
