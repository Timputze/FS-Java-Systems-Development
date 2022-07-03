package src;

import javax.swing.*;

/**
 * This class is used to gather a lieValue from the user as input. It also keeps the User from entering a value that does not follow the MaexchenDiceRoll limitations, by using that classes checkIfValidDiceValue method.
 * See class MaexchenDiceRoll
 * See method checkIfValidDiceValue()
 */

public class Lier extends JOptionPane {
    public String value; //Saves the value that the player inputs and wants as a lie

    /**
     *  The constructor of the Lier class.
     */
    public Lier(){
    this.value = showInputDialog(null, "Please enter your lie below. Make sure that the Lie is greater than the value told by the previous player!", "You are going to lie!", JOptionPane.PLAIN_MESSAGE);
    int lie_value = Integer.parseInt(value);
    catcher(lie_value);
    }

    /**
     *  A recursive method that catches an incorrect MäxchenDiceROll input.
     */
    private void catcher(int lie_value) {
        //System.out.println(lie_value);
        if (!MaexchenDiceRoll.checkIfValidDiceValue(lie_value)) {
            this.value = showInputDialog(null, "You entered a value that does not match a possible dice pair value. Please try again.", "You are going to lie!", JOptionPane.WARNING_MESSAGE);
            lie_value = Integer.parseInt(value);
            catcher(lie_value);
        }
    }

    /**
     * A getter method. Turns the value from a String to an Int.
     * @return lie_value
     */
    public int getLie(){
       int lie_value = Integer.parseInt(value);
       return lie_value;
    }
}
