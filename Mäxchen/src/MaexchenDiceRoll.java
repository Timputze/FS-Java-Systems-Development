package src;

import java.util.Random;
import java.util.Scanner;

/**
 * This class calculates the possible dice Rolls of a normal Mäxchen game.
 * In other words, the highest value Mäxchen (21), the pashes (11,22,33,44,55,66) and the normal values where the first digit always has to be higher die value (e.g if a 3 and a 1 is rolled the resulting MaexchenDiceRoll is 31 and not 13)
 */
// References:
// https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html


public class MaexchenDiceRoll implements Comparable<MaexchenDiceRoll>{

	private int result = 0; //The variable that holds the result of a diceroll/shake

	/**
	 * See A getter method
	 * @return result
	 */
	public int getResult() {
		return result;
	}

	/**
	 * See The constructor of the MaexchenDiceRoll class without parameter
	 */
	public MaexchenDiceRoll() {
		this.result = rollDice();
	}

	/**
	 * See The constructor of the MaexchenDiceRoll class
	 * @param lieValue
	 * See Lier class
	 */
	public MaexchenDiceRoll(int lieValue){
		this.result = lieValue;
	}

	/**
	 * Checks whether the roll result is a Pasch (11,22,33,44,55,66)
	 * @return boolean
	 */
	public boolean isPasch(){
		if (this.result == 0){
			return false;
		}
		int lastDigit = this.result % 10;
		int firstDigit = (this.result - lastDigit) / 10;

		return firstDigit == lastDigit;
	}

	/**
	 * Checks whether the roll result is a Mäxchen (21)
	 * @return boolean
	 */
	public boolean isMäxchen(){
		return (this.result == 21);
	}

	/**
	 * Overwrites the compareTo method from the Comparable interface
	 *  uses the other methods from the MaexchenDiceRoll class to assign and compare the different cases.
	 * @param o the object to be compared.
	 * @return valueResult of either 1 or -1
	 */
	@Override
	public int compareTo(MaexchenDiceRoll o) { //Overriding the compareTo method of the Comparable Interface
		//System.out.println(this.result);
		//System.out.println("VS");
		//System.out.println(o.result);

		if (this.result == o.result) {
			return 0;
		}

		int valueResult = 0;
		if (o.getResult() > this.result) {
			valueResult = -1;
		} else if (o.getResult() < this.result){
			valueResult = 1;
		}


		if (this.isMäxchen()){
			return 1;
		}

		if (this.isPasch()){
			if (o.isMäxchen()){
				return -1;
			} 
			if (o.isPasch()){
				return valueResult;
			}
			return 1;
		}

		// current is not a pasch and not a mäxchen
		if (o.isPasch()) {
			return -1;
		} 

		return valueResult;
	}

	/**
	 *  This method is used in the lier class, where the player can freely input a value. This checks against unvalid values (smaller than the previous toldValue) from being entered.
	 * @param value
	 * @return boolean true or false
	 */
	public static boolean checkIfValidDiceValue(int value) {

		int lastDigit = value % 10;
		int firstDigit = (value - lastDigit) / 10;

		/*
		 * Check against last player roll
		 */
		MaexchenDiceRoll thisRoll = new MaexchenDiceRoll(value);
		MaexchenDiceRoll lastRoll = GameService.getInstance().getToldValueOfInActivePlayer();
		
		boolean thisRollBigger = thisRoll.compareTo(lastRoll) != -1;

		return (value <= 66) && ( lastDigit <= 6 && lastDigit != 0) && (firstDigit >= lastDigit) && thisRollBigger;
	}

	/**
	 *  A method that creates a new dice roll result and returns it. Conditions set the result to be a value according to the possible Mäxchen dice combinations
	 * @return result
	 */
	public static int rollDice() {
	
		int result = 0;
		Random ran = new Random();			    
				int die1;
				int die2;
				die1 = ran.nextInt(6)+1;
				die2 = ran.nextInt(6)+1;
				
		if(die1>die2) {
			result = (die1*10) + die2;
			//System.out.println("The result is: " + result + "\n");
		} else if(die2>die1) {
			result = (die2*10) + die1;
			//System.out.println("The result is: " + result + "\n");
		} else if(die1==die2) {
			result = (die1*10) + die2;
			//System.out.println("The result is: " + result + "\n");
		}
		return result;	
	}

	/**
	 * @deprecated
	 */
	public static int Lie() { //This method was useful in the beginning to test whether the class works on its own without the UI. Not actually used for the final game application.
		
				Scanner playerLie = new Scanner(System.in);
				    System.out.println("Enter the result of the dice roll: ");

				    int playerLieAmount = playerLie.nextInt();
				    System.out.println("Result according to the player is: " + playerLieAmount);
				    
				    return playerLieAmount;
										
	}

}
