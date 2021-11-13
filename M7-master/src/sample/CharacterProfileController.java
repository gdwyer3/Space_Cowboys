package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class CharacterProfileController {

    //track so-far allocated skills
    @FXML private Text allocatedPilotSkill;
    @FXML private Text allocatedEngineerSkill;
    @FXML private Text allocatedFighterSkill;
    @FXML private Text allocatedMerchantSkill;

    //track name, current difficulty, starting credits
    @FXML private Text playerName;
    @FXML private Text currentDifficulty;
    @FXML private Text startingCredits;

    @FXML
    private Button travelButton;

    @FXML
    private Button close;


    public void showCharacterProfile() {
        allocatedPilotSkill.setText(Integer.toString(ChooseLevelScreenController.
                newPlayerTest.getPilotPoints()));
        allocatedEngineerSkill.setText(Integer.toString(ChooseLevelScreenController.
                newPlayerTest.getEngineerPoints()));
        allocatedFighterSkill.setText(Integer.toString(ChooseLevelScreenController.
                newPlayerTest.getFighterPoints()));
        allocatedMerchantSkill.setText(Integer.toString(ChooseLevelScreenController.
                newPlayerTest.getMerchantPoints()));
        playerName.setText(ChooseLevelScreenController.newPlayerTest.getPlayerName());
        currentDifficulty.setText(ChooseLevelScreenController.newPlayerTest.getChosenDifficulty());
        startingCredits.setText(ChooseLevelScreenController.
                newPlayerTest.getCredits() + "");
    }

    public void goTravelLog(ActionEvent event) throws IOException {

        Stage stage = null;

        if (event.getSource() == travelButton) {
            stage = (Stage) travelButton.getScene().getWindow();

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("TravelLog.fxml"));
            Parent root = (Parent) loader.load();

            TravelLogController nextController = loader.getController();
            nextController = loader.getController();

            nextController.setInitialRegionActivity();

            //sets the region with the winning item
            String winningRegion = Region.selectWinningItemRegion();
            System.out.println("Region with winning Item: " + winningRegion);

            /*Random randGen = new Random();
            int temp = randGen.nextInt(10);

            nextController.playerStart = temp;
            nextController.startRegion = nextController.map.get(nextController.regionArray[temp]);

            nextController.placeRegionsOnMap(nextController.playerStart);
            //below lines are unnecessary based on below logic with "i" button
            nextController.setRegNameAndButton();
            nextController.setRegTechLevel();
            nextController.setRegDescription();

            // button places 30 units below last visited region's button
            nextController.newVisit.relocate(47, nextController.lastVisited);
            nextController.newVisit.setPrefWidth(150);
            nextController.lastVisited = nextController.newVisit.getLayoutY();
            nextController.newVisit.setText(nextController.startRegion.getRegName());
            nextController.regbuttons.add(nextController.newVisit); // add to button list

            //necessary for map redraw when click on the first region
            nextController.newVisit.setOnMouseClicked(e -> {
                nextController.placeRegionsOnMap(temp);
            });

            // button places 30 units below last visited region's button
            nextController.regionInfo.relocate(200, nextController.lastVisited);
            nextController.regionInfo.setText("i");
            nextController.infoButtons.add(nextController.regionInfo); // add info button to list

            nextController.regionInfo.setOnMouseClicked(e -> {
                nextController.regName.setText(""
                        + nextController.map.get(nextController.regionArray[temp]).getRegName());
                nextController.regDescription.setText(""
                        + nextController.map.get(nextController.regionArray[temp])
                        .getRegDescription());
                nextController.regTechLevel.setText(""
                        + nextController.map.get(nextController.regionArray[temp]).getTechLevel());
                nextController.regionDetails.setVisible(true);

            });

            nextController.logPane.getChildren().addAll(nextController.newVisit);
            nextController.logPane.getChildren().addAll(nextController.regionInfo);
            nextController.prevVisit.add(temp);*/

            stage.getScene().setRoot(root);
            stage.setFullScreen(false);
            stage.setTitle("Travel Log");
            stage.show();
        }
    }

    //closes out program when hitting close button
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }
}