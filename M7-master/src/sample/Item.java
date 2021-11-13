package sample;
import javafx.scene.control.TextField;

public class Item {


    protected String name;
    protected double buyPrice;
    protected double sellPrice;

    //buy and sell count track how much a player purchases

    private TextField buyCount;
    private TextField sellCount;

    private int sellQuantity;


    protected static int merchPts = SkillPointsScreenController.merchantCounter;

    private int quantity;


    public Item(String name, double buyPrice, double sellPrice, int quantity) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.buyCount = new TextField();
        this.sellCount = new TextField();

    }

    /*private static Item food = new Item("Food", 100.0, 90.0,
            (int) (Math.random() * 10) + 25);
    private static Item water = new Item("Water", 30.0, 27.0,
            (int) (Math.random() * 10) + 25);
    private static Item fur = new Item("Fur", 250.0, 225.0,
            (int) (Math.random() * 10) + 25);
    private static Item ore = new Item("Ore", 400.0, 380.0,
            (int) (Math.random() * 10) + 25);
    private static Item game = new Item("Game", 200.0, 180.0,
            (int) (Math.random() * 10) + 25);
    private static Item gun = new Item("Gun", 800.0, 720.0,
            (int) (Math.random() * 10) + 25);
    private static Item medicine = new Item("Medicine", 500.0, 450.0,
            (int) (Math.random() * 10) + 25);
    private static Item narcotics = new Item("Narcotics", 3000.0, 2700.0,
            (int) (Math.random() * 10) + 25);
    private static Item robot = new Item("Robot", 4000.0, 3600.0,
            (int) (Math.random() * 10) + 25);*/

    //sell quantity should be default of 0-- urica
    private static Item food = new Item("Food", 100.0, 90.0,
            0);
    private static Item water = new Item("Water", 30.0, 27.0,
            0);
    private static Item fur = new Item("Fur", 250.0, 225.0,
            0);
    private static Item ore = new Item("Ore", 400.0, 380.0,
            0);
    private static Item game = new Item("Game", 200.0, 180.0,
            0);
    private static Item gun = new Item("Gun", 800.0, 720.0,
            0);
    private static Item medicine = new Item("Medicine", 500.0, 450.0,
            0);
    private static Item narcotics = new Item("Narcotics", 3000.0, 2700.0,
            0);
    private static Item robot = new Item("Robot", 4000.0, 3600.0,
            0);

    protected static Item[] itemArray = new Item[9];

    static {
        itemArray[0] = food;
        itemArray[1] = water;
        itemArray[2] = fur;
        itemArray[3] = ore;
        itemArray[4] = game;
        itemArray[5] = gun;
        itemArray[6] = medicine;
        itemArray[7] = narcotics;
        itemArray[8] = robot;
        calculateBuyPrice(itemArray);
        calculateSellPrice(itemArray);
    }

    private static void calculateBuyPrice(Item[] itemArray) {
        for (int i = 0; i < 9; i++) {
            int basePrice = (int) itemArray[i].buyPrice;
            int newPrice = basePrice
                    - ChooseLevelScreenController.newPlayerTest.getMerchantPoints();
            itemArray[i].buyPrice = newPrice;
        }
    }

    private static void calculateSellPrice(Item[] itemArray) {
        for (int i = 0; i < 9; i++) {
            int basePrice = (int) itemArray[i].sellPrice;
            int newPrice = basePrice
                    + ChooseLevelScreenController.newPlayerTest.getMerchantPoints();
            itemArray[i].sellPrice = newPrice;
        }
    }

    //added getters and setters for quantity, name, and buy price to populate tableview
    public int getItemQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    //added specific setter method for marketplace tableview procedure
    public void setName(String name) {
        this.name = name;
    }

    public void setItemQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getBuyPrice() {
        return this.buyPrice;
    }

    public void setBuyPrice(double price) {

        this.buyPrice = price;
    }


    public TextField getBuyCount() {
        return buyCount;
    }

    public TextField getSellCount() {
        return sellCount;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public static Item[] getItemArray() {
        return itemArray;
    }

    public Integer getQuantity() {

        return quantity;

    }


}
