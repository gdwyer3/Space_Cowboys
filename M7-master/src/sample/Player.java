package sample;

public class Player {

    //player information
    private String playerName;
    private String chosenDifficulty;
    private Integer totalSkillPoints;
    private Integer fighterPoints;
    private Integer pilotPoints;
    private Integer engineerPoints;
    private Integer merchantPoints;
    private Double credits;
    private boolean foundWinItem;
    protected Ship playerShip; // added ship attribute

    public Player() {
        playerShip = new Ship("Gnat", 5, 12, 50, 0); // ship comes with player
    }
    /*public Player(int credits) {
        this.credits = credits;
    }*/

    public Player(String name, String difficulty, Integer skill,
                   Integer fighter, Integer pilot, Integer engineer, Integer merchant) {
        super();
        this.playerName = name;
        this.chosenDifficulty = difficulty;
        this.totalSkillPoints = skill;
        this.fighterPoints = fighter;
        this.pilotPoints = pilot;
        this.engineerPoints = engineer;
        this.merchantPoints = merchant;
        playerShip = new Ship("Gnat", 5, 12, 50, 0); // ship comes with player
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getChosenDifficulty() {
        return chosenDifficulty;
    }

    public Integer getTotalSkillPoints() {
        return totalSkillPoints;
    }

    public Integer getFighterPoints() {
        return fighterPoints;
    }

    public Integer getPilotPoints() {
        return pilotPoints;
    }

    public Integer getEngineerPoints() {
        return engineerPoints;
    }

    public Integer getMerchantPoints() {
        return merchantPoints;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setChosenDifficulty(String chosenDifficulty) {
        this.chosenDifficulty = chosenDifficulty;
    }

    public void setTotalSkillPoints(Integer totalSkillPoints) {
        this.totalSkillPoints = totalSkillPoints;
    }

    public void setFighterPoints(Integer fighterPoints) {
        this.fighterPoints = fighterPoints;
    }

    public void setPilotPoints(Integer pilotPoints) {
        this.pilotPoints = pilotPoints;
    }

    public void setEngineerPoints(Integer engineerPoints) {
        this.engineerPoints = engineerPoints;
    }

    public void setMerchantPoints(Integer merchantPoints) {
        this.merchantPoints = merchantPoints;
    }

    public void setFoundWinItem(boolean found) {

        this.foundWinItem = found;

    }

    public boolean getFoundWinItem() {

        return this.foundWinItem;

    }

}
