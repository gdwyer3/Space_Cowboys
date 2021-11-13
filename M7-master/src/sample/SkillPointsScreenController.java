package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SkillPointsScreenController
        implements Initializable {

    //variables --------------------------------------------------------------------

    //labels
    @FXML
    private Text playerName; //label that says "Name:"
    @FXML
    private Text selectedDifficulty; //label that says "difficulty"
    @FXML
    private Text skillPointsArea; //label that says "Skill Pts:"

    //pilot skill
    @FXML
    private Text pilotSkill; //pilot label
    @FXML
    private Button plus1; //increase pilot skill
    @FXML
    private Button min1; // decrease pilot skill
    private int pilotCounter = 0; //pilot skill counter

    //engineer skill
    @FXML
    private Text engineerSkill; //engineer label
    @FXML
    private Button plus2; // increase engineer skill
    @FXML
    private Button min2; //decrease engineer skill
    private int engineerCounter = 0;

    //fighter skill
    @FXML
    private Text fighterSkill;
    @FXML
    private Button plus3;
    @FXML
    private Button min3;
    private int fighterCounter = 0;

    //merchant skill
    @FXML
    private Text merchantSkill; //merchant label
    @FXML
    private Button plus4; //increase merchant skill
    @FXML
    private Button min4; //decrease merchant skill
    protected static int merchantCounter = 0; //merchant skill counter

    /*total counter -> calculation of the skill points the player allocates
    *when pressing plus/minus buttons
    used to make sure skills don;t go past the avaliable skill points
    */
    private int totalCounter = 0;

    //submit button -> located on bottom right of screen
    @FXML
    private Button submitButton;

    //close button -> located on top right of screen
    @FXML
    private Button close;

    //-----------------------------------------------------------------------------------

    public SkillPointsScreenController() { }

    public void showSkillPointsScreen() {
        skillPointsArea.setText(Integer.toString(ChooseLevelScreenController.
                newPlayerTest.getTotalSkillPoints()));
        playerName.setText(ChooseLevelScreenController.newPlayerTest.getPlayerName());
        selectedDifficulty.setText(ChooseLevelScreenController.newPlayerTest.getChosenDifficulty());
    }

    public void addSkillPts(ActionEvent event) throws IOException {

        int totalSkill = ChooseLevelScreenController.newPlayerTest.getTotalSkillPoints();

        //PILOT SKILL
        //increment buttons
        plus1.setOnAction(e -> {
            if (totalCounter < totalSkill) {
                pilotCounter++;
                totalCounter++;
                pilotSkill.setText("" + Integer.toString(pilotCounter));
            }
        });

        //decrement button
        min1.setOnAction(e -> {
            if (pilotCounter > 0) {
                pilotCounter--;
                totalCounter--;
                pilotSkill.setText("" + Integer.toString(pilotCounter));
            }

        });

        //FIGHTER SKILL
        //increment button
        plus2.setOnAction(e -> {
            if (totalCounter < totalSkill) {
                fighterCounter++;
                totalCounter++;
                fighterSkill.setText("" + Integer.toString(fighterCounter));
            }
        });

        //decrement button
        min2.setOnAction(e -> {
            if (fighterCounter > 0) {
                fighterCounter--;
                totalCounter--;
                fighterSkill.setText("" + Integer.toString(fighterCounter));
            }

        });

        //MERCHANT SKILL
        //increment button
        plus3.setOnAction(e -> {
            if (totalCounter < totalSkill) {
                merchantCounter++;
                totalCounter++;
                merchantSkill.setText("" + Integer.toString(merchantCounter));
            }
        });

        //decrement button
        min3.setOnAction(e -> {
            if (merchantCounter > 0) {
                merchantCounter--;
                totalCounter--;
                merchantSkill.setText(Integer.toString(merchantCounter));
            }
        });

        //ENGINEER SKILL
        //increase button
        plus4.setOnAction(e -> {
            if (totalCounter < totalSkill) {
                engineerCounter++;
                totalCounter++;
                engineerSkill.setText("" + Integer.toString(engineerCounter));
            }
        });

        //decrement button
        min4.setOnAction(e -> {
            if (engineerCounter > 0) {
                engineerCounter--;
                totalCounter--;
                engineerSkill.setText("" + Integer.toString(engineerCounter));
            }

        });

    }

    /*
     * When submit button is clicked...
     * player goes to the character profile screen
     * displays information player entered (name, difficulty, etc.)
     *
     * Method Action:
     * Switches the stage/screen to the CharacterProfile.fxml
     *
     */
    public void goToCharacterProfile(ActionEvent event) throws IOException {

        if (event.getSource() == submitButton) {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("CharacterProfile.fxml"));
            Parent root = (Parent) loader.load();

            ChooseLevelScreenController.newPlayerTest.setFighterPoints(fighterCounter);
            ChooseLevelScreenController.newPlayerTest.setPilotPoints(pilotCounter);
            ChooseLevelScreenController.newPlayerTest.setEngineerPoints(engineerCounter);
            ChooseLevelScreenController.newPlayerTest.setMerchantPoints(merchantCounter);

            //creating instance of characterSheetScreen controller
            CharacterProfileController nextController = loader.getController();
            nextController.showCharacterProfile();

            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setFullScreen(false);
            stage.setTitle("Space Traders");
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

}
