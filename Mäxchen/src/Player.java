package src;
/**
 * The class Player holds all parameters and methods necessary to save information about the two playing users.
 */

public class Player {

    private int maxDrunkeness; //How drunk the players are allowed to be before the game finishes
    private int perShotDrunkeness; //By how much the players drunkometers rise per Shot

    private int drunkometer = 0; //The current value of the players drunkometer. The drunkometer is a progress bar shown to the user over the UI (see Class GUI)

    private boolean isCurrentlyPlaying = false;

    private MaexchenDiceRoll faceValue; //The Dice Value a player actually rolled after pressing the shake button

    /**
     *  Getter method
     * @return faceValue = The Dice Value a player actually rolled after pressing the shake button
     */
    public MaexchenDiceRoll getFaceValue() {
        return faceValue;
    }

    /**
     *  Rolls a new dice value of the class MäxchenDiceROll according to the Mäxchen rules
     * @return faceValue
     * See  class MaexchenDiceRoll
     */
    public MaexchenDiceRoll rollDiceRoll() {
        return this.faceValue = new MaexchenDiceRoll();
    } //

    private MaexchenDiceRoll toldValue; //The Dice Value a player said he rolled (can be the truth or a lie)

    /**
     *  Getter method
     * @return faceValue = The Dice Value a player said he rolled (can be the truth or a lie)
     */
    public MaexchenDiceRoll getToldValue() {
        return toldValue;
    }

    /**
     *  A setter method. Saves a toldValue.
     *
     */
    public void setToldValue(int toldValue) {
        this.toldValue = new MaexchenDiceRoll(toldValue);
    } //A told value also follows the limits of the Mäxchen rules, which is why it is a MaexchenDiceRoll object.

    /**
     *  A setter method. If the player decides to tell the truth, the faceValue automatically is equal to the toldValue
     */
    public void tellTheTruth(){
        this.toldValue = this.faceValue;
    }

    /**
     *  A setter method. Resets the faceValue and the ToldValue for the start of a new round. A new round is started when one of the players loses.
     */
    public void resetValues(){
        System.out.println("VALUES RESET");
        this.faceValue = new MaexchenDiceRoll(0);
        this.toldValue = new MaexchenDiceRoll(0);
    }

    /**
     *  A setter method. When the believe button is pressed, the previous value inside the toldValue box gets saved as a faceValue as well
     */
    public void believe(){
        this.faceValue = this.toldValue;
    }

    /**
     *  The constructor of the class.
     * @param maxDrunkeness
     * @param perShotDrunkeness
     */
    public Player(int maxDrunkeness, int perShotDrunkeness) { //The constructor of the player class.
        this.maxDrunkeness = maxDrunkeness;
        this.perShotDrunkeness = perShotDrunkeness;
        this.toldValue = new MaexchenDiceRoll(0);
    }

    public boolean isCurrentlyPlaying() {
        return isCurrentlyPlaying;
    }

    /**
     *  If a player´s drunkometer is higher than what is maximally possible for a player to be, then this player is will "faint" and has lost the game.
     * @return boolean true or false = Answers the question whether the drunkometer is full.
     */
    public boolean isDrunk(){
        return drunkometer >= maxDrunkeness;
    }

    /**
     *  Used to check whether the Inactive (previous) player was lying. If their toldValue deviated from their rolled MaexchenDiceRoll (FaceValue) then they obviously did and a true boolean is returned.
     * @return boolean true
     */
    public boolean isLying(){
        return !(this.toldValue == this.faceValue);
    }

    /**
     *  A setter method. Sets the isCurrentlyPlaying parameter to false when called.
     */
    public void endTurn() {
        this.isCurrentlyPlaying = false;
    }

    /**
     *  A setter method. Sets the isCurrentlyPlaying parameter to true when called.
     */
    public void myTurn() {
        this.isCurrentlyPlaying = true;
    }

    /**
     *  A getter method.
     * @return the drunkometer value of the player = how full it is
     */
    public int getDrunkometer() {
        return drunkometer;
    }

    /**
     *  When a player drinks, their current drunkometer rises by a shot (perShotDrunkness parameter)
     */
    public void drink(){
        this.drunkometer += perShotDrunkeness;
    }
}
