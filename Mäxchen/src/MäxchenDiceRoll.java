import java.util.Random;
import java.util.*;

public class MäxchenDiceRoll {

	public void rollDice() {

//beginning of user input for amount of players
		Scanner playerAmountInput = new Scanner(System.in);
		    System.out.println("Enter amount of players \n");

		    String playerAmount = playerAmountInput.nextLine();
		    System.out.println("Amount of players are: " + playerAmount);
				//end of user input for amount of players
Random ran = new Random();
		    
		int die1;
		int die2;
		
		die1 = ran.nextInt(6)+1;
		die2 = ran.nextInt(6)+1;
		
		System.out.println(die1 + " " +die2);
		
	int result;
		
if(die1>die2) {
	result = (die1*10) + die2;
	System.out.println("The result is: " + result);
} else if(die2>die1) {
	result = (die2*10) + die1;
	System.out.println("The result is: " + result);
} else if(die1==die2) {
	result = (die1*10) + die2;
	System.out.println("The result is: " + result);
} 


	}

}
