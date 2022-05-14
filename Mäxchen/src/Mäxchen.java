import java.util.concurrent.ThreadLocalRandom;

public class Mäxchen {

	public static void main(String[] args) {

		int die1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
		int die2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);

		System.out.println(die1);
		System.out.println(die2);



	}

}
