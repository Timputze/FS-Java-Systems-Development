package src;
/**
 * This class helps separate and program the game according to 4 game phases. Mäxchen itself has a minimum of 3 stages. (Tic-Tac-Toe e.g is just a 1-phase game)
 * The 4th phase that was implemented here is used to separate the rounds (Player 1 vs Player 2) from each other. The current Player has to press an "End Turn" Button and pass on the computer to the other player before the game can continue.
 */
//References used:
//https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html#:~:text=An%20enum%20type%20is%20a,the%20days%20of%20the%20week
//https://www.geeksforgeeks.org/enum-in-java/#:~:text=Enums%20are%20used%20when%20we,represented%20using%20enum%20data%20type


public enum GamePhase{
    RECEIVE_PHASE,
    ROLL_DICE_PHASE,
    END_PHASE,
    WAIT_FOR_END_ROUND
}