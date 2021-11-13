package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class StartController {

    //fxml varaibles
    @FXML
    private Button startButton;

    //------------------------------------------------------------------------------------


    public StartController() {
    }



    /*
    * When start button is clicked...
    * player goes to the character sheet screen
    *
    * Method Action:
    * Switches the stage/screen to the ChooseLevel.fxml
    *
    */
    public void goToChooseLevel(ActionEvent event) throws IOException {

        Stage stage = null;

        Parent myNewscene = null;

        if (event.getSource() == startButton) {
            stage = (Stage) startButton.getScene().getWindow();
            myNewscene = FXMLLoader.load(getClass().getResource("ChooseLevel.fxml"));

        }

        stage.getScene().setRoot(myNewscene);
        stage.setFullScreen(false);
        stage.setTitle("Space Traders");
        stage.show();

    }
}




