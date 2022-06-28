
import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * This is a version of the popular German drinking game called "Mäxchen". It involves two dice that are rolled and then arranged 
 * so that the larger of the two numbers is the the "tens" decimal place and the smaller is in the "ones" place, e.g. if I rolled
 * a 5 and a 3, the resulting number would be 53. Any double number is higher than "normal" numbers, e.g. 11 is higher than 53.
 * However, a 22, 44, 66 etc. are all higher than an 11. The highest "trump" number is 21, called Mäxchen, which is a guaranteed win,
 * as nothing can beat it. Additionally, each players' result has to be higher than the previous one's result. 
 * 
 * The game goes as follows:
 * One player shakes the dice, gets to see the result in secrecy, as then can either be honest or lie about the result before passing
 * it on to the next player. Afterwards, the player whom the dice were passed to can either choose to not look and pass it on blindly 
 * to the next player and saying a number higher than the one the previous player said and hope the previous player was bluffing, or he 
 * can shake it in hopes of getting a higher number and then passing it on to the next player (the number has to be higher than the 
 * previous one, even if he rolled a number less than the previous player). Alternatively, the player whom the dice were passed to can
 * call his bluff and reveal the true number the dice rolled. If it is different to what the player said, he must drink. However if 
 * you reveal the dice and the player was being truthful, you must drink. Then you shake the dice and say a number and pass the dice on 
 * to the next player. 
 * 
 * This process repeats until one player is blacked out drunk.
 */
public class Server {

	private ServerSocket ss;
	private int playerNo;
	private ServerSideConnection player1;
	private ServerSideConnection player2;
	private int turnsMade;
	private int maxTurns;
	private int[] values;
	private int player1ButtonNum;
	private int player2ButtonNum;
	
	public Server() {
		System.out.println("---------Server----------");
		playerNo = 0;
		turnsMade = 0;
		maxTurns = 10;
		values = new int [4];
		int die1;
		int die2;
		int result = 0;
		Random ran = new Random();
		
		for (int i = 0; i < values.length; i++) {
			die1 = ran.nextInt(6)+1;
			die2 = ran.nextInt(6)+1;
			if(die1>die2) {
				result = (die1*10) + die2;
			} else if(die2>die1) {
				result = (die2*10) + die1;
			} else if(die1==die2) {
				result = (die1*10) + die2;
			}
			values[i] = result;
			System.out.println("Value number " + (i + 1) + " " + "is: " + values[i]);
		}
		
		try {
			ss = new ServerSocket(51737);
		} catch (IOException e) {
			System.out.println("IOException from Server constructor :(");
		}
	}
	
	public void acceptConnections() {
		try {
			System.out.println("Waiting for connections...");
			while(playerNo < 2) {
				Socket s = ss.accept();
				playerNo++;
				System.out.println("Player ID " + playerNo + " has connected to the server.");
				ServerSideConnection ssc = new ServerSideConnection(s, playerNo);
				if (playerNo == 1) {
					player1 = ssc;
				} else {
					player2 = ssc;
				}
				Thread t = new Thread(ssc);
				t.start();
			}
			System.out.println("We now have 2 players, the lobby is full.");
		} catch(IOException e) {
			System.out.println("IOException from acceptConnections :(");
		}
	}
	
	private class ServerSideConnection implements Runnable {
		
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		private int playerID;
		
		public ServerSideConnection(Socket s, int id) {
			socket = s;
			playerID = id;
			try {
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
			} catch(IOException e) {
				System.out.println("IOException from ServerSideConnection constructor :(");
			}
		}
		
		public void run() {
			try {
				dataOut.writeInt(playerID);
				dataOut.writeInt(maxTurns);
				dataOut.writeInt(values[0]);
				dataOut.writeInt(values[1]);
				dataOut.writeInt(values[2]);
				dataOut.writeInt(values[3]);
				dataOut.flush();
				
				while(true) {
					if (playerID == 1) {
						player1ButtonNum = dataIn.readInt();
						System.out.println("Player 1 clicked button" + " " + player1ButtonNum);
						player2.sendButtonNum(player1ButtonNum);
					} else {
						player2ButtonNum = dataIn.readInt();
						System.out.println("Player 2 clicked button" + " " + player2ButtonNum);
						player2.sendButtonNum(player2ButtonNum);
					}
					turnsMade++;
					if (turnsMade == maxTurns) {
						System.out.println("Maximum turns has been reached!");
						break;
					}
				}
				player1.closeConnection();
				player2.closeConnection();
			} catch (IOException e) {
				System.out.println("IOException from run() constructor :(");
			}
		}
		
		public void sendButtonNum(int n) {
			try {
				dataOut.writeInt(n);
				dataOut.flush();
			} catch (IOException e) {
				System.out.println("IOException from sendButtonNum() ssc");
			}
		}
		public void closeConnection() {
			try {
				socket.close();
				System.out.println("Connection ended.");
			} catch (IOException e) {
				System.out.println("IOException from closeConnection() ssc.");
			}
		}
	}
	
	public static void main(String args[]) {
		Server gs = new Server();
		gs.acceptConnections();
		
	}
	
}
