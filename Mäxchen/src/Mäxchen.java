import java.util.concurrent.ThreadLocalRandom;
//taken from StackOverflow
import java.util.*;
import java.util.Arrays;

public class Mäxchen {

	public static void main(String[] args) {

//beginning of user input for amount of players
		Scanner playerAmountInput = new Scanner(System.in);
		    System.out.println("Enter amount of players \n");

		    String a = playerAmountInput.nextLine();
		    System.out.println("Amount of players are: " + a);
				//end of user input for amount of players

		int playerAmount=2;
		int playerResult=0;
		int die1=ThreadLocalRandom.current().nextInt(1, 6 + 1); //random die 1
		int die2=ThreadLocalRandom.current().nextInt(1, 6 + 1); //random die 2
		int actualResult=0;

Integer[] numbers = new Integer[] { die1, die2 }; //putting dice into array

Arrays.sort(numbers, Collections.reverseOrder()); //sorting dice in descending order

System.out.println("The dice return: " + Arrays.toString(numbers));



	}

}