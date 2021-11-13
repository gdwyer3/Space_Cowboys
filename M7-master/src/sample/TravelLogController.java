package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class TravelLogController {

    @FXML
    private Button close;
    @FXML
    protected Pane regions;
    @FXML
    protected Pane logPane;
    @FXML
    protected Label regName;
    @FXML
    protected Label regTechLevel;
    @FXML
    protected Label regDescription;
    @FXML
    private Button startButton11;
    @FXML
    private Button submitButton1;

    private TableView<Item> market;
    private TableView<Item> cargo;

    @FXML
    protected Pane regionDetails;
    @FXML protected Button traderTesterButton;
    protected Button newVisit = new Button();
    protected Button regionInfo = new Button();
    @FXML
    private Button regionMarket;


    private Random randGen = new Random();
    protected double lastVisited = 172;
    protected int playerStart;
    private int buttonNum = 1;
    protected Point[] regionArray = Region.getRandRegion();
    protected HashMap<Point, Region> map = Region.regionHashMap();
    protected Set<Integer> prevVisit = new HashSet<>();

    protected Region startRegion; //= map.get(regionArray[playerStart]);
    private Circle region0 = new Circle();
    private Circle region1 = new Circle();
    private Circle region2 = new Circle();
    private Circle region3 = new Circle();
    private Circle region4 = new Circle();
    private Circle region5 = new Circle();
    private Circle region6 = new Circle();
    private Circle region7 = new Circle();
    private Circle region8 = new Circle();
    private Circle region9 = new Circle();

    private Text cood0 = new Text();
    private Text cood1 = new Text();
    private Text cood2 = new Text();
    private Text cood3 = new Text();
    private Text cood4 = new Text();
    private Text cood5 = new Text();
    private Text cood6 = new Text();
    private Text cood7 = new Text();
    private Text cood8 = new Text();
    private Text cood9 = new Text();

    private Text[] coordinates = {cood0, cood1, cood2, cood3,
                                     cood4, cood5, cood6, cood7, cood8, cood9};
    private Circle[] points = {region0, region1, region2, region3,
                                  region4, region5, region6, region7, region8, region9};

    //track the current regions marketplace array
    private Item[] marketplaceArray;
    protected double lastAdded = lastVisited;

    protected ArrayList<Button> regbuttons = new ArrayList<>();
    protected ArrayList<Button> infoButtons = new ArrayList<>();


    public void map(int currIndex) {
        regionDetails.setVisible(false);
        for (int i = 0; i < 10; i++) { // remove current map
            regions.getChildren().remove(points[i]);
            logPane.getChildren().remove(coordinates[i]);
        }
        DecimalFormat df = new DecimalFormat("##.##");
        playerStart = currIndex;

        for (int i = 0; i < 10; ++i) { // redraw new map
            double coordinateX = regionArray[i].getX();
            double coordinateY = regionArray[i].getY();
            points[i].setCenterX(coordinateX);
            points[i].setCenterY(coordinateY);
            points[i].setRadius(5);
            Double dist;
            if (calcNearestRegion() < 10) {
                dist = regionArray[i].distance(regionArray[calcNearestRegion()]);
            } else {
                dist = regionArray[i].distance(regionArray[playerStart]);
                // calc distance from current if nearby disabled
            }

            coordinates[i] = new Text("(" + coordinateX + ", "
                    + coordinateY + ") " + df.format(dist));
            coordinates[i].setLayoutX(coordinateX + 400);
            coordinates[i].setLayoutY(coordinateY + 120);

            if (prevVisit.contains(i)) {
                points[i].setFill(Color.CHARTREUSE);
                coordinates[i].setFill(Color.CHARTREUSE);
            }
            if (i == currIndex) {
                points[i].setFill(Color.RED);
                coordinates[i].setFill(Color.RED);
            }

            if (i == calcNearestRegion() && calcNearestRegion() < regionArray.length) {
                points[i].setFill(Color.BLUE);
                coordinates[i].setFill(Color.BLUE);
            }
            regions.getChildren().add(points[i]);
            logPane.getChildren().add(coordinates[i]);

        }
    }


    //add regions to pane to achieve dimensionality. set player in random starting region
    public void placeRegionsOnMap(int currIndex) {

        startButton11.setVisible(false);
        playerStart = currIndex;
        map(currIndex);
    }

    public int calcNearestRegion() { // returns index of nearest region
        if (prevVisit.size() < 10) {
            Point curr = regionArray[playerStart];
            int minIndex;
            // if player does not start at region at 0 index we will set min to 0 index intitially
            if (playerStart != 0 && !prevVisit.contains(0)) {
                minIndex = 0;
            } else {
                minIndex = 1;
            }
            // for chance that min index might be in the set already
            while ((minIndex < regionArray.length && (prevVisit.contains(minIndex))
                    || playerStart == minIndex)) {
                // change min when min already in set
                minIndex++;
            }
            if (minIndex < regionArray.length) {
                for (int i = 0; i < regionArray.length; i++) {
                    if (prevVisit.contains(i)) {
                        continue;
                    }
                    // we don't want to include current region in dist calculations bc it will be 0
                    if (i == playerStart) {
                        continue;
                    }
                    if (curr.distance(regionArray[i]) < curr.distance(regionArray[minIndex])) {
                        minIndex = i;
                    }
                }
            }

            return minIndex;
        } else {
            return 11;
        }

    }

    //when submit1 button pressed, this function executes
    //WHY DOES NOT TAKE ACTION EVENT AS PARAM?
    public void goToNearest() throws IOException {

        if (prevVisit.size() == 10) {
            // System.out.println("Set is full");
            submitButton1.setVisible(false);
            map(playerStart);
            lastAdded = lastVisited + 30;
            ChooseLevelScreenController.newPlayerTest.playerShip.decreaseFuelAfterTravel();
        } else {

            if (calcNearestRegion() < regionArray.length
                    && !prevVisit.contains(calcNearestRegion())) {
                Region near = map.get(regionArray[calcNearestRegion()]); // new nearest region
                // now player is starting at the nearest region
                playerStart = calcNearestRegion();
                int temp = playerStart; // keep track of index for mouse click method
                startRegion = near;
                Point showCurrent = startRegion.getRegCoordinates();
                // replace region info on bottom
                setRegionInfo(near);
                // create button to add to travel log
                Button newVisit = new Button();
                //create region information button for the region
                Button regionInfo = new Button("i");
                // button places 30 units below last visited region's button
                newVisit.relocate(47, lastVisited + 30);
                newVisit.setPrefWidth(150);
                newVisit.setText(near.getRegName());
                regionInfo.relocate(200, lastVisited + 30);
                lastAdded = lastVisited + 30; // for future use
                lastVisited = newVisit.getLayoutY();
                logPane.getChildren().add(newVisit);
                logPane.getChildren().add(regionInfo);

                newVisit.setOnMouseClicked(e -> {
                    try {
                        chanceOfEncounter(temp);
                    } catch (IOException ex) {
                        System.out.println("Problem with encounter");
                        ex.printStackTrace();
                    }
                    System.out.println(showCurrent);
                    regionDetails.setVisible(false);
                    ChooseLevelScreenController.newPlayerTest.playerShip.decreaseFuelAfterTravel();
                    map(temp);
                });

                regbuttons.add(newVisit); // add button to array liat for easy future access

                regionInfo.setOnMouseClicked(e -> {
                    setRegionInfo(near);
                    regionDetails.setVisible(true); // button displayed with this too
                });
                infoButtons.add(regionInfo); // add new info button to arraylist for easy access

                // redraw region map with new coordinate colors
                chanceOfEncounter(temp); // check for chance of encounter --------------NEW FOR M6
                ChooseLevelScreenController.newPlayerTest.playerShip.
                        decreaseFuelAfterTravel(); // fuel-- per travel
                map(playerStart);
                prevVisit.add(playerStart);
                // add button to travel log
            }
        }
    }

    /**
     * method for initiating three different types of encounters based
     * on probability and level of game
     * happens when trying to travel to other region
     *
     * hard --> 75% chance
     * medium --> 50% chance
     * easy --> 25% chance
     *
     * @param regionToGo player wants to go to
     * @throws IOException just in case
     */
    protected void chanceOfEncounter(int regionToGo) throws IOException {
        Random encounter = new Random();
        Random encounterType = new Random();
        String level = ChooseLevelScreenController.newPlayerTest.getChosenDifficulty();
        int random = encounter.nextInt(100) + 1; // produces number between 1 - 100
        // equal chance of being any encounter (bandit/trader/police)
        // but chance of encounter varies per level
        int type = encounterType.nextInt(3) + 1; // number bw 1-3, one for each encounter
        if (((level.equalsIgnoreCase("Hard") && random >= 25)
                || (level.equalsIgnoreCase("Medium") && random >= 50))
                || (level.equalsIgnoreCase("Easy") && random >= 75)) {
            switch (type) {
            case 1: // bandit
                initiateEncounter(regionToGo, "BanditEncounter.fxml");
                break;
            case 2:
                if (ChooseLevelScreenController.newPlayerTest.playerShip.getSize() > 0) {
                    initiateEncounter(regionToGo, "PoliceEncounter.fxml");
                }
                break;
            case 3:
                // not doing polymorphism with trader, since it is a special encounter
                initiateTraderEncounter(regionToGo);
                break;
            default:
                break;
            }
        }



    }

    private void initiateEncounter(int regionToGo, String fxmlName) throws IOException {
        Stage stage;
        stage = (Stage) regionMarket.getScene().getWindow();
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource(fxmlName));
        Parent root = (Parent) loader.load();
        // this is polymorphism since the static type of control is EncounterController,
        // but the dynamic type of control will be the subclass of EncounterController
        // specified in the given fxml file. Thus, if more encounters were to be added,
        // they can still use this method instead of having one method new encounter.
        EncounterController control = loader.getController();
        control.setValues(ChooseLevelScreenController.newPlayerTest, playerStart, regionToGo);
        control.stuffToGoBackToTravelLog(this.regbuttons, this.infoButtons,
                regions.getChildren().toArray(), this.prevVisit, this.lastAdded,
                this.startRegion, regionToGo);
        stage.getScene().setRoot(root);
        stage.setFullScreen(false);
        stage.show();
    }

    public void initiateTraderEncounter(int regionToGo) throws IOException {
        Stage stage = null;
        Parent myNewscene = null;
        stage = (Stage) traderTesterButton.getScene().getWindow();
        myNewscene = FXMLLoader.load(getClass().getResource("TraderScreen.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TraderScreen.fxml"));
        Parent root = (Parent) loader.load();
        TraderScreenController nextController = loader.getController();
        nextController.setValues(ChooseLevelScreenController.newPlayerTest,
                playerStart, regionToGo);
        nextController.stuffToGoBackToTravelLog(this.regbuttons, this.infoButtons,
                regions.getChildren().toArray(), this.prevVisit, this.lastAdded,
                this.startRegion, regionToGo);
        nextController.setupShop();
        nextController.setCargoInfo();
        stage.getScene().setRoot(root);
        stage.setFullScreen(false);
        stage.setTitle("Trading Goods");
        stage.show();
    }

    @FXML
    private void testMyBandit() throws IOException {
        initiateEncounter(playerStart, "BanditEncounter.fxml");
    }

    @FXML
    private void testMyPolice() throws IOException {
        initiateEncounter(playerStart, "PoliceEncounter.fxml");
    }

    //when player clicks region market button, this method executes
    public void goToMarket(ActionEvent e) throws IOException {
        Stage stage = null;
        Parent myNewscene = null;
        if (e.getSource() == regionMarket) {
            stage = (Stage) regionMarket.getScene().getWindow();
            myNewscene = FXMLLoader.load(getClass().getResource("MarketPlace.fxml"));
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("MarketPlace.fxml"));
            Parent root = (Parent) loader.load();
            MarketPlaceController nextController = loader.getController();

            //display the marketplace in the vbox
            //nextController.setMarketItems(marketplaceArray);
            nextController.setLogtravel(logPane.getChildren().toArray());
            // help store state of travel log to switch back later --------------------------------
            nextController.setRegbuttons(this.regbuttons); // get all travel log buttons
            nextController.setInfoButtons(this.infoButtons);  // get all info buttons
            nextController.setRegionInfo(regions.getChildren().toArray()); // put reg nodes in array
            nextController.setCurrentReg(playerStart); // tells current region of player
            nextController.prevVisit = this.prevVisit; // tells already visited regions for map
            // new equation for buttons to fix overlap:
            int calcLastAdded = infoButtons.size() * 30 + 142;
            nextController.lastAdded = calcLastAdded; // for adding buttons later
            //System.out.println("going into market with lastAdded as " + nextController.lastAdded);
            nextController.setStartRegion(this.startRegion); // for first button
            // -------------------------------------------------------------------------------------

            //init marketplace items based on current region tech level
            Point current = regionArray[playerStart];
            Region currentRegion = map.get(current);
            marketplaceArray = currentRegion.setMarketplaceArray(currentRegion.getTechLevel());
            //added items to marketplace
            nextController.setCargoItems();
            nextController.setMarketItems();
            //set region name in top left corner of marketplace
            nextController.setRegionName(currentRegion);
            //add ship info to marketplace screen
            nextController.setShipInfo(ChooseLevelScreenController.
                    newPlayerTest.playerShip); // get player ship
            stage.getScene().setRoot(root);
            stage.setFullScreen(false);
            stage.setTitle("Market Place");

        }
    }

    //closes out program when hitting close button
    public void handleCloseButtonAction(ActionEvent event) {

        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);

    }

    public void setCargoAndMarket(TableView<Item> currentMarket, TableView<Item> currentCargo) {

        market = currentMarket;
        cargo = currentCargo;

    }


    /*
    adds randomized player starting region buttons (name, i)
    to the travelLog pane. also sets the appropriate event
    handlers and adds region to previously visited set
     */

    public void setInitialRegionActivity() {
        int temp = randGen.nextInt(10);
        playerStart = temp;
        startRegion = map.get(regionArray[temp]);

        placeRegionsOnMap(playerStart);

        // button places 30 units below last visited region's button
        newVisit.relocate(47, lastVisited);
        newVisit.setPrefWidth(150);
        lastVisited = newVisit.getLayoutY();
        newVisit.setText(startRegion.getRegName());
        regbuttons.add(newVisit); // add to button list

        //necessary for map redraw when click on the first region
        newVisit.setOnMouseClicked(e -> {
            placeRegionsOnMap(temp);
        });

        // button places 30 units below last visited region's button
        regionInfo.relocate(200, lastVisited);
        regionInfo.setText("i");
        infoButtons.add(regionInfo); // add info button to list

        regionInfo.setOnMouseClicked(e -> {
            setRegionInfo(map.get(regionArray[temp]));
            regionDetails.setVisible(true);
            lastVisited = lastAdded;
        });

        logPane.getChildren().addAll(newVisit);
        logPane.getChildren().addAll(regionInfo);
        prevVisit.add(temp);
    }

    private void setRegionInfo(Region region) {
        regName.setText("" + region.getRegName());
        regDescription.setText("" + region.getRegDescription());
        regTechLevel.setText("" + region.getTechLevel());
    }


    public void testMyTrader() throws IOException {
        Stage stage = null;
        Parent myNewscene = null;
        stage = (Stage) traderTesterButton.getScene().getWindow();
        myNewscene = FXMLLoader.load(getClass().getResource("TraderScreen.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TraderScreen.fxml"));
        Parent root = (Parent) loader.load();
        TraderScreenController nextController = loader.getController();
        nextController.stuffToGoBackToTravelLog(this.regbuttons, this.infoButtons,
                regions.getChildren().toArray(), this.prevVisit, this.lastAdded,
                this.startRegion, playerStart);
        nextController.setupShop();
        nextController.setCargoInfo();
        stage.getScene().setRoot(root);
        stage.setFullScreen(false);
        stage.setTitle("Trading Goods");
        stage.show();

    }
}

