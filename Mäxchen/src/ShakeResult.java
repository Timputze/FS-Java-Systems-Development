package src;

import javax.swing.*;
import java.awt.*;

/**
 * A class that creates a pop-up message that displays the rolled dice value to the player.
 */

public class ShakeResult extends JOptionPane {
    int result;

    /**
     *  The constructor of the class. It takes a result as parameter to be displayed.
     * @param result
     */
    ShakeResult(int result){
        this.result = result;
        String msg = "<html> <p></p><p>You rolled:<b>" + result + "</b></p></html>";
        JLabel label = new JLabel(msg);
        label.setFont(new Font("Sans-serif", Font.PLAIN, 14));

        showMessageDialog(null,label);
    }
}