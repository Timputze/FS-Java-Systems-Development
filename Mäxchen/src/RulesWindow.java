package src;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * This class was created to make a Rules Window, which should popup through the menubars Helper tab. Its purpose is to list some game instructions for the user to read
 * See Rules.html
 */

//References:
// https://stackoverflow.com/questions/8849063/adding-a-scrollable-jtextarea-java
// https://stackoverflow.com/questions/27240113/closing-java-popup-frame-without-exiting-program

// Copied and edited (Timestamp 14:04)
// https://www.youtube.com/watch?v=Nz19LmgXd6g

public class RulesWindow extends JFrame {

    JEditorPane edita = new JEditorPane();
    JScrollPane scroll = new JScrollPane(edita);
    RulesWindow(){

        //Setting up the Jframe that holds the html editorpane
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Allows the User to close the Ruleswindow without closing the whole game itself. This would happen if we would set Jframe.EXIT_ON_CLOSE.
        setTitle("Rules");

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dim.width/3, dim.height /2); //sets the width and height of our frame

        setResizable(false);
        ImageIcon frameicon = new ImageIcon("SilverDice.jpg");
        setIconImage(frameicon.getImage());

        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        //Setting the editorpane to the Jframe
        edita.setEnabled(true);

        //Creating a Scrollpane for the Editorpane
        Dimension scrollSize = new Dimension(dim.width/3 - 50, dim.height/2 - 50);
        scroll.setPreferredSize(scrollSize);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        c.add(scroll);


        //Adding the html file to the Editorpane
        URL rulespage = RulesWindow.class.getResource("Rules.html");
        try {
            edita.setPage(rulespage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Setting the Jframe to visible
        setVisible(true);
    }
}
