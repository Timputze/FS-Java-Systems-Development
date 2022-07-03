package src;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * This class is supposed to be an "exciting" extra game feature, that has nothing to do with the main functionality of the game. It helps you pick a shot (alcohol) to drink for when you have to take a penalty shot.
 */
// References:
// Led to the idea of using a JOptionPane components:
// https://stackoverflow.com/questions/8852560/how-to-make-popup-window-in-java


public class ShotRoller extends JOptionPane {

    String[] alcohol_library = {"Tequila", "Vodka", "Beer","Water","Jägermeister","Absinth","Berentzen","Korn","Whiskey","Soju", "Moonshine","Escorial Green","WildsauTropfen","Ficken","Pfeffi","Berlin Air","Bacardi","Asbach","Ouzo","Sambouka","Ramazotti"};
    String[] story = {"You sit at the bar, alone. The Bartender sends you a glare, but serves you nonetheless.", "The Lady behind the counter is busy. There are lot of people in today. She hurries over.", "Do you really want another shot? Perhaps not, but the young bartender tries to show off and you receive one for free.", "You are happily laughing with your friends. One of them gets his wallet out and buys a round. Cheers!", "The drunk next to you has fallen asleep, but his shot remains unsipped on the counter. The Bartender looks at you and shrugs, uncaring."};

    /**
     * See The constructor of the ShotRoller class
     */
    public ShotRoller(){
        Random rand = new Random();

        int upperbound_alc = alcohol_library.length;
        int randomindex_alc = rand.nextInt(upperbound_alc); //Picks at random an item out of the array

        int upperbound_story = story.length;
        int randomindex_story = rand.nextInt(upperbound_story); //Picks at random an item out of the array

        String msg = "<html> <p>" + story[randomindex_story] + "</p><p></p><p>You receive a shot of <b>" + alcohol_library[randomindex_alc] + "</b></p></html>"; //combines the different randomized parts of the two arrays into a message
        JLabel label = new JLabel(msg);
        label.setFont(new Font("Sans-serif", Font.PLAIN, 14));

        showMessageDialog(null,label);
    }
}
