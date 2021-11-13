package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class BanditEncounterController extends EncounterController {
    // access player
    private Player player = ChooseLevelScreenController.newPlayerTest;
    private int lastVisit;
    private int regiontoGo; // region player is trying to get to
    private int merchant;
    private int pilot;
    private int demand;
    private int fight;
    @FXML private Label shipName = new Label();
    @FXML private Label shipHealth = new Label();
    @FXML private Label shipFuel = new Label();
    @FXML private Label shipCargoSpace = new Label();
    @FXML private Label pCredits = new Label();
    @FXML private Label fighter = new Label();
    @FXML private Label merchanter = new Label();
    @FXML private Label piloter = new Label();
    @FXML private Button payB;
    @FXML private Button fleeB;
    @FXML private Button fightB;
    @FXML protected VBox playerInfo = new VBox();
    @FXML protected AnchorPane mainPane;
    @FXML protected Label banditDescription = new Label();
    protected Label bandit = new Label();

    private void terminateAndReturn(int region) { // return to specified region
        //close encounter window/ screen
        updatePlayerInfo();
        payB.setVisible(false);
        fightB.setVisible(false);
        fleeB.setVisible(false);
        playerInfo.getChildren().addAll(super.endEncounter);
        super.goToTravelLog(region);
    }

    public void addSkills() {
        fighter.setText("Fight Skill: " + fight);
        merchanter.setText("Merchant Skill: " + merchant);
        piloter.setText("Pilot Skill: " + pilot);
        playerInfo.getChildren().addAll(fighter, piloter, merchanter);
    }

    public void setValues(Player player, int lastVisit, int regiontoGo) {
        // constructor to initialize variables
        this.player = player;
        this.lastVisit = lastVisit;
        this.regiontoGo = regiontoGo;
        merchant = player.getMerchantPoints();
        pilot = player.getPilotPoints();
        fight = player.getFighterPoints();
        Random r = new Random();
        demand = r.nextInt(2001) + 500; // bandit demands 500 - 2500 credits randomly
        setDemand(demand);
        labelShipName();
        labelShipCargoSpace();
        labelShipFuel();
        labelShipHealth();
        updatePlayerInfo();
        addSkills();
    }


    public void labelShipName() {
        shipName.setText("Ship Name: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getName());
        if (!playerInfo.getChildren().contains(shipName)) {
            playerInfo.getChildren().add(shipName);
        }
    }
    public void labelShipHealth() {
        shipHealth.setText("Ship Health: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getHealth());
        if (!playerInfo.getChildren().contains(shipHealth)) {
            playerInfo.getChildren().add(shipHealth);
        }

    }
    public void labelShipFuel() {
        shipFuel.setText("Ship Fuel: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getCurrentFuel());
        if (!playerInfo.getChildren().contains(shipFuel)) {
            playerInfo.getChildren().add(shipFuel);
        }

    }
    public void labelShipCargoSpace() {
        shipCargoSpace.setText("# Items in Cargo: "
            + ChooseLevelScreenController.newPlayerTest.playerShip.getSize());
        if (!playerInfo.getChildren().contains(shipCargoSpace)) {
            playerInfo.getChildren().addAll(shipCargoSpace);
        }
    }

    public void updatePlayerInfo() {
        double i = player.getCredits();
        pCredits.setText("Credits: " + i);
        if (playerInfo.getChildren().contains(pCredits)) {
            playerInfo.getChildren().remove(pCredits);
        }
        playerInfo.getChildren().add(pCredits);
    }

    protected void setDemand(int demand) {
        //banditDescription = new Label("The bandits are demanding " + demand + " credits!");
        banditDescription.setText("The bandits are demanding " + demand + " credits!");
        if (mainPane.getChildren().contains(banditDescription)) {
            mainPane.getChildren().remove(banditDescription);
        }
        mainPane.getChildren().add(banditDescription);
    }

    // so far assuming that bandit does not need to have its own class since it is
    // NPC anything it does is through the encounter


    /*Pay the bandit's demand and continue to the desired destination. If the player cannot
    afford the bandit's demands, then the player must give the bandit all the items in their
    inventory. If the player has no items, the bandit will damage the ship's health. Then the
    player continues to the target destination. */
    @FXML
    private void playerPayBandit() throws IOException {
        // if player has enough credits subtract amount of demand from credits
        if (player.getCredits() - demand >= 0) {
            player.setCredits(Math.max(player.getCredits() - demand, 0));
            updatePlayerInfo();
            terminateAndReturn(regiontoGo);
        } else if (player.playerShip.numItemInInventory() > 0) { // when player has items to give up
            // give up all items in inventory
            ChooseLevelScreenController.newPlayerTest.playerShip.clearInventory();
            updateShipInfo();
            terminateAndReturn(regiontoGo);
        } else {
            ChooseLevelScreenController.newPlayerTest.playerShip.decreaseHealth(
                    15); // damage ship health

            if (GameOver.gameOverOrNot()) {

                goToGameOverScreen(payB);

            }
            updateShipInfo();
            terminateAndReturn(regiontoGo);
        }


    }

    private void updateShipInfo() {
        labelShipHealth();
        labelShipFuel();
        labelShipCargoSpace();
        labelShipName();
    }

    /*Try to flee back to the previous region. The success of fleeing is dependent on the
    player’s Pilot skill (higher Pilot level, higher chance of escape). If the player successfully
    flees back to the original region, they should still lose the fuel required to travel initially,
    but they keep all their credits & items and they are safe. If the player fails to flee, the
    bandit will take all their credits and damage the health value of the player's ship. */
    @FXML
    private void playerFlee() throws IOException {
        Random r = new Random();
        int p = r.nextInt(11);

        if (((pilot) > 7 && player.getChosenDifficulty().equalsIgnoreCase("Hard")
                && p < 4)) {
            terminateAndReturn(lastVisit); // to previous region
            // more chances if pilot skill higher
        } else if (((pilot > 4 && player.getChosenDifficulty().equalsIgnoreCase("Easy")
                && p < 5))
                || ((pilot > 6 && player.getChosenDifficulty().equalsIgnoreCase("Medium"))
                && p < 4)) {
            terminateAndReturn(lastVisit);
        } else {
            ChooseLevelScreenController.newPlayerTest.setCredits(0.0); // lose all credits
            updatePlayerInfo();
            ChooseLevelScreenController.newPlayerTest.playerShip.decreaseHealth(
                    10); // decrease health 10 points

            if (GameOver.gameOverOrNot()) {

                goToGameOverScreen(fleeB);

            }
            terminateAndReturn(lastVisit);
        }

    }

    /* Try to fight off the bandit. The success of defeating the bandit is dependent on the
    player’s fighter skill (higher fighter level, higher chance of winning). Successfully
    fighting off the bandit will allow the player to travel as intended to the desired
    destination without any new consequences. Additionally, success will grant the player
    some of the bandit's credits as a reward for winning the fight. Failing to fight off the
    bandit will cost the player all their credits and should damage the health of the player's
    ship. */
    @FXML
    private void playerFightBandit() throws IOException {
        Random r = new Random();
        int p = r.nextInt(11);

        if (((fight) > 7 && player.getChosenDifficulty().equalsIgnoreCase("Hard")
                && p < 4)) {
            ChooseLevelScreenController.newPlayerTest.setCredits(
                    player.getCredits() + 250); // get credits
            updatePlayerInfo();
            terminateAndReturn(lastVisit); // to previous region
            // more chances if pilot skill higher
        } else if (((fight > 4 && player.getChosenDifficulty().equalsIgnoreCase("Easy")
                && p < 5))
                || ((fight > 6 && player.getChosenDifficulty().equalsIgnoreCase("Medium"))
                && p < 4)) {
            ChooseLevelScreenController.newPlayerTest.setCredits(// get credits
                    ChooseLevelScreenController.newPlayerTest.getCredits() + 450);
            updatePlayerInfo();
            terminateAndReturn(lastVisit);
        } else {
            ChooseLevelScreenController.newPlayerTest.setCredits(0.0); // lose all credits
            updatePlayerInfo();
            ChooseLevelScreenController.newPlayerTest.playerShip.decreaseHealth(
                    10); // decrease health 10 points

            if (GameOver.gameOverOrNot()) {

                goToGameOverScreen(fightB);

            }
            updateShipInfo();
            terminateAndReturn(lastVisit);
        }

    }

    /**
     * Takes player to the game over Screen
     * @param actionButton - the action that we are implementing on the Bandit
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
        nextController.setGameOverText(false);
        nextController.setPlayerStatus(ChooseLevelScreenController.newPlayerTest);

        stage.getScene().setRoot(root);
        stage.setFullScreen(false);
        stage.setTitle("Game Over....");
        stage.show();

    }

}
