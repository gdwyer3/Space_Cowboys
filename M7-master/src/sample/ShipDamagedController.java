package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ShipDamagedController extends EncounterController {


    @FXML private Text shipHealthBefore;
    @FXML private Text shipHealthAfter;
    @FXML private Button goToDestinationButton;
    private Player player = ChooseLevelScreenController.newPlayerTest;
    private int lastVisit;

    // DON'T DELETE, I ADDED THIS METHOD FOR IMPLEMENTING POLYMORPHISM FOR GRASP EXAMPLES -- Nooreen
    public void setValues(Player player, int lastVisit, int regiontoGo) {
        // constructor to initialize variables
        this.player = player;
        this.lastVisit = lastVisit;
        this.regiontoGo = regiontoGo;
    }

    public void setShipHealthChange(Integer shipHealthBefore1) {

        shipHealthBefore.setText(shipHealthBefore1 + "");
        shipHealthAfter.setText(
                ChooseLevelScreenController.newPlayerTest.playerShip.getHealth() + "");

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


    public void travelToDestination(ActionEvent event) throws IOException {

        Stage stage = null;
        Parent myNewscene = null;
        stage = (Stage) goToDestinationButton.getScene().getWindow();
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
