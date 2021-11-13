package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;


//author: Joshua Suber

//changes: (list changes:: person who made that change)


/**
 * Ship Market Controller
 *
 * Controls functionality of the Ship Market Screen
 * Sub-section of the Marketplace Controller
 *
 * Handles strictly ship related purchases
 *
 * Possible additions: purchase or selling of ships
 */


public class ShipMarketController {


    // Methods created for switching back and forth between travel log and market
    private Object[] logtravel;
    private ArrayList<Button> regbuttons;
    private ArrayList<Button> infoButtons;
    private Object[] regionInfo;
    private int currentReg;
    protected Set<Integer> prevVisit;
    protected double lastAdded;
    private Region startRegion;

    //added fxml for setting region name in top left corner
    @FXML private Text regionName;
    @FXML private Button back;
    @FXML private Button close;

    //ship info
    @FXML private Label shipName;
    @FXML private Label shipCargoCap;
    @FXML private Label shipInventory;
    @FXML private Label shipFuel;
    @FXML private Label shipHealth;

    @FXML private Text fuelCostText;
    @FXML private Text repairCostText;

    private int capacity;
    private int shipInventorySize;
    @FXML private Text moneyAmount;
    private int shipCurrentFuel;
    private int fuelPrice = 300;
    private int fuelGallon = 1;
    private Double playerCredits;
    private int playerEngineerSkill = (int)
            ChooseLevelScreenController.newPlayerTest.getEngineerPoints();
    private int repairSpecsPrice = 500;
    private int purchasedRepairSpecs = 5;
    private int shipCurrentHealth;

    // changed from public to private for checkstyle
    private Ship ship = ChooseLevelScreenController.newPlayerTest.playerShip;

    private int maxFuel = ship.getFuelCap();
    @FXML private Button refuel;
    @FXML private Button repair;


    //refuels -> puchasing fuel
    public void handleRefuelButtonAction(ActionEvent event) {

        refuel.setOnAction(e -> {
            if (ChooseLevelScreenController.newPlayerTest.getCredits() > 0
                    && ChooseLevelScreenController.newPlayerTest.getCredits()
                    >= fuelPrice) {
                //call refuel method in Ship class
                ship.refuel(playerCredits, shipCurrentFuel, fuelPrice, fuelGallon);
                // reset to updated player ship
                ship = ChooseLevelScreenController.newPlayerTest.playerShip;
                //set new player credits and fuel level
                setShipInfo(ship);
            }
        });
    }

    //repair -> repairing ship
    public void handleRepairButtonAction(ActionEvent event) {
        repair.setOnAction(e -> {
            if (ChooseLevelScreenController.newPlayerTest.getCredits() > 0
                    && ChooseLevelScreenController.newPlayerTest.getCredits() >= repairSpecsPrice) {
                //call refuel method in Ship class
                ship.repair(playerCredits, playerEngineerSkill, shipCurrentHealth,
                        repairSpecsPrice, purchasedRepairSpecs);

                //set new player credits and fuel level, reset to updated player ship
                ship = ChooseLevelScreenController.newPlayerTest.playerShip;
                setShipInfo(ship);
            }
        });
    }

    public void setShipInfo(Ship ship) {
        shipName.setText("Name: " + ship.getName());
        shipCargoCap.setText("Cargo Capacity: " + ship.getCargoCap());
        capacity = ship.getCargoCap();
        //System.out.println("\nItems in Inventory: " + ship.getInventorySize());
        shipInventory.setText("Items in Inventory: " + ship.getInventorySize());
        shipInventorySize = ship.getInventorySize();
        shipFuel.setText("Fuel: " + ship.getCurrentFuel());
        shipCurrentFuel = ship.getCurrentFuel();
        shipHealth.setText("Health: " + ship.getHealth());
        shipCurrentHealth = ship.getHealth();
        //currentInventoryList = ship.getItemInventoryList();
        //availableCargoSpace = ship.getCurrentCargoSpace();
        moneyAmount.setText("Your money: "
                + ChooseLevelScreenController.newPlayerTest.getCredits());
        playerCredits = ChooseLevelScreenController.newPlayerTest.getCredits();


    }

