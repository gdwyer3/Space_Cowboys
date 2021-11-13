package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class EndCreditsScreenController {

    @FXML private Button restartButton;

    public void restartGame(ActionEvent event) throws IOException {

        ChooseLevelScreenController.newPlayerTest = new Player();

        Stage stage = null;

        Parent myNewscene = null;

        if (event.getSource() == restartButton) {
            stage = (Stage) restartButton.getScene().getWindow();
            myNewscene = FXMLLoader.load(getClass().getResource("newStart.fxml"));

        }

        stage.getScene().setRoot(myNewscene);
        stage.setFullScreen(false);
        stage.setTitle("Space Traders");
        stage.show();

    }

}
