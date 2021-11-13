package sample;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;


public class MarketPlaceController {


    // Methods created for switching back and forth between travel log and market
    private Object[] logtravel;
    private ArrayList<Button> regbuttons;
    private ArrayList<Button> infoButtons;
    private Object[] regionInfo;
    private int currentReg;
    protected Set<Integer> prevVisit;
    protected double lastAdded;
    private Region startRegion;
    // ---------------------------------------------------------------------------

    //added fxml for setting region name in top left corner
    @FXML
    private Text regionName;
    @FXML
    private Button back;
    @FXML
    private Button close;

    //tracking the tableview and columns for adding items. specific types are set
    //tableview for purchasing items
    @FXML private TableView<Item> itemList;
    @FXML private TableColumn<Item, Double> buyPrice;
    @FXML private TableColumn<Item, String> itemName;
    @FXML private TableColumn<Item, Integer> itemQuantity;
    @FXML private TableColumn<Item, String> buyCount;

    //tableview for selling items
    @FXML private TableView<Item> itemInventory;
    @FXML private TableColumn<Item, Double> sellPrice;
    @FXML private TableColumn<Item, String> sellItem;
    @FXML private TableColumn<Item, Integer> sellQuantity;
    @FXML private TableColumn<Item, String> sellCount;


    @FXML
    private Label shipName;
    @FXML
    private Label shipCargoCap;
    @FXML
    private Label shipInventory;
    @FXML
    private Label shipFuel;
    @FXML
    private Label shipHealth;

    private int capacity;
    private int maxCapacity = 5;

    private int shipInventorySize;
    private int availableCargoSpace;

    @FXML private Button purchaseButton;
    @FXML private Button tradeButton;

    @FXML private Text purchaseTotal;
    @FXML private Text sellItemCount;
    @FXML private Text sellValue;
    @FXML private Text moneyAmountText;
    private int total = 0;
    private int buyQuantity = 0;
    private int selltotal = 0;

    private ArrayList<Item> currentInventoryList;

    private Item items;

    @FXML private Text priceTotal;
    private double price = 0;
    private double sellprice = 0;

    @FXML private Text playerCredits;

    private int credits;

    @FXML private Button shipMarketButton;

    private Ship playerShip;


    //commented out for testing
    // private Player player = ChooseLevelScreenController.newPlayerTest;

    // GO BACK AND FORTH BETWEEN MARKET PLACE AND TRAVEL LOG
    /*bugs fixed
     */
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
            //System.out.println("coming out with lastAdded as " + nextController.lastAdded);
            nextController.regbuttons = this.regbuttons; // so buttons are always saved
            nextController.infoButtons = this.infoButtons; // so info buttons always saved
            nextController.startRegion = this.startRegion;
            stage.getScene().setRoot(root);
            stage.setFullScreen(false);
            //stage.setTitle("Enter Player Name");
            stage.show();
        });
    }


    public void setMarketItems() {

        ObservableList<Item> marketItems = FXCollections.observableArrayList();
        for (int i = 0; i < Item.getItemArray().length; i++) {

            if (Item.getItemArray()[i].getQuantity() != 0) {

                marketItems.add(Item.getItemArray()[i]);

            }

        }

        //add the winning item if the current region is supposed to have it
        Region reg = Region.regionHashMap().get((Region.getRandRegion())[currentReg]);
        if (reg.getWinningItem()) {
            marketItems.add(new Item("Golden Ticket", 1000, 800, 1));
        }

        buyPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("buyPrice"));
        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
        //tracks how much a player purchases
        buyCount.setCellValueFactory(new PropertyValueFactory<Item, String>("buyCount"));
        itemList.getItems().addAll(marketItems);

    }

    public void updateMarket(ObservableList<Item> updatedMarket) {

        itemList.getItems().clear();
        buyPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("buyPrice"));
        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
        //tracks how much a player purchases
        buyCount.setCellValueFactory(new PropertyValueFactory<Item, String>("buyCount"));
        itemList.getItems().addAll(updatedMarket);


    }

    public void setCargoItems() {

        sellPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("sellPrice"));
        sellItem.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        sellQuantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
        sellCount.setCellValueFactory(new PropertyValueFactory<Item, String>("sellCount"));
        itemInventory.getItems().addAll(
                ChooseLevelScreenController.newPlayerTest.playerShip.getItemInventoryList());

    }

    private void updateCargo(ObservableList<Item> updatedCargo) {

        itemInventory.getItems().clear();
        sellPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("sellPrice"));
        sellItem.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        sellQuantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
        sellCount.setCellValueFactory(new PropertyValueFactory<Item, String>("sellCount"));
        itemInventory.getItems().addAll(updatedCargo);

    }


    //set the region name
    public void setRegionName(Region current) {
        regionName.setText(current.getRegName());
    }

    //closes out program when hitting close button
    public void handleCloseButtonAction(ActionEvent event) {

        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }


    public void setShipInfo(Ship ship) {
        shipName.setText("Name: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getName());
        shipCargoCap.setText("Cargo Capacity: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getCargoCap());
        capacity = ship.getCargoCap();
        System.out.println("\nItems in Inventory: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getInventorySize());
        shipInventory.setText("Items in Inventory: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getInventorySize());
        shipInventorySize =
                ChooseLevelScreenController.newPlayerTest.playerShip.getInventorySize();
        shipFuel.setText("Fuel: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getCurrentFuel());
        shipHealth.setText("Health: "
                + ChooseLevelScreenController.newPlayerTest.playerShip.getHealth());
        currentInventoryList =
                ChooseLevelScreenController.newPlayerTest.playerShip.getItemInventoryList();
        availableCargoSpace =
                ChooseLevelScreenController.newPlayerTest.playerShip.getCurrentCargoSpace();
        moneyAmountText.setText(""
                + ChooseLevelScreenController.newPlayerTest.getCredits());

    }

    public void purchaseItems() {

        double playerMoneyAmount = ChooseLevelScreenController.newPlayerTest.getCredits();
        ObservableList<Item> currentMarket = FXCollections.observableArrayList();
        currentMarket.addAll(itemList.getItems());

        for (int i = 0; i < currentMarket.size(); i++) {

            Item newItem = currentMarket.get(i);

            if (!newItem.getBuyCount().getText().equals("")) {
                int newItemQuantity = newItem.getItemQuantity();
                int newItemBuyCount = Integer.parseInt(newItem.getBuyCount().getText());
                double totalCost = newItemBuyCount * newItem.getBuyPrice();

                if (availableCargoSpace - newItemBuyCount != 0
                        && newItemBuyCount <= newItemQuantity && playerMoneyAmount > totalCost) {

                    //successfully purchasing the golden ticket means you win the game
                    if (newItem.getName().equals("Golden Ticket")) {
                        ChooseLevelScreenController.newPlayerTest.setFoundWinItem(true);
                        try {
                            goToGameOverScreen();
                        } catch (Exception e) {
                            System.out.println("Error with winning item");
                        }
                    }

                    System.out.println(newItem.getName() + ": " + newItemBuyCount);
                    ChooseLevelScreenController.newPlayerTest.playerShip.addToInventory(

                            new Item(newItem.getName(), 0.0, newItem.getSellPrice() / (4.0 / 3.0),
                                    newItemBuyCount)

                    );
                    currentMarket.get(i).setItemQuantity(newItemQuantity - newItemBuyCount);
                    playerMoneyAmount -= totalCost;
                    moneyAmountText.setText("" + playerMoneyAmount);
                    availableCargoSpace -= newItemBuyCount;

                    if (currentMarket.get(i).getQuantity() == 0) {

                        currentMarket.remove(i);

                    }
                }
            }
        }

        shipInventory.setText("Items in Inventory: "
                + (ChooseLevelScreenController.newPlayerTest.playerShip.getCargoCap()
                - availableCargoSpace));
        ChooseLevelScreenController.newPlayerTest.playerShip.setInventorySize(
                (ChooseLevelScreenController.newPlayerTest.playerShip.getCargoCap()
                        - availableCargoSpace));
        ChooseLevelScreenController.newPlayerTest.setCredits(playerMoneyAmount);
        updateMarket(currentMarket);
        setCargoItems();

    }

    protected void goToGameOverScreen() throws IOException {
        Stage stage = null;
        Parent myNewscene = null;

        stage = (Stage) purchaseButton.getScene().getWindow();
        myNewscene = FXMLLoader.load(getClass().getResource("GameOverScreen.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverScreen.fxml"));
        Parent root = (Parent) loader.load();

        GameOverScreenController nextController = loader.getController();
        nextController.setPlayerStatus(ChooseLevelScreenController.newPlayerTest);

        stage.getScene().setRoot(root);
        stage.setFullScreen(false);
        stage.setTitle("YOU WON!");
        stage.show();
    }

    public void tradeItems() {

        double playerMoneyAmount = ChooseLevelScreenController.newPlayerTest.getCredits();
        ObservableList<Item> currentCargo = FXCollections.observableArrayList();
        currentCargo.addAll(itemInventory.getItems());

        for (int i = 0; i < currentCargo.size(); i++) {

            Item newItem = currentCargo.get(i);

            if (!newItem.getSellCount().getText().equals("")) {

                int sellItemQuantity = newItem.getItemQuantity();
                int itemSellCount = Integer.parseInt(newItem.getSellCount().getText());
                double totalEarned = itemSellCount * newItem.getSellPrice();

                if (itemSellCount <= sellItemQuantity) {

                    System.out.println(newItem.getName() + ": " + itemSellCount);
                    Item itemSold =  new Item(newItem.getName(), newItem.getSellPrice() * 2, 0.0,
                            itemSellCount);

                    playerMoneyAmount += totalEarned;
                    availableCargoSpace += itemSellCount;
                    moneyAmountText.setText("" + playerMoneyAmount);

                    if (currentCargo.get(i).getQuantity() == 0) {

                        currentCargo.remove(i);

                    }

                    itemList.getItems().add(itemSold);
                    ChooseLevelScreenController
                            .newPlayerTest.playerShip.removeItemFromInventory2(itemSold);

                }
            }
        }



        shipInventory.setText("Items in Inventory: "
                + (ChooseLevelScreenController.newPlayerTest.playerShip.getCargoCap()
                - availableCargoSpace));
        ChooseLevelScreenController.newPlayerTest.playerShip.setInventorySize(
                (ChooseLevelScreenController.newPlayerTest.playerShip.getCargoCap()
                        - availableCargoSpace));
        ChooseLevelScreenController.newPlayerTest.setCredits(playerMoneyAmount);
        updateCargo(currentCargo);

    }


    //Milestone 7 Additions

    /**
     * go to Ship Market method
     *
     * @param event - event
     * @throws IOException - exception
     */

    public void goToShipMarket(ActionEvent event) throws IOException {

        Stage stage = null;

        if (event.getSource() == shipMarketButton) {
            stage = (Stage) shipMarketButton.getScene().getWindow();

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("Ship Market.fxml"));
            Parent root = (Parent) loader.load();

            ShipMarketController nextController = loader.getController();
            nextController = loader.getController();

            nextController.setShipInfo(ChooseLevelScreenController.
                    newPlayerTest.playerShip);
            nextController.setMarketInfo();
            nextController.stuffToGoBackToTravelLog(
                    regbuttons,
                    infoButtons,
                    regionInfo,
                    prevVisit,
                    lastAdded,
                    startRegion);
            stage.getScene().setRoot(root);
            stage.setFullScreen(false);
            stage.setTitle("Ship Market");
            stage.show();
        }
    }









    //getter methods

    public Object[] getLogtravel() {

        return logtravel;
    }

    public void setLogtravel(Object[] logtravel) {
        this.logtravel = logtravel;
    }

    public ArrayList<Button> getRegbuttons() {
        return regbuttons;
    }

    public void setRegbuttons(ArrayList<Button> regbuttons) {
        this.regbuttons = regbuttons;
    }

    public ArrayList<Button> getInfoButtons() {
        return infoButtons;
    }

    public void setInfoButtons(ArrayList<Button> infoButtons) {
        this.infoButtons = infoButtons;
    }

    public Object[] getRegionInfo() {
        return regionInfo;
    }

    public void setRegionInfo(Object[] regionInfo) {
        this.regionInfo = regionInfo;
    }

    public int getCurrentReg() {
        return currentReg;
    }

    public void setCurrentReg(int currentReg) {
        this.currentReg = currentReg;
    }

    public Region getStartRegion() {
        return startRegion;
    }

    public void setStartRegion(Region startRegion) {
        this.startRegion = startRegion;
    }
}