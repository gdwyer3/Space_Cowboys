package sample;

import javafx.collections.FXCollections;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class InventoryChangesController {


    @FXML private TableView<Item> beforeChangeInventory;
    @FXML private TableColumn<Item, String> beforeChangeProductName;
    @FXML private TableColumn<Item, Double> beforeChangeProductPrice;
    @FXML private TableColumn<Item, String> beforeChangeProductQuantity;
    @FXML private TableView<Item> afterChangeInventory;
    @FXML private TableColumn<Item, String> afterChangeProductName;
    @FXML private TableColumn<Item, Double> afterChangeProductPrice;
    @FXML private TableColumn<Item, Integer> afterChangeProductQuantity;

    @FXML private Button moveOnButton;

    private Player player = ChooseLevelScreenController.newPlayerTest;
    private int lastVisit;
    private int regiontoGo; // region player is trying to get to
    private Object[] logtravel;
    private ArrayList<Button> regbuttons;
    private ArrayList<Button> infoButtons;
    private Object[] regionInfo;
    private int currentReg;
    protected Set<Integer> prevVisit;
    protected double lastAdded;
    protected Region startRegion;

    /**
     * Displays all the items in the inventory before trading
     * @param beforeChangeItemList observble list param
     */
    public void setupBeforeChangeTable(ObservableList<Item> beforeChangeItemList) {

        beforeChangeProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        beforeChangeProductPrice.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        beforeChangeProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        beforeChangeInventory.getItems().addAll(beforeChangeItemList);

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

    /**
     * Displays all the items in the inventory after trading
     * @param afterChangeItemList array list param
     */
    public void setupAfterChangeTable(ArrayList<Item> afterChangeItemList) {

        afterChangeProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        afterChangeProductPrice.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        afterChangeProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ObservableList<Item> myNewList = FXCollections.observableArrayList();
        myNewList.addAll(ChooseLevelScreenController.newPlayerTest.playerShip.getItemInventory());
        afterChangeInventory.getItems().addAll(myNewList);

    }

    public void setValues(Player player, int lastVisit, int regiontoGo) {
        this.player = player;
        this.lastVisit = lastVisit;
        this.regiontoGo = regiontoGo;
    }

    public void travelToDestination(ActionEvent event) throws  IOException {

        Stage stage = null;
        stage = (Stage) moveOnButton.getScene().getWindow();
        player.playerShip.decreaseFuelAfterTravel(); // decrease fuel by one
        // bc we want anchorpane not vbox
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
        nextController.map(nextController.playerStart); // map with current state where it left off
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
