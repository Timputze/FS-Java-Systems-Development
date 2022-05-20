import java.util.*;

public class MäxchenDiceRoll {
	
	public void playerAmount() {
		//beginning of user input for amount of players
				Scanner playerAmountInput = new Scanner(System.in);
				    System.out.println("Enter amount of players \n");

				    String playerAmount = playerAmountInput.nextLine();
				    System.out.println("Amount of players are: " + playerAmount + "\n");
						//end of user input for amount of players
				
	}
	
	public void rollDice() {
		    
		Random ran = new Random();
				    
				int die1;
				int die2;
				
				die1 = ran.nextInt(6)+1;
				die2 = ran.nextInt(6)+1;
			
		if(die1>die2) {
			int result;
			result = (die1*10) + die2;
			System.out.println("The result is: " + result + "\n");
		} else if(die2>die1) {
			int result;
			result = (die2*10) + die1;
			System.out.println("The result is: " + result + "\n");
		} else if(die1==die2) {
			int result;
			result = (die1*10) + die2;
			System.out.println("The result is: " + result + "\n");
		}
			
	}

	public void Lie() {
		
				Scanner playerLie = new Scanner(System.in);
				    System.out.println("Enter the result of the dice roll: ");

				    String playerLieAmount = playerLie.nextLine();
				    System.out.println("Result according to the player is: " + playerLieAmount);
										
	}
	
	}
