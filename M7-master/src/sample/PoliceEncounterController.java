package sample;

//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PoliceEncounterController extends EncounterController {
    @FXML private Button forfeitButton;
    @FXML private Button fleeButton;
    @FXML private Button fightButton;

    //@FXML private VBox playerShipInfo;
    @FXML private Label shipName = new Label();
    @FXML private Label shipHealth = new Label();
    @FXML private Label shipFuel = new Label();
    @FXML private Label shipCargoSpace = new Label();
    @FXML private Label playerCredits = new Label();
    @FXML private VBox playerShipInfo = new VBox();
    @FXML private Label policeDescription = new Label();
    @FXML private AnchorPane anchorPane = new AnchorPane();
    private int lastVisit;

    private void terminateAndReturn(int region) {
        fightButton.setVisible(false);
        fleeButton.setVisible(false);
        forfeitButton.setVisible(false);
        updateShipInfo();
        super.endEncounter.setLayoutX(750);
        playerShipInfo.getChildren().addAll(super.endEncounter);
        super.goToTravelLog(region);
    }

    public void labelShipName() {
        shipName.setText("Ship Name: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getName());
        if (playerShipInfo.getChildren().contains(shipName)) {
            playerShipInfo.getChildren().remove(shipName);
        }
        playerShipInfo.getChildren().add(shipName);
    }
    public void labelShipHealth() {
        shipHealth.setText("Ship Health: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getHealth());
        if (playerShipInfo.getChildren().contains(shipHealth)) {
            playerShipInfo.getChildren().remove(shipHealth);
        }
        playerShipInfo.getChildren().add(shipHealth);
    }
    public void labelShipFuel() {
        shipFuel.setText("Ship Fuel: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getCurrentFuel());
        if (playerShipInfo.getChildren().contains(shipFuel)) {
            playerShipInfo.getChildren().remove(shipFuel);
        }
        playerShipInfo.getChildren().add(shipFuel);
    }
    public void labelShipCargoSpace() {

        shipCargoSpace.setText("Available Cargo Space: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getCurrentCargoSpace());
        if (playerShipInfo.getChildren().contains(shipCargoSpace)) {
            playerShipInfo.getChildren().remove(shipCargoSpace);
        }
        playerShipInfo.getChildren().add(shipCargoSpace);
    }

    protected ArrayList<Item> shipInventoryList;
    protected Item requestedItem;

    public void pickRequestedItem() {
        int randomIndex = (int) (Math.random() * shipInventoryList.size());

        if (shipInventoryList.size() > 0) {

            requestedItem = shipInventoryList.get(randomIndex);

        } else {

            requestedItem = null;

        }
    }

    public void labelPoliceDescription() {
        pickRequestedItem();
        policeDescription.setText("The Police want to inspect your " + requestedItem.getName());

        if (anchorPane.getChildren().contains(policeDescription)) {
            anchorPane.getChildren().remove(policeDescription);
        }
        anchorPane.getChildren().add(policeDescription);
    }

    public void setValues(Player player, int lastVisit, int regiontoGo) {
        // constructor to initialize variables
        System.out.println("in param constructor");
        this.shipInventoryList = new ArrayList<>();

        Item[] currentInventory = ChooseLevelScreenController
                .newPlayerTest.playerShip.getItemInventory();
        for (int i = 0; i < currentInventory.length; i++) {

            if (currentInventory[i] != null) {

                shipInventoryList.add(currentInventory[i]);

            }

        }

        this.lastVisit = lastVisit;
        this.regiontoGo = regiontoGo;
        Random r = new Random();
        labelShipName();
        labelShipCargoSpace();
        labelShipFuel();
        labelShipHealth();
        labelPoliceDescription();
        playerCredits.setText("Credits: " + ChooseLevelScreenController.newPlayerTest.getCredits());
    }

    protected double policeFighterSkill = (Math.random() * 5) + 1;
    protected double policePilotSkill = (Math.random() * 5) + 1;



    private void updateShipInfo() {
        labelShipCargoSpace();
        labelShipFuel();
        labelShipHealth();
        labelShipName();
    }

    @FXML
    private void fightSpacePolice() throws IOException {
        if (ChooseLevelScreenController.newPlayerTest.getFighterPoints() >= policeFighterSkill) {
            ChooseLevelScreenController.newPlayerTest.playerShip.decreaseFuelAfterTravel();
            //close window
            //go to intended region
            updateShipInfo();
            terminateAndReturn(regiontoGo);
        } else {

            ChooseLevelScreenController
                    .newPlayerTest.playerShip.removeItemFromInventory2(requestedItem);

            ChooseLevelScreenController
                    .newPlayerTest.playerShip.decreaseHealth(
                    ChooseLevelScreenController.newPlayerTest.playerShip.getHealth() - 1);

            if (GameOver.gameOverOrNot()) {

                goToGameOverScreen(fightButton);

            }
            ChooseLevelScreenController.newPlayerTest.playerShip.decreaseFuelAfterTravel();
            //close window
            //go to intended region
            updateShipInfo();
            terminateAndReturn(regiontoGo);
        }
    }

    @FXML
    private void forfeitSpacePolice() {

        ChooseLevelScreenController
                .newPlayerTest.playerShip.removeItemFromInventory2(requestedItem);

        ChooseLevelScreenController.newPlayerTest.playerShip.decreaseFuelAfterTravel();

        //close window
        //go to intended destination
        updateShipInfo();
        terminateAndReturn(regiontoGo);
    }

    @FXML
    private void fleeSpacePolice() throws IOException {
        if (ChooseLevelScreenController.newPlayerTest.getPilotPoints() >= policePilotSkill) {
            //WANTS TO MAKE PILOT POINTS METHOD STATIC
            ChooseLevelScreenController.newPlayerTest.playerShip.decreaseFuelAfterTravel();
            //close window
            //go to intended destination
            updateShipInfo();
            terminateAndReturn(regiontoGo);
        } else {

            ChooseLevelScreenController
                    .newPlayerTest.playerShip.removeItemFromInventory2(requestedItem);
            double newCredits = (0.75 * ChooseLevelScreenController.newPlayerTest.getCredits());
            ChooseLevelScreenController.newPlayerTest.setCredits(newCredits);

            playerCredits.setText("Credits: " + newCredits); // WANTS TO MAKE CREDIT METHODS STATIC
            playerCredits.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            ChooseLevelScreenController.newPlayerTest.playerShip.decreaseHealth(
                    ChooseLevelScreenController.newPlayerTest.playerShip.getHealth() - 1);

            if (GameOver.gameOverOrNot()) {

                goToGameOverScreen(fleeButton);

            }
            ChooseLevelScreenController.newPlayerTest.playerShip.decreaseFuelAfterTravel();
            //close window
            //go to intended destination
            updateShipInfo();
            terminateAndReturn(regiontoGo);
        }
    }

    /**
     * Takes player to the End Credit Screen
     * @param actionButton - button
     * @throws IOException - exception
     */
    protected void goToGameOverScreen(Button actionButton) throws IOException {

        Stage stage = null;
        Parent myNewscene = null;

        stage = (Stage) actionButton.getScene().getWindow();
        myNewscene = FXMLLoader.load(getClass().getResource("GameOverScreen.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverScreen.fxml"));
        Parent root = (Parent) loader.load();
        GameOverScreenController nextController = loader.getController();
        nextController.setPlayerStatus(ChooseLevelScreenController.newPlayerTest);
        nextController.setGameOverText(false);

        stage.getScene().setRoot(root);
        stage.setFullScreen(false);
        stage.setTitle("Game Over....");
        stage.show();

    }

}