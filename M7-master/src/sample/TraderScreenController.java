package sample;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class TraderScreenController extends EncounterController {

    @FXML private Button buyButton;
    @FXML private Button robButton;
    @FXML private Button ignoreButton;
    @FXML private Button negotiateButton;

    @FXML private TableView<Item> shopTable;
    @FXML private TableColumn<Item, String> productName;
    @FXML private TableColumn<Item, Double> productPrice;
    @FXML private TableColumn<Item, Integer> productQuantity;
    @FXML private TableColumn<Item, String> productBuyCount;

    @FXML private Text moneyAmount;

    private Trader currentTrader = new ProductTrader();
    private int lastVisit;

    private ObservableList<Item> shopItemList;
    private ArrayList<Item> currentShipInventory;

    private int availableCargoSpace;
    private boolean alreadyNegotiate = false;
    private int shipHealthBefore;
    private int shipCargoCap;

    // DON'T DELETE, I ADDED THIS METHOD FOR IMPLEMENTING POLYMORPHISM FOR GRASP EXAMPLES -- Nooreen
    public void setValues(Player player, int lastVisit, int regiontoGo) {
        // constructor to initialize variables
        this.player = player;
        this.lastVisit = lastVisit;
        this.regiontoGo = regiontoGo;
    }

    public void buy() throws IOException {
        Item selectedItem = shopTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int selectedItemBuyCount = Integer.parseInt(selectedItem.getBuyCount().getText());
            int selectedItemQuantity = selectedItem.getQuantity();
            double playerMoneyAmount = ChooseLevelScreenController.newPlayerTest.getCredits();
            double total = selectedItemBuyCount * selectedItem.getBuyPrice();
            selectedItem.setItemQuantity(selectedItemBuyCount);
            selectedItem.setSellPrice(selectedItem.getBuyPrice() / (4.0 / 3.0));


            if (availableCargoSpace >= selectedItemBuyCount
                    && selectedItemBuyCount <= selectedItemQuantity && playerMoneyAmount >= total) {

                availableCargoSpace -= selectedItemBuyCount;
                ChooseLevelScreenController.newPlayerTest.setCredits(playerMoneyAmount - total);
                //currentShipInventory.add(selectedItem);

                System.out.println(selectedItemQuantity);
                System.out.println(selectedItemBuyCount);
                System.out.println("Trade Successful");
                ChooseLevelScreenController.newPlayerTest.playerShip.addToInventory(selectedItem);
                ChooseLevelScreenController.newPlayerTest.playerShip.setInventorySize(shipCargoCap
                        - availableCargoSpace);
                goToInventoryChangeScreen();

            } else {

                System.out.println(selectedItemQuantity);
                System.out.println(selectedItemBuyCount);
                System.out.println("Trade UnSuccessful");

            }
        }
    }


    /**
     * Negotiates with the trader regarding the price of his/her items in store
     */
    public void negotiate() {

        //The player can only negotiate with the trader iff
        // the player has NOT negotiate with the trader yet
        if (!alreadyNegotiate) {

            //Negotiate will success if the player's merchant points is above 5
            if (ChooseLevelScreenController.newPlayerTest.getMerchantPoints() > 5) {
                //trader is happy
                currentTrader.decreasePrice();
                setupShop();
            } else { //trader is upset
                currentTrader.raisePrice();
                setupShop();
            }
            alreadyNegotiate = true;
        }
    }

    public void rob() throws IOException {

        if (ChooseLevelScreenController.newPlayerTest.getFighterPoints() > 7) {

            int randomNum1 = (int) (Math.random()
                    * ((ProductTrader) (currentTrader)).getAllShopItems().size());
            int randomQuantity1 = ((int) (Math.random() * 3)) + 1;
            Item randomItem1 = shopItemList.get(randomNum1);
            randomItem1.setItemQuantity(randomQuantity1);

            if (availableCargoSpace - randomQuantity1 >= 0) {

                //currentShipInventory.add(randomItem1);
                ChooseLevelScreenController.newPlayerTest.playerShip.addToInventory(randomItem1);
                System.out.println("Added " + randomItem1.getName()
                        + " Quantity: " + randomItem1.getQuantity());
                availableCargoSpace -= randomQuantity1;

            }

            int randomNum2 = (int) (Math.random()
                    * ((ProductTrader) (currentTrader)).getAllShopItems().size());
            int randomQuantity2 = ((int) (Math.random() * 3)) + 1;
            Item randomItem2 = shopItemList.get(randomNum2);
            randomItem1.setItemQuantity(randomQuantity2);

            if (availableCargoSpace - randomQuantity2 >= 0) {

                //currentShipInventory.add(randomItem2);
                ChooseLevelScreenController.newPlayerTest.playerShip.addToInventory(randomItem2);
                System.out.println("Added " + randomItem2.getName()
                        + " Quantity: " + randomItem2.getQuantity());
                availableCargoSpace -= randomQuantity2;

            }

            int randomNum3 = (int) (Math.random()
                    * ((ProductTrader) (currentTrader)).getAllShopItems().size());
            int randomQuantity3 = ((int) (Math.random() * 3)) + 1;
            Item randomItem3 = shopItemList.get(randomNum3);
            randomItem3.setItemQuantity(randomQuantity3);

            if (availableCargoSpace - randomQuantity3 >= 0) {


                //currentShipInventory.add(randomItem3);
                ChooseLevelScreenController.newPlayerTest.playerShip.addToInventory(randomItem3);

                System.out.println("Added " + randomItem3.getName()
                        + " Quantity: " + randomItem3.getQuantity());
                availableCargoSpace -= randomQuantity3;

            }

            goToInventoryChangeScreen();

        } else {

            //ship health damaged
            ChooseLevelScreenController.newPlayerTest.playerShip.decreaseHealth(10);


            if (GameOver.gameOverOrNot()) {

                goToGameOverScreen();


            } else {

                goToShipHealthDamagedScreen();

            }

        }
    }

    /**
     * Takes player to the Game Over Screen
     * @throws IOException - exception
     */
    protected void goToGameOverScreen() throws IOException {

        Stage stage = null;
        Parent myNewscene = null;

        stage = (Stage) robButton.getScene().getWindow();
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


    protected void stuffToGoBackToTravelLog(ArrayList<Button> regbuttons,
                                            ArrayList<Button> infoButtons,
                                            Object[] regionInfo,
                                            Set<Integer> prevVisit,
                                            double lastAdded,
                                            Region startRegion,
                                            int regiontoGo) {
        this.regbuttons = regbuttons; // get all travel log buttons
        this.infoButtons = infoButtons; // get all info buttons
        this.regionInfo = regionInfo; // put reg nodes in array
        this.prevVisit = prevVisit; // tells already visited regions for map
        this.lastAdded = lastAdded; // for adding buttons later
        this.startRegion = startRegion;
        this.regiontoGo = regiontoGo;
    }

    public void ignore(ActionEvent event) throws IOException {

        if (event.getSource() == ignoreButton) {
            //stage = (Stage) ignoreButton.getScene().getWindow();
            //myNewscene = FXMLLoader.load(getClass().getResource("TravelLog.fxml"));
            player.playerShip.decreaseFuelAfterTravel(); // decrease fuel by one
            // bc we want anchorpane not vbox
            Stage stage = (Stage) ignoreButton.getScene().getWindow().getScene().getWindow();
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("TravelLog.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            TravelLogController nextController = loader.getController();
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
            for (int j = 0; j < regionInfo.length; j++) {
                if (regionInfo[j] instanceof Node) {
                    Node curr = (Node) regionInfo[j]; // cast to node and add to regions
                    nextController.regions.getChildren().add(curr);
                }
            }


            // changing properties to the way they used to be b/f market
            nextController.regionDetails.setVisible(false);
            // no need to show region details for back  at moment
            nextController.prevVisit = this.prevVisit;
            // tells what regions have already been visited
            nextController.playerStart = regiontoGo; // tells current region
            // map with current state where it left off
            nextController.map(nextController.playerStart);
            System.out.println(lastAdded);
            if (lastAdded != 0) {
                nextController.lastVisited = lastAdded;
                // so buttons don't add on top of eachother
            } else {
                nextController.lastVisited = 172;
            }
            nextController.regbuttons = this.regbuttons; // so buttons are always saved
            nextController.infoButtons = this.infoButtons; // so info buttons always saved
            nextController.startRegion = this.startRegion;
            stage.getScene().setRoot(root);
            stage.setFullScreen(false);
            //stage.setTitle("Enter Player Name");
            stage.show();

        }

    }

    /**
     * Gets all the items
     */
    public void setCargoInfo() {

        availableCargoSpace = 5;
        Item[] items = ChooseLevelScreenController.newPlayerTest.playerShip.getItemInventory();
        shipCargoCap = ChooseLevelScreenController.newPlayerTest.playerShip.getCargoCap();
        moneyAmount.setText(
                "Your Money Amount: " + ChooseLevelScreenController.newPlayerTest.getCredits()
        );

        for (int i = 0; i < items.length; i++) {

            if (items[i] != null) {

                availableCargoSpace -= items[i].getQuantity();

            }

        }
        shipHealthBefore = ChooseLevelScreenController.newPlayerTest.playerShip.getHealth();
        currentShipInventory = new ArrayList<>();
        System.out.println("\nAvailable cargo " + availableCargoSpace);
        System.out.println("Cargo Cap "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getCargoCap());
        System.out.println(
                ChooseLevelScreenController.newPlayerTest.playerShip.numItemInInventory());
        System.out.println("Money Amount: "
                + ChooseLevelScreenController.newPlayerTest.getCredits());

        for (int i = 0; i < items.length; i++) {

            if (items[i] != null) {

                currentShipInventory.add(items[i]);
                System.out.println(items[i].getName());

            }
        }
    }

    /**
     * Sets up or update the shop table view
     * Sets up or update the array list that contains all the items in the trader's shop
     */
    public void setupShop() {

        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productBuyCount.setCellValueFactory(new PropertyValueFactory<>("buyCount"));
        shopTable.getItems().addAll(((ProductTrader) (currentTrader)).getAllShopItems());
        shopItemList = ((ProductTrader) (currentTrader)).getAllShopItems();

    }

    /* public void updateShipStatus() {


        ChooseLevelScreenController.newPlayerTest.playerShip.setItemInventory(currentShipInventory);

    }*/

    public void goToShipHealthDamagedScreen() throws IOException {

        Stage stage = null;
        Parent myNewscene = null;

        stage = (Stage) robButton.getScene().getWindow();
        myNewscene = FXMLLoader.load(getClass().getResource("ShipHealthDamaged.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShipHealthDamaged.fxml"));
        Parent root = (Parent) loader.load();
        ShipDamagedController nextController = loader.getController();
        nextController.stuffToGoBackToTravelLog(this.regbuttons, this.infoButtons, this.regionInfo,
                this.prevVisit, this.lastAdded, this.startRegion, this.regiontoGo);
        nextController.setShipHealthChange(shipHealthBefore);

        stage.getScene().setRoot(root);
        stage.setFullScreen(false);
        stage.setTitle("Ship Health Damaged");
        stage.show();

    }

    public void goToInventoryChangeScreen() throws IOException {

        Stage stage = null;
        Parent myNewscene = null;

        stage = (Stage) buyButton.getScene().getWindow();
        myNewscene = FXMLLoader.load(getClass().getResource("InventoryChanges.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("InventoryChanges.fxml"));
        Parent root = (Parent) loader.load();
        InventoryChangesController nextController = loader.getController();
        nextController.stuffToGoBackToTravelLog(this.regbuttons, this.infoButtons, this.regionInfo,
                this.prevVisit, this.lastAdded, this.startRegion, this.regiontoGo);
        nextController.setupAfterChangeTable(currentShipInventory);

        stage.getScene().setRoot(root);
        stage.setFullScreen(false);
        stage.setTitle("Inventory Changed");
        stage.show();

    }

}
