package sample;

public class GameOver {

    /**
     * Determine whether the game is over or not
     * @return whether the game is over or not
     */
    public static boolean gameOverOrNot() {

        boolean playerFoundWinItem = ChooseLevelScreenController.newPlayerTest.getFoundWinItem();
        int shipHealth = ChooseLevelScreenController.newPlayerTest.playerShip.getHealth();
        return shipHealth == 0 || playerFoundWinItem;

    }
}
