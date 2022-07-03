package src;
/**
 * The class GameService holds all parameters and methods necessary to save the current game state. (The current and previous round)
 * This is specifically important to compare the previous dice faceValues and toldValues for the Mäxchen game functionality, as these are params important for the doubt, believe, truth and lie features of a normal game.
 */
// References accessed during development:
// https://www.baeldung.com/java-singleton
// https://www.javatpoint.com/instance-variable-in-java
// http://www.cafeaulait.org/course/week2/43.html">The?Operator</a>
// https://java-design-patterns.com/patterns/game-loop/#:~:text=A%20game%20loop%20runs%20continuously,user%20input%20and%20processor%20speed
// https://www.code4projects.net/design-patterns-in-game-programming/
// https://www.bogotobogo.com/DesignPatterns/mvc_model_view_controller_pattern.php
// https://www.baeldung.com/java-enum-simple-state-machine#:~:text=A%20state%20machine%20%E2%80%94%20also%20called,state%20changes%20are%20called%20transitions


public class GameService {

    Player playerA; //The first player

    Player playerB; //The second player

    static int amountTillPlayerFaints = 100; //This is the total amount a player can "drink" or be drunk.

    static int amountPerShot = 10; //With every value, a player´s Drunkometer rises by this set amount.

    GamePhase currentGamePhase; //This is an object of the class Gamephase, which holds all possible phases of a Gameround.

    MyGUI guiToUpdate; //This parameter is used to update the gui when the gamephase switches

    private static GameService INSTANCE; //Here we had the problem that in the GUI class, the gameService could not be called normally, so we had to initiate is as an instance instead. (See reference 1 - Singletons)

    /**
     *  this method calls upon the constructor of the GamerService class
     */
    private GameService() {

        this.playerA = new Player(amountTillPlayerFaints, amountPerShot); //Our Version of Mäxchen is playable in a 2-player local couch multiplayer. Thus, these two players are created at the start of a new game.
        this.playerB = new Player(amountTillPlayerFaints, amountPerShot);

        this.playerA.myTurn(); //A new game starts with Player A being the first player
        this.currentGamePhase = GamePhase.ROLL_DICE_PHASE; //A new game starts with the shake phase
    }

    /**
     *  This method is used to connect the UI to the gameService
     * @param ui
     */
    public void registerUI(MyGUI ui){
        this.guiToUpdate = ui;
    }

    /**
     *  Creates an instance of the GamerService class in order to access the class from other class
     * @return instance of the GameService class
     */
    public static GameService getInstance() { //It checks if an instance of GameService (whether the game is already running or not) has already been created. If not (instance = null), then a new instance is made.
        if(INSTANCE == null) {
            INSTANCE = new GameService();
        }
        
        return INSTANCE;
    }

    /**
     *  this method checks to see if either of the two players is drunk. A game is considered won if this is the case.
     * See isDrunk() method
     * @return boolean true or false
     */
    public boolean isWon(){
        return this.playerA.isDrunk() || this.playerB.isDrunk();
    }


    /**
     *  This method checks which player (A or B) is currently playing and "active"
     * In order to save the values to the correct player (and know who is the current(Active) player and who is the previous(Inactive) player) we implemented the getActivePlayer and getInactivePlayer methods.
     * @return playerA or playerB
     */
    public Player getActivePlayer(){
        if (this.playerA.isCurrentlyPlaying()){
            return playerA;
        } else {
            return playerB;
        }
    }

    /**
     *  This method checks which player (A or B) is currently not playing and "inactive"
     * In order to save the values to the correct player (and know who is the current(Active) player and who is the previous(Inactive) player) we implemented the getActivePlayer and getInactivePlayer methods.
     * @return playerA or playerB
     * */
    public Player getInActivePlayer(){
        if (this.playerA.isCurrentlyPlaying()){
            return playerB;
        } else {
            return playerA;
        }
    }

    /**
     *  a gettermethod that gets the toldValue of the current player
     * @return a toldValue of the currently playing player
     */
    public MaexchenDiceRoll getToldValueOfInActivePlayer(){
        return this.getInActivePlayer().getToldValue();
    }
    //For every round, the game works with a "ToldValue" and a "FaceValue". The ToldValue is the value of dice that is given(told) to the next player (e.g B) by the previous player (e.g A) during their END_PHASE.
    //This value can either be the truth (the actual faceValue of what was rolled by the Inactiveplayer) or a lie. Either way it is being passed on as a ToldValue to the next player (B).
    //The next player (B) (now the Active player) starts of in the RECEIVE_PHASE. Here they are given the choice whether to believe or doubt the previous players ToldValue.
    //If player B chooses to believe, the ToldValue is saved as a FaceValue, which player B now has to trump (get a higher value).

    /**
     *  This method switches around the players and tells them through the console that they should change devices.
     * The Console output was originally implemented for testing, but we choose to keep it, as it could be useful.
     * See myTurn() method
     */
    public void endTurn(){
        System.out.println("TURN ENDED.");
        System.out.println("PLAYERS WILL NOW BE CHANGED.");
        System.out.print(this.getActivePlayer().toString());

        Player active = this.getActivePlayer();
        Player inactive = this.getInActivePlayer();

        active.endTurn();
        inactive.myTurn();

        System.out.print("It is now "+ this.getActivePlayer().toString() + " turn.PLEASE PASS ON THE DEVICE.");
    }

    /**
     *  This method initiates the next phase of the game based on what the current phase is.
     * Order of phases: ROLL_DICE_PHASE => END_PHASE => WAIT_FOR_END_ROUND => RECEIVE_PHASE => ROLL_DICE_PHASE etc.(Loop)
     */
    public void nextPhase(){
        switch (this.currentGamePhase) {
            case END_PHASE:
                this.currentGamePhase = GamePhase.WAIT_FOR_END_ROUND;
                break;
            case RECEIVE_PHASE:
                this.currentGamePhase = GamePhase.ROLL_DICE_PHASE;
                break;
            case ROLL_DICE_PHASE:
                this.currentGamePhase = GamePhase.END_PHASE;
                break;
            case WAIT_FOR_END_ROUND:
                this.currentGamePhase = GamePhase.RECEIVE_PHASE;
                this.endTurn();
                break;
            default:
                break;
        }
        System.out.println("It is now the "+ currentGamePhase.toString()+" Phase"); //This output was originally used for testing purposes, but we left it in because it might be useful.
        this.triggerUIUpdate(); //Updates the UI (since certain Buttons are disabled based on what phase it is).
    }

    //The following methods are used for a specific "Event", hence the naming "on-...".
    /**
     *  An event method that is called when the skake button is clicked
     * @return getActivePlayer().getFaceValue() = the value that has been actually rolled and was saved as a faceValue
     */
    public MaexchenDiceRoll onPlayerShake(){
        if (!(this.currentGamePhase == GamePhase.ROLL_DICE_PHASE)){
            return null;
        }

        this.getActivePlayer().rollDiceRoll();

        this.nextPhase();
        return this.getActivePlayer().getFaceValue();
    }

    /**
     *  An event method that is called when the truth button is clicked
     * @return boolean true or false
     * See method tellTheTruth() = the value that has been actually rolled is both saved as the faceValue and the toldValue
     */
    public boolean onPlayerTruth(){
        if (!(this.currentGamePhase == GamePhase.END_PHASE)){
            return false;
        } 
        
        this.getActivePlayer().tellTheTruth();
        this.nextPhase();
        return true;
    }
    /**
     *  An event method that is called when the lie button is clicked
     * @return boolean true or false
     * See method setToldValue() = the value that has been inputted as a lie by the player gets saved as a ToldValue
     */
    public boolean onPlayerLie(int value){
        if (!(this.currentGamePhase == GamePhase.END_PHASE)){
            return false;
        } 
        this.getActivePlayer().setToldValue(value);
        this.nextPhase();
        return true;
    }

    /**
     *  An event method that is called when the end turn button is clicked
     * @return boolean true or false
     */
    public boolean onEndTurnButton(){
        if (!(this.currentGamePhase == GamePhase.WAIT_FOR_END_ROUND)){
            return false;
        }
        nextPhase();
        return true;
    }

    /**
     *  An event method that is called when the beleive button is clicked
     * Here we have a special case: If a Mäxchen has been passed as a told Value by the previous player, the current player lose when they press the believe button
     * @return boolean true or false
     */
    public boolean onBelieve(){ //In this case the method is used when the player shakes the Believe button.
        if (!(this.currentGamePhase == GamePhase.RECEIVE_PHASE)){
            return false;
        }
        if (this.getInActivePlayer().getToldValue().isMäxchen() ){
            this.getActivePlayer().drink();
            this.getInActivePlayer().resetValues();
            this.nextPhase();
            return true;

        } else {
            this.getInActivePlayer().believe();
            this.nextPhase();
            return true;
        }
    }

    /**
     * This enum is an object that saves different cases. As a doubtor you can either be right or wrong when doubting the previous player
     */
    public enum DoubtResult{
        INVALID, //For errors
        WAS_RIGHT, //Either the player was right about doubting and caught the previous player in a lie (Consequence; Previous player has to drink, the FaceValue gets reset)
        WAS_WRONG //Or the previous player has told the truth and the current player was wrong (Consequence: The current player has to drink, the FaceValue gets reset)
    }

    /**
     *  In this case this event method is used when the player shakes the Doubt button. As seen by the Enum used, the Doubt Event is special.
     * The method also handles To which player the loss(and consequently the penalty shot) is assigned is based on whether the current player rightfully doubted or not.
     * @return doubtResult = whether the doubtor WAS_RIGHT or WAS_WRONG
     */
    public DoubtResult onPlayerDoubt(){
        if (!(this.currentGamePhase == GamePhase.RECEIVE_PHASE)){
            return DoubtResult.INVALID;
        }
        DoubtResult doubtResult = (this.getInActivePlayer().isLying())? DoubtResult.WAS_RIGHT: DoubtResult.WAS_WRONG; // Here we used the ? operator to return the respective Doubt result. Islying() returns a true boolean if the previous player lied, therefore the WAS_Doubt result is returned. For a false boolean the IS_WRONG result is returned.

        //System.out.println(this.getInActivePlayer().getFaceValue().getResult()); // Was used for testing
        //System.out.println(this.getInActivePlayer().getToldValue().getResult()); // Was used for testing
        //System.out.println(this.getInActivePlayer().isLying()); // Was used for testing
        
        if (doubtResult == DoubtResult.WAS_RIGHT) {
            this.getInActivePlayer().drink();
        } else {
            this.getActivePlayer().drink();
        }

        this.getInActivePlayer().resetValues(); //When something is doubted, the round restarts with the doubter (no matter who lost). Much like the start of a new game, the "Inactive" player should have no set told and face values and therefore they are reset.
        this.nextPhase();
        return doubtResult;
    }

    /**
     *  this method triggers or forces the UI to update. This is necessary as there are different layouts for the UI, depening on the current game phase.
     */
    public void triggerUIUpdate(){
        if (this.guiToUpdate != null) {
            this.guiToUpdate.triggerUIUpdate();
        }
    }
}
