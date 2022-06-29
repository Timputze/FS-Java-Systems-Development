import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is a version of the popular German drinking game called "Mäxchen". It involves two dice that are rolled and then arranged 
 * so that the larger of the two numbers is the the "tens" decimal place and the smaller is in the "ones" place, e.g. if I rolled
 * a 5 and a 3, the resulting number would be 53. Any double number is higher than "normal" numbers, e.g. 22 is higher than 53.
 * However, a 44, 55, 66 etc. are all higher than an 22 but the 22 would be higher than the 11. The highest "trump" number is 21, 
 * called Mäxchen, which is a guaranteed win, as nothing can beat it. Additionally, each players' result has to be higher than the 
 * previous one's result. 
 * 
 * The game goes as follows:
 * One player shakes the dice, gets to see the result in secrecy, as then can either be honest or lie about the result before passing
 * it on to the next player. Afterwards, the player whom the dice were passed to can either choose to not look and pass it on blindly 
 * to the next player and saying a number higher than the one the previous player said and hope the previous player was bluffing, or he 
 * can shake it in hopes of getting a higher number and then passing it on to the next player (the number has to be higher than the 
 * previous one, even if he rolled a number less than the previous player, meaning he will have to lie). Alternatively, the player whom 
 * the dice were passed to can call his bluff and reveal the true number the dice rolled. If it is different to what the player said, he 
 * must drink. However if you reveal the dice and the player was being truthful, you must drink. Then you shake the dice and say a number 
 * and pass the dice on to the next player. 
 * 
 * This process repeats until one player is blacked out drunk.
 */

public class MäxchenFinal {
	
	public static int rollDice() {
		int result = 0;
		Random ran = new Random();			    
				int die1;
				int die2;
				die1 = ran.nextInt(6)+1;
				die2 = ran.nextInt(6)+1;
		if(die1>die2) {
			result = (die1*10) + die2;
		} else if(die2>die1) {
			result = (die2*10) + die1;
		} else if(die1==die2) {
			result = (die1*10) + die2;
		}
		return result;
	}		
	
public static void main(String[] args) {
	
	int x = rollDice();
	String rolling;
	
	Scanner sc = new Scanner(System.in);
	System.out.println("Do you want to roll the dice? 'Y' for yes and 'N' for No. \n");
	rolling = sc.nextLine();
	
	if (rolling.equals("Y")) {
		System.out.println("The dice thrown yield the result: " + x + "\nThis number will vanish in a bit so remember it well! \n");
	} else {
		int sc3;
		Scanner sc2 = new Scanner(System.in);
		System.out.println("What's the new result then? \n");
		sc3 = sc2.nextInt();
		System.out.println("You have said it is: " + sc3);
		System.out.println("Pass to the next player. \n");
		Thread t = new Thread() {
		     public void run() {             
		         String[] args = { };
		         MäxchenFinal.main(args);     
		     }  
		};  
		t.start(); 
	}
	
	Timer timer = new Timer();
	timer.schedule(new TimerTask() { 

	   public void run() {
		   for (int i = 0; i < 100000; i++) {
	       System.out.println("\n");
		   }
	   }
	},  10000);
	
	Scanner playerLie = new Scanner(System.in);
    System.out.println("Enter the result of the dice roll that you want the other player to see once this text has disappeared: \n");

    int playerLieAmount = playerLie.nextInt();
    System.out.println("Result according to the player is: " + playerLieAmount + "\n");
	
	Scanner input = new Scanner(System.in);
	System.out.println("Do you believe this number? answer Y for yes and N for no. \n \n");
	
	String guess = input.nextLine();
	
	if (guess.equals("Y")) {
		System.out.println("Pass to the next player. \n");
		Thread t = new Thread() {
		     public void run() {             
		         String[] args = { };
		         MäxchenFinal.main(args);     
		     }  
		};  
		t.start(); 
	} else if (guess.equals("N") && x == playerLieAmount) {
		System.out.println("The player was being honest! You lose and have to drink!" + "\n \n");
	} else if (guess.equals("N") && x != playerLieAmount) {
		System.out.println("The player was lying! You win and the previous player must drink!" + "\n \n");
	}

	
}
}