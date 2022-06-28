
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;

public class Player extends JFrame{

	private int width;
	private int height;
	private Container contentPane;
	private JTextArea message;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;
	private int playerID;
	private int otherPlayer;
	private int[] values;
	private int maxTurns;
	private int turnsMade;
	private int myPoints;
	private int enemyPoints;
	private boolean buttonsEnabled;
	
	private ClientSideConnection csc;
	
	private static final long serialVersionUID = 1L;
	
	public Player(int w, int h) {
		width = w;
		height = h;
		contentPane = this.getContentPane();
		message = new JTextArea();
		b1 = new JButton("1");
		b2 = new JButton("2");
		b3 = new JButton("3");
		b4 = new JButton("4");
		values = new int [4];
		turnsMade = 0;
		myPoints = 0;
		enemyPoints = 0;
	}
	
	public void setupGUI() {
		this.setSize(width, height);
		this.setTitle("Player "+ playerID +": Mäxchen");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setLayout(new GridLayout(1, 5));
		contentPane.add(message);
		message.setText("Click to roll a dice!");
		message.setWrapStyleWord(true);
		message.setLineWrap(true);
		message.setEditable(false);
		contentPane.add(b1);
		contentPane.add(b2);
		contentPane.add(b3); //remove
		contentPane.add(b4); //remove
		
		if (playerID == 1) {
			message.setText("You are Player 1 and go first!");
			otherPlayer = 2;
			buttonsEnabled = true;
		} else {
			message.setText("You are Player 2 and go after Player 1!");
			otherPlayer = 1;
			buttonsEnabled = false;
			Thread t = new Thread(new Runnable() {
				public void run() {
				updateTurn();
			}
			});
			t.start();
		}
		toggleButtons();
		
		this.setVisible(true);
	} 
	
	public void connectToServer() {
		csc = new ClientSideConnection();
	}
	
	public void setUpButtons() {
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton b = (JButton) ae.getSource();
				int bNum = Integer.parseInt(b.getText());
				
				message.setText("You rolled the dice! Awaiting response from player " + otherPlayer);
				turnsMade++;
				System.out.println("Turns made so far: " + turnsMade);
				
				buttonsEnabled = false;
				toggleButtons();
				
				myPoints += values[bNum - 1];
				System.out.println("My points: " + myPoints);
				csc.sendButtonNum(bNum);
				
				if (playerID==2 && turnsMade == maxTurns) {
					checkWinner();
				} else {
					Thread t = new Thread(new Runnable() {
						public void run() {
						updateTurn();
						}
					});
					t.start();
				}	
			}
		};
		
		b1.addActionListener(al);
		b2.addActionListener(al);
		b3.addActionListener(al); //remove
		b4.addActionListener(al); //remove
	}
	
	public void toggleButtons() {
		b1.setEnabled(buttonsEnabled);
		b2.setEnabled(buttonsEnabled);
		b3.setEnabled(buttonsEnabled); //remove
 		b4.setEnabled(buttonsEnabled); //remove
		
	}
	
	public void updateTurn() {
		int n = csc.receiveButtonNum();
		message.setText("Your opponnent clicked button " + n + ". Now it is your turn!");
		enemyPoints += values[n-1];
		System.out.println("Your opponent has " + enemyPoints + " points.");
		if (playerID == 1 && turnsMade == maxTurns) {
			checkWinner();
		} else {
			buttonsEnabled = true;
			
		}
		toggleButtons();		
	}
	
	private void checkWinner() { //edit for JPlane input later with lying functionality implemented
		buttonsEnabled = false;
		if (myPoints>enemyPoints) {
			message.setText("You win! \n " + myPoints + "\n" + "You: " + myPoints + "\n" + "Opponent: " + enemyPoints);
		} else if (myPoints<enemyPoints) {
			message.setText("You lose! \n " + myPoints + "\n" + "You: " + myPoints + "\n" + "Opponent: " + enemyPoints);
		} else {
			message.setText("It's a tie! \n " + myPoints + "\n" + "You: " + myPoints + "\n" + "Opponent: " + enemyPoints);
		}
		csc.closeConnection();
	}
	
	private class ClientSideConnection {
		
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		
		public ClientSideConnection() {
			System.out.println("----------Client---------");
			try {
				socket = new Socket("localhost", 51737);
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
				playerID = dataIn.readInt();
				System.out.println("Connected to server as Player " + playerID + ".");
				maxTurns = dataIn.readInt() / 2;
				values[0] = dataIn.readInt();
				values[1] = dataIn.readInt();
				values[2] = dataIn.readInt(); //remove
				values[3] = dataIn.readInt(); //remove
				System.out.println("Maximum turns are:" + " " + maxTurns);
				System.out.println("Value 1 is:" + " " + values[0]);
				System.out.println("Value 2 is:" + " " + values[1]);
				System.out.println("Value 3 is:" + " " + values[2]); //remove
				System.out.println("Value 4 is:" + " " + values[3]); //remove
			} catch (IOException e) {
				System.out.println("IOException from ClientSideConnection constructor :(");
			}
		}
		
		public void sendButtonNum(int n) {
			try {
			dataOut.writeInt(n);
			dataOut.flush();
			} catch (IOException e) {
				System.out.println("IOException from sendButtonNum() CSC");
			}
		}
		
		public int receiveButtonNum() {
			int n = -1;
			try {
				n = dataIn.readInt();
				System.out.println("Player " + otherPlayer +" rolled the dice.");
			} catch (IOException e) {
				System.out.println("IOException from receiveButtonNum() csc");
			}
			return n;
		}
		public void closeConnection() {
			try {
				socket.close();
				System.out.println("Connection ended.");
			} catch (IOException e) {
				System.out.println("IOException from closeConnection() csc");
			}
		}
	}
	
	public static void main(String[] args){
		Player p = new Player(1200, 200);
		p.connectToServer();
		p.setupGUI();
		p.setUpButtons();
		
	}
	
}