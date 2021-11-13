package sample;

import java.util.ArrayList;

public class Ship {
    // currently, name is only public attribute of ship, rest will have getters
    private String name;

    private int cargoCap;
    // currently array of objects since item class not created yet
    // can't have more items than ship can hold
    private Item[] itemInventory = new Item[cargoCap];
    private ArrayList<Item> itemInventoryList;
    private int fuelCap;
    private int health;
    //size tracks the current inventory size
    //available cargo capacity = cargo cap - size
    private int size;
    private int currentFuel;

    private int inventorySize;

    public Ship(String name, int cargoCap, int fuelCap, int health, int size) {
        this.name = name;
        this.cargoCap = cargoCap;
        this.fuelCap = fuelCap;
        currentFuel = fuelCap; // start with full tank
        this.health = health;
        this.size = size;
        itemInventory = new Item[cargoCap];
        itemInventoryList = new ArrayList<>();
    }


    //protected Ship gnat = new Ship("Gnat", 5, 12, 50, 3);





    //may be redundant
    public int getSize() {
        return size;
    }

    /* returns number of items in the inventory (size) */
    public int numItemInInventory() {

        return size;
    }

    //added setter for size when player buys/sells item and goes to another marketplace
    public void setSize(int size) {
        this.size = size;
    }


    public ArrayList<Item> getItemInventoryList() {

        return itemInventoryList;

    }

    public void decreaseHealth(int amountToDecrease) {
        health = Math.max(health - amountToDecrease, 0); // the lowest health can be is 0
    }

    //this may be incorrect
    public void decreaseFuelAfterTravel() { // decrease fuel by one for each travel
        currentFuel--;
        if (currentFuel < 0) {
            currentFuel = 0;
        }
    }

    /*
    adds new item to end of array
     */
    public void addToInventory(Item item) {

        boolean added = false;

        for (int i = 0; i < itemInventoryList.size() && !added; i++) {

            int currentItemQuantity = itemInventoryList.get(i).getItemQuantity();

            if (itemInventoryList.get(i).getName().equals(item.getName())) {

                itemInventoryList.get(i).setItemQuantity(currentItemQuantity + item.getQuantity());
                added = true;

            }
        }

        if (!added) {

            itemInventoryList.add(item);

        }

        size += item.getItemQuantity();
        System.out.println("Item added: " + item.getName());
        System.out.println("Current Size: " + size);


        for (int i = 0; i < itemInventoryList.size(); i++) {

            itemInventory[i] = itemInventoryList.get(i);

        }

    }

    //Item[] itemArray;
    //changed to item inventory list to prevent null pointer exception
    public void removeItemFromInventory2(Item item) {
        if (item != null) {
            for (int i = 0; i < itemInventoryList.size(); i++) {

                if (itemInventoryList.get(i).getName().equals(item.getName())) {

                    itemInventoryList.get(i).setItemQuantity(
                            itemInventoryList.get(i).getItemQuantity() - item.getQuantity()
                    );

                    size -= item.getItemQuantity();

                    if (itemInventoryList.get(i).getQuantity() == 0) {

                        itemInventoryList.remove(i);

                    }

                    break;

                }
            }
        }

        itemInventory = new Item[cargoCap];

        for (int i = 0; i < itemInventoryList.size(); i++) {

            itemInventory[i] = itemInventoryList.get(i);

        }

    }

    public void clearInventory() {
        itemInventory = new Item[cargoCap];
        size = 0;
    }

    public void setInventorySize(int inventorySize) {
        this.inventorySize = inventorySize;

    }

    //Joshua: Structured getters and setters  getters and

    //getter methods
    public int getInventorySize() {
        return inventorySize;
    }

    public int getFuelCap() {

        return fuelCap;
    }

    public int getHealth() {

        return health;
    }

    public String getName() {

        return name;
    }

    public int getCargoCap() {
        return cargoCap;
    }

    /*
    returns array of items in inventory, currently an array of objects
     */
    public Item[] getItemInventory() {

        return itemInventory;
    }

    //returns the available cargo space left. people have used this incorrectly?
    public int getCurrentCargoSpace() {

        System.out.println("My size " + size);
        return cargoCap - size;
    }

    public int getCurrentFuel() {
        return currentFuel;

    }


    //setter methods
    public void setFuelCap(int fuelCap) {
        this.fuelCap = fuelCap;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setCurrentFuel(int currentFuel) {
        this.currentFuel = currentFuel;
    }








    //MILESTONE 7 ADDITIONS
    //author: Joshua



    /**
     * refuel method
     *
     * When called:
     * Player credits be decrease after purchase of fuel from the ship marketplace
     * Ship's fuel will increase depending on amount of fuel purchased by player
     *
     * Update ship fuel, update player credits
     *
     * @param playerCredits - current player credits
     * @param shipCurrentFuel - current ship fuel
     * @param fuelPrice - ship price
     * @param fuelGallons - fuel gallon
     *
     */

    public void refuel(Double playerCredits, int shipCurrentFuel, int fuelPrice, int fuelGallons) {

        //decrease the player credits based on amount of fuel purchased
        playerCredits -= fuelPrice * fuelGallons;

        //setting new player credits
        ChooseLevelScreenController.newPlayerTest.setCredits(playerCredits);

        //increase the ship's fuel
        shipCurrentFuel += fuelGallons;

        //setting new ship fuel level
        setCurrentFuel(shipCurrentFuel);

    }

    /**
     * repair method
     *
     * When called:
     * Give discount on repair price based on player's engineer skill
     * Player credits decrease based on new discount price
     * Ship's health increases based on amount of repair specs purchased
     *
     * Update player credits, update ship health
     *
     * @param playerCredits - player credits
     * @param playerEngineerSkill - player's engineer points
     * @param shipCurrentHealth - current ship health
     * @param repairSpecsPrice - repair price
     * @param purchasedRepairSpecs - purchased repair parts
     *
     */

    public void repair(Double playerCredits, int playerEngineerSkill,
                        int shipCurrentHealth, int repairSpecsPrice, int purchasedRepairSpecs) {
        //give discount based on player's engineer skill
        if (playerEngineerSkill != 0) {
            repairSpecsPrice -= repairSpecsPrice / playerEngineerSkill;
        }

        //decrease player credits based on new discounted price
        playerCredits -= repairSpecsPrice;

        //setting new player credits
        ChooseLevelScreenController.newPlayerTest.setCredits(playerCredits);

        //decrease ship health based on puchasedHealth
        shipCurrentHealth += purchasedRepairSpecs;

        //setting new ship health
        setHealth(shipCurrentHealth);
    }


}
