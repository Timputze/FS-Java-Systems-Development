import java.util.*;

public class MäxchenDiceRoll {
	
	
	public int playerAmount() {
		//beginning of user input for amount of players
				Scanner playerAmountInput = new Scanner(System.in);
				    System.out.println("Enter amount of players \n");

				    int playerAmount = playerAmountInput.nextInt();
				    System.out.println("Amount of players are: " + playerAmount + "\n");
						//end of user input for amount of players
					return playerAmount;
				
	}
	
	public int rollDice() {
		
		int result = 0;
		
		Random ran = new Random();
				    
				int die1;
				int die2;
				
				die1 = ran.nextInt(6)+1;
				die2 = ran.nextInt(6)+1;
				
		if(die1>die2) {
			result = (die1*10) + die2;
			System.out.println("The result is: " + result + "\n");
		} else if(die2>die1) {
			result = (die2*10) + die1;
			System.out.println("The result is: " + result + "\n");
		} else if(die1==die2) {
			result = (die1*10) + die2;
			System.out.println("The result is: " + result + "\n");
		}
		
		return result;
			
	}

	public int Lie() {
		
				Scanner playerLie = new Scanner(System.in);
				    System.out.println("Enter the result of the dice roll: ");

				    int playerLieAmount = playerLie.nextInt();
				    System.out.println("Result according to the player is: " + playerLieAmount);
				    
				    return playerLieAmount;
										
	}
	
	}
