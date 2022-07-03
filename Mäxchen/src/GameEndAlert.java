package src;

import javax.swing.*;
import java.awt.*;

/**
 * This class creates a JOptionPane that acts as a "GameOver" message, which closes the entire game after the player presses the ok button.
 */
//References:
// https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
// https://stackoverflow.com/questions/30725378/how-to-close-when-on-joptionpane-showmessagedialo


public class GameEndAlert extends JOptionPane{

    /**
     * calles the constructor of the GameEndAlert class
     */
    GameEndAlert(){
            String msg = "<html> <p></p><p>Congratulations! One of the players is fully drunk and has <b>fainted.</b> </p> <p>As a result we declare <b> a GAMER OVER! </b> </p> <p> The game will now be closed...</p></html>";
            JLabel label = new JLabel(msg);
            label.setFont(new Font("Sans-serif", Font.PLAIN, 22));
            showMessageDialog(null,label);
            System.exit(0); //Makes the system close the game.

        }

}
