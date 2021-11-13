package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ChooseLevelScreenController implements Initializable {

    @FXML
    private Button levelOneButton;
    @FXML
    private Button levelTwoButton;
    @FXML
    private Button levelThreeButton;
    @FXML
    private TextArea nameField;

    //close button -> closes program
    @FXML
    private Button close;

    //instantiate player to track fields
    protected static Player newPlayerTest = new Player();

    public void goToSkillPointsScreen1(ActionEvent event) throws IOException {

        if (!nameField.getText().trim().equals("")
                && (!nameField.getText().equalsIgnoreCase("null"))) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SkillPoints.fxml"));
            Parent root = (Parent) loader.load();

            //setting player name, difficulty, and total skill point fields
            newPlayerTest.setPlayerName(nameField.getText());
            newPlayerTest.setChosenDifficulty("Easy");
            newPlayerTest.setTotalSkillPoints(30);
            newPlayerTest.setCredits(2000.0);

            SkillPointsScreenController nextController = loader.getController();
            nextController.showSkillPointsScreen();

            Stage stage = (Stage) levelOneButton.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setFullScreen(false);
            stage.setTitle("Space Traders");
            stage.show();

        } else {
            nameField.clear();
            nameField.setPromptText("You cannot enter empty or null for this field!");
        }
    }

    public void goToSkillPointsScreen2(ActionEvent event) throws IOException {

        if (!nameField.getText().trim().equals("")
                && (!nameField.getText().equalsIgnoreCase("null"))) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SkillPoints.fxml"));
            Parent root = (Parent) loader.load();

            //setting player name, difficulty, and total skill point fields
            newPlayerTest.setPlayerName(nameField.getText());
            newPlayerTest.setChosenDifficulty("Medium");
            newPlayerTest.setTotalSkillPoints(15);
            newPlayerTest.setCredits(1500.0);

            SkillPointsScreenController nextController = loader.getController();
            nextController.showSkillPointsScreen();

            Stage stage = (Stage) levelTwoButton.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setFullScreen(false);
            stage.setTitle("Space Traders");
            stage.show();

        } else {

            nameField.clear();
            nameField.setPromptText("You cannot enter empty or null for this field!");

        }

    }

    public void goToSkillPointsScreen3(ActionEvent event) throws IOException {

        if (!nameField.getText().trim().equals("")
                && (!nameField.getText().equalsIgnoreCase("null"))) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SkillPoints.fxml"));
            Parent root = (Parent) loader.load();

            //setting player name, difficulty, and total skill point fields
            newPlayerTest.setPlayerName(nameField.getText());
            newPlayerTest.setChosenDifficulty("Hard");
            newPlayerTest.setTotalSkillPoints(5);
            newPlayerTest.setCredits(1000.0);

            SkillPointsScreenController nextController = loader.getController();
            nextController.showSkillPointsScreen();

            Stage stage = (Stage) levelThreeButton.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setFullScreen(false);
            stage.setTitle("Space Traders");
            stage.show();

        } else {

            nameField.clear();
            nameField.setPromptText("You cannot enter empty or null for this field!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //closes out program when hitting close button
    public void handleCloseButtonAction(ActionEvent event) {

        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);

    }
}