    public void setMarketInfo() {

        fuelCostText.setText(fuelCostText.getText() + fuelPrice);
        int repairPrice;
        if (playerEngineerSkill != 0) {
            // more engineer skill --> less repair cost
            repairPrice = repairSpecsPrice - repairSpecsPrice / playerEngineerSkill;
        } else {
            // to handle exceptions
            repairPrice = repairSpecsPrice;
        }
        repairCostText.setText(repairCostText.getText() + repairPrice);

    }
    protected void stuffToGoBackToTravelLog(ArrayList<Button> regbuttons,
                                            ArrayList<Button> infoButtons,
                                            Object[] regionInfo,
                                            Set<Integer> prevVisit,
                                            double lastAdded,
                                            Region startRegion) {
        this.regbuttons = regbuttons; // get all travel log buttons
        this.infoButtons = infoButtons; // get all info buttons
        this.regionInfo = regionInfo; // put reg nodes in array
        this.prevVisit = prevVisit; // tells already visited regions for map
        this.lastAdded = lastAdded; // for adding buttons later
        this.startRegion = startRegion;
    }

    // GO BACK AND FORTH BETWEEN MARKET PLACE AND TRAVEL LOG
    @FXML
    public void goBack() throws IOException {
        back.setOnMouseClicked((EventHandler<Event>) e -> {
            // creating new travel log
            Stage stage = (Stage) back.getScene().getWindow();
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("TravelLog.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            TravelLogController nextController = loader.getController();
            // add visit buttons and region info buttons back along with their event handlers
            for (int i = 0; i < regbuttons.size(); i++) {
                nextController.logPane.getChildren().add(regbuttons.get(i));
                nextController.logPane.getChildren().add(infoButtons.get(i));
                Button curr = regbuttons.get(i);
                Button currI = infoButtons.get(i);
                int temp = -1; // ideally will always change to region
                // and will never cause exception
                for (int k = 0; k < Region.getAllRegion().size(); k++) {
                    if (Region.getAllRegion().get(k).getRegName()
                            .equalsIgnoreCase(curr.getText())) {
                        temp = k;
                        break;
                    }
                }
                int finalTemp = temp; // bc lambda expression needs it like this
                curr.setOnMouseClicked(ev -> {
                    //System.out.println(showCurrent);
                    try {
                        nextController.chanceOfEncounter(finalTemp);
                    } catch (IOException ex) {
                        System.out.println("Problem with encounter");
                        ex.printStackTrace();
                    }
                    nextController.regionDetails.setVisible(false);
                    nextController.map(finalTemp);
                });

                currI.setOnMouseClicked(ev -> {
                    Region currR = Region.getAllRegion().get(finalTemp);
                    nextController.regName.setText("" + currR.getRegName());
                    nextController.regDescription.setText("" + currR.getRegDescription());
                    nextController.regTechLevel.setText("" + currR.getTechLevel());
                    nextController.regionDetails.setVisible(true); // button displayed with this too
                });
            }

            //ChooseLevelScreenController.newPlayerTest.
            //playerShip.setItemInventory(currentInventoryList);
            // get region nodes back
            for (int j = 0; j < regionInfo.length; j++) {
                if (regionInfo[j] instanceof Node) {
                    Node curr = (Node) regionInfo[j]; // cast to node and add to regions
                    nextController.regions.getChildren().add(curr);
                }
            }

            // trying to fix the reset of travel log by
            // changing properties to the way they used to be b/f market
            nextController.regionDetails.setVisible(false);
            // no need to show region details for back  at moment
            nextController.prevVisit = this.prevVisit;
            // tells what regions have already been visited
            nextController.playerStart = currentReg; // tells current region
            nextController.map(currentReg); // map with current state where it left off
            System.out.println(lastAdded);
            if (lastAdded != 0) {
                nextController.lastVisited = this.lastAdded;
                nextController.lastAdded = lastAdded;
                // so buttons don't add on top of eachother
            } else  {
                nextController.lastVisited = 172;
                nextController.lastAdded = lastAdded;
            }
            nextController.regbuttons = this.regbuttons; // so buttons are always saved
            nextController.infoButtons = this.infoButtons; // so info buttons always saved
            nextController.startRegion = this.startRegion;
            stage.getScene().setRoot(root);
            stage.setFullScreen(false);
            //stage.setTitle("Enter Player Name");
            stage.show();
        });
    }




    //closes out program when hitting close button
    public void handleCloseButtonAction(ActionEvent event) {

        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }
}
