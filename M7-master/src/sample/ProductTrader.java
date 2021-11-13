package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductTrader extends Trader {

    private ObservableList<Item> shopList;

    public ProductTrader() {

        shopList = FXCollections.observableArrayList();
        shopList.add(new Item("Food", (Math.random() * 8) + 55,
                0.0, (int) (Math.random() * 5) + 10));
        shopList.add(new Item("Water", (Math.random() * 8) + 25,
                0.0, (int) (Math.random() * 5) + 25));
        shopList.add(new Item("Ore", (Math.random() * 8) + 85,
                0.0, (int) (Math.random() * 5) + 25));
        shopList.add(new Item("Fur", (Math.random() * 8) + 45,
                0.0, (int) (Math.random() * 5) + 25));
        shopList.add(new Item("Medicine", (Math.random() * 8) + 105,
                0.0, (int) (Math.random() * 5) + 25));
        shopList.add(new Item("Robot", (Math.random() * 8) + 555,
                0.0, (int) (Math.random() * 5) + 25));
        shopList.add(new Item("Robot", (Math.random() * 8) + 99,
                0.0, (int) (Math.random() * 5) + 25));

    }


    /**
     * Raises the price of everything on stock
     * Happens when the player fails to negotiate with the trader
     */
    public void raisePrice() {

        for (int i = 0; i < shopList.size(); i++) {

            Item currentItem = shopList.get(i);
            currentItem.setBuyPrice(currentItem.getBuyPrice()
                    + 20);

        }
    }

    /**
     * Decreases the price of everything on stock
     * Happens when the player successfully negotiates with the trader
     */
    public void decreasePrice() {

        for (int i = 0; i < shopList.size(); i++) {

            Item currentItem = shopList.get(i);
            currentItem.setBuyPrice(currentItem.getBuyPrice() - 20);

        }
    }

    /**
     * Updates the trader's item list with the updated amount after trading
     * @param updatedShopList the trader's item list
     */
    public void updateShopItems(ObservableList<Item> updatedShopList) {

        shopList = updatedShopList;

    }

    /**
     * Get all the items in the trader's shop as an observable list
     * @return the trader's item list
     */
    public ObservableList<Item> getAllShopItems() {
        return shopList;
    }

}
