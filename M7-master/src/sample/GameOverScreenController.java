package sample;

//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverScreenController {

    @FXML private Text gameOverText;

    @FXML private Text difficultyText;
    @FXML private Text creditsText;
    @FXML private Text shipHealthText;
    @FXML private Text shipFuelText;
    @FXML private Text itemsOwnedText;
    @FXML private Text skillPointsText;

    @FXML private Button continueButton;

    public void setPlayerStatus(Player player) {

        difficultyText.setText(difficultyText.getText() + player.getChosenDifficulty());
        creditsText.setText(creditsText.getText() + player.getCredits());
        shipHealthText.setText(shipHealthText.getText() + player.playerShip.getHealth());
        shipFuelText.setText(shipFuelText.getText() + player.playerShip.getCurrentFuel());
        itemsOwnedText.setText(itemsOwnedText.getText() + player.playerShip.getInventorySize());
        skillPointsText.setText(skillPointsText.getText() + player.getTotalSkillPoints());

    }

    public void setGameOverText(boolean wonGame) {

        if (!wonGame) {

            gameOverText.setText("You Lost..");
            gameOverText.setFill(Color.RED);

        }
    }

    /**
     * Takes player to the Game Over Screen
     * @throws IOException - exception
     */
    @FXML
    protected void goToEndCreditsScreen() throws IOException {

        Stage stage = null;
        Parent myNewscene = null;

        stage = (Stage) continueButton.getScene().getWindow();
        myNewscene = FXMLLoader.load(getClass().getResource("EndCredits.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndCredits.fxml"));
        Parent root = (Parent) loader.load();

        stage.getScene().setRoot(root);
        stage.setFullScreen(false);
        stage.setTitle("Game Over....");
        stage.show();

    }
}
