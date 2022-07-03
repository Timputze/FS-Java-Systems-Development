package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//
 // A short summary of how the game is supposed to run. For more information see the ReadMe file or the Rules window in the game application itself.
 // This is a version of the popular German drinking game called "Mäxchen". It involves two dice that are rolled and then arranged
 // so that the larger of the two numbers is the "tens" decimal place and the smaller is in the "ones" place, e.g. if I rolled
 // a 5 and a 3, the resulting number would be 53. Any double number is higher than "normal" numbers, e.g. 22 is higher than 53.
 // However, a 44, 55, 66 etc. are all higher than a 22 but the 22 would be higher than the 11. The highest "trump" number is 21,
 // called Mäxchen, which is a guaranteed win, as nothing can beat it except for another Mäxchen. Additionally, each players' result has to be higher than the
 // previous one's result or the same.
 //
 // The game of team 2 goes as follows:
 // One player shakes the dice, gets to see the result in secrecy, and then can either be honest or lie about the result before passing
 // it on to the next player. Afterwards, the player whom the dice were passed to can either choose to believe the previous player or doubt them.
 // By believing the game continues normally and the current player has to trump the believed value. In the doubt case, one of the players will lose -
 // either the doubter or the doubtee and the game told and facevalues are reset such that the doubter can start a new round with a shake.
 // Losing in Team2´s Mäxchen means that the losing player will have to drink a penalty shot and their Drunk-O-Meter, which shows a players "drunkness", rises
 // A shake typically has to be higher or the same as the passed on told value from the previous player. This also means if a Mäxchen is passed on, the current player still has a chance to win.
 //
 // This process repeats in a loop until one player is blacked out drunk (has a full Drunk-O-Meter) and "faints", ending the game. A pop-up notification will notify the player of this Game Over.
 //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


 /** About the MyGUI class:
 * The following class contains the main user interface of the game application, as well as the public static void main method to start and run the game.
 * The whole JavaDoc and commentary has been made by:
 * @author I5AB3LL (Isabell Post)
 */

 // References (Tutorials and Websites):
 // Plugin IntelliJ: "UI Designer"
 // https://www.youtube.com/watch?v=Kmgo00avvEw
 // https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html
 // https://www.java-tutorial.org/jprogressbar.html
 // https://www.javatpoint.com/java-actionlistener
 // https://www.w3schools.com/html/

 // References (Stackoverflow):
 // https://stackoverflow.com/questions/14316384/implementing-java-menubar-and-opening-new-window
 // https://stackoverflow.com/questions/56621362/how-to-change-window-size-according-to-screen-size-in-java

 // Sources:
 // All ButtonImages: Selfmade by
 // @author Isabell Post
 // FrameIcon SilverDice.jpg:
 // https://m2.dalvey.com/media/catalog/product/cache/2b036f0fc2b039d5b2b4726d04a72ea9/d/a/dalvey_dice--00615.jpg



public class MyGUI {

    private JButton shake = new JButton();
    private JButton endturn = new JButton();
    private JButton believe = new JButton();
    private JButton doubt = new JButton();
    private JButton truth = new JButton();
    private JButton lie = new JButton();

    /**
     * Creates Jprogressbars drunkmeter1 (for player 1) and drunkmeter 2 (For player 2)
     * @param 0 The starting value of the bar
     * @param GameService.amounTillPLayerFaints The maximum amount a player can drink
     */
    private JProgressBar drunkmeterp1 = new JProgressBar(0,GameService.amountTillPlayerFaints);
    private JProgressBar drunkmeterp2 = new JProgressBar(0,GameService.amountTillPlayerFaints);
    private JLabel des_barp1 = new JLabel();
    private JLabel des_barp2 = new JLabel();

    /**
     *  The triggerUIUpdate() method updates the UI by calling the respective method layout method during the appropriate gamephase.
     * It also updates the current Scores and checks whether the game has been won. If the game has been won a gameover window will open which closes the game when excited
     * See method updatesScores()
     * See method isWon()
     * See Class GameEndAlert
     */
    public void triggerUIUpdate()
    {
        System.out.println("Updating the UI...");

        switch (GameService.getInstance().currentGamePhase){
            case END_PHASE:
                setupEndPhaseLayout();
                break;
            case RECEIVE_PHASE:
                setupReceivePhaseLayout();
                break;
            case ROLL_DICE_PHASE:
                setupShakeLayout();
                break;
            case WAIT_FOR_END_ROUND:
                setupEndButton();
                break;
            default:
                break;

        }

        updateScores();

        if (GameService.getInstance().isWon() == true) {
            GameEndAlert alert = new GameEndAlert();
        }

    }

    /**
     * The UI contains progress bars (1 per player) called Drunkometers. These Drunkometers save and show the players losses through their "drunkness".
     * The more drunk a player is, the more they had to drink because everytime a player loses a penalty shot has to be drunk.
     *  The method updateScores() updates these losses and hence also the shown drunkometers of the UI.
     */
    private void updateScores(){
        //System.out.println(GameService.getInstance().playerA.getDrunkometer());
        //System.out.println(GameService.getInstance().playerB.getDrunkometer());
        drunkmeterp1.setValue(GameService.getInstance().playerA.getDrunkometer());
        drunkmeterp2.setValue(GameService.getInstance().playerB.getDrunkometer());
    }

    /**
     * The following methods (setupShakeLayout(), setupEndPhaseLayout(),setupEndButton(),setupReceivePhaseLayout()) set the appropriate Button layout in a UI update (disables or enables buttons) fitting to the current Game phase.
     * the setupShakeLayout() disables all buttons except for the shake button during the  ROLL_DICE_PHASE
     */
    private void setupShakeLayout(){
        shake.setEnabled(true);
        endturn.setEnabled(false);
        believe.setEnabled(false);
        doubt.setEnabled(false);
        truth.setEnabled(false);
        lie.setEnabled(false);
    }

    /**
     * the setupEndPhaseLayout() method disables all buttons expect for the lie button during the END_PHASE. The disabling of the truth button depends on the following:
     * If a player shakes a number that is below the current told value, then the truth cannot be told.
     * In this case, the truth button gets disabled and the player has no other choice but to lie.
     */
    private void setupEndPhaseLayout(){
        shake.setEnabled(false);
        endturn.setEnabled(false);
        believe.setEnabled(false);
        doubt.setEnabled(false);
        lie.setEnabled(true);

        Player active = GameService.getInstance().getActivePlayer();
        Player inactive = GameService.getInstance().getInActivePlayer();

        if (active.getFaceValue().compareTo(inactive.getToldValue()) == -1)
        {
            truth.setEnabled(false);
        } else {
            truth.setEnabled(true);
        }
        
    }

    /**
     *  the setupEndButton() method disables all buttons except for the end button for the WAIT_FOR_ROUND_END phase
     */
    private void setupEndButton(){
        shake.setEnabled(false);
        endturn.setEnabled(true);
        believe.setEnabled(false);
        doubt.setEnabled(false);
        truth.setEnabled(false);
        lie.setEnabled(false); 
    }

    /**
     *  the setupReceivePhaseLayout() method disables all buttons except for the believe and the doubt button for the RECEIVE_PHASE
     */
    private void setupReceivePhaseLayout(){
        shake.setEnabled(false);
        endturn.setEnabled(false);
        believe.setEnabled(true);
        doubt.setEnabled(true);
        truth.setEnabled(false);
        lie.setEnabled(false); 
    }

    /**
     * The constructor of the UI class. It sets and configures the UI. It is called in the run method of the game.
     * See method in the class MyGUI
     */
    public MyGUI() {
        //----------------------------------------------
        //The configuration and creation of the main frame can be found below

        JFrame frame = new JFrame();
        
        GameService.getInstance().registerUI(this);

        //This ensures that the window is closable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Here we set the title of the frame
        frame.setTitle("DrunkMäxchen");

        //Here we take the ScreenSize of the User and use it to adjust the frame size
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(dim.width/2, dim.height / 2); //sets the width and height of our frame, which is always half the size of the users screen

        //This is to prevent the User from resizing the window
        frame.setResizable(false);

        //Setting the frame icon
        ImageIcon frameicon = new ImageIcon("SilverDice.jpg"); //Weburl is available in the Copyright document
        frame.setIconImage(frameicon.getImage());

        //Sets the Background color of the frame
        frame.getContentPane().setBackground(new Color(137,153,179));
        //Sets the frame layout manager
        frame.setLayout(null);

        //----------------------------------------------
        //The configuration and creation of the MenuBar and its items for the Window can be found below

        JMenuBar mb = new JMenuBar();

        JMenu help_tab = new JMenu("Helper"); //This is the tab that the User can click on to access "Helping" features
        JMenu settings_tab = new JMenu("Customizer"); //This should allow the User to set the colour of the game background
        JMenuItem backgroundpicker = new JMenuItem("Background");
        JMenuItem Rules_item = new JMenuItem("Rules"); //This should allow the User to view a list of the Game rules through a new pop up Jframe
        JMenuItem Shot_roll = new JMenuItem("Shot Roller"); //This roller is a random shot generator that can help the User choose a shot to drink

        //Adding the help tab and its items to the menubar
        mb.add(help_tab);
        help_tab.add(Rules_item);
        help_tab.add(Shot_roll);

        /**
         * Opens a Rules Window to read Mäxchen instructions from
         * See RulesWindow class
         */
        Rules_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RulesWindow r = new RulesWindow();
            }
        });

        /**
         * Opens a JOption pane with a shot suggestion
         * See ShotRoller class
         */
        Shot_roll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShotRoller suggestion = new ShotRoller();
            }
        });


        mb.add(settings_tab);
        settings_tab.add(backgroundpicker);

        //This is a tab that can hold Menuitems that the User can access to customize the colours of the window e.g Backgroundcolour
        backgroundpicker.addActionListener(new ActionListener() { //This implements a cooler chooser, which is accessed when the backgroundpicker is clicked on by the player
            @Override
            public void actionPerformed(ActionEvent e) {
                JColorChooser chooser = new JColorChooser();
                Color back_colour = JColorChooser.showDialog(null, "Pick a colour for the background", Color.BLACK);
                frame.getContentPane().setBackground(back_colour);
            }
        });

        frame.setJMenuBar(mb); //Adds the MenuBar to the Jframe

        //----------------------------------------------
        //The configuration of all main buttons of the UI can be found below this statement

        ImageIcon shakeicon = new ImageIcon("Shakebutton.jpg");
        shake.setIcon(shakeicon);
        shake.setBounds(frame.getWidth()/4 + 30, frame.getHeight()/3 + 50, shakeicon.getIconWidth(),shakeicon.getIconHeight());
        shake.setBackground(Color.WHITE);

        /**
         * Opens a JOption Pane that displays a MaexchenDiceRoll result
         * See ShakeResult class
         * See MaexchenDiceRoll class (concerning the calculation of this result)
         */
        shake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MaexchenDiceRoll myRoll = GameService.getInstance().onPlayerShake(); //Here the onPlayerShake event is initiated through the GamerService class. It was at this point that we realized that we could only access this class from the GUI through an instance.
                if (myRoll != null) {
                    ShakeResult shakey = new ShakeResult(myRoll.getResult());
                } else {
                    throw new RuntimeException("This BUTTON should be DISABLED! <Shaker>");
                }
            }
        });

        ImageIcon endturnicon = new ImageIcon("EndTurnButton.jpg");
        endturn.setIcon(endturnicon);
        endturn.setBounds(frame.getWidth()-(shakeicon.getIconWidth()+50), frame.getHeight()/4*3, shakeicon.getIconWidth(),shakeicon.getIconHeight());
        endturn.setBackground(Color.WHITE);

        endturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GameService.getInstance().onEndTurnButton()){
                    throw new RuntimeException("This BUTTON should be DISABLED! <EndTurnButton>");
                }
            }
        });

        ImageIcon believicon = new ImageIcon("BelieveButton.jpg");
        believe.setIcon(believicon);
        believe.setBounds(frame.getWidth()/20, frame.getHeight()/16, believicon.getIconWidth(), believicon.getIconHeight());
        believe.setBackground(Color.WHITE);

        believe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GameService.getInstance().onBelieve()){
                    throw new RuntimeException("This BUTTON should be DISABLED! <Believe>");
                }
            }
        });

        ImageIcon doubticon = new ImageIcon("DoubtButton.jpg");
        doubt.setIcon(doubticon);
        doubt.setBounds(frame.getWidth()-(frame.getWidth()/2), frame.getHeight()/16, doubticon.getIconWidth(),doubticon.getIconHeight());
        doubt.setBackground(Color.WHITE);

        doubt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameService.DoubtResult doubtResult = GameService.getInstance().onPlayerDoubt();
                switch (doubtResult) {
                    case INVALID:
                        throw new RuntimeException("This BUTTON should be DISABLED! <Doubt>");
                    case WAS_RIGHT:
                        System.out.println("You were right to doubt the player! They were lying!");
                        break;
                    case WAS_WRONG:
                        System.out.println("You were wrong to doubt the player! They were telling the truth.");
                        break;
                    default:
                        break;

                }
            }
        });

        ImageIcon truthicon = new ImageIcon("truthbutton.jpg");
        truth.setIcon(truthicon);
        truth.setBounds(frame.getWidth()/20, frame.getHeight()/4*3, truthicon.getIconWidth(),truthicon.getIconHeight());
        truth.setBackground(Color.WHITE);

        truth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GameService.getInstance().onPlayerTruth()){
                    throw new RuntimeException("This BUTTON should be DISABLED! <Truth>");
                }
            }
        });

        ImageIcon lieicon = new ImageIcon("LieButton.jpg");
        lie.setIcon(lieicon);
        lie.setBounds(frame.getWidth()-(frame.getWidth()/2), frame.getHeight()/4*3, lieicon.getIconWidth(),lieicon.getIconHeight());
        lie.setBackground(Color.WHITE);

        /**
         * Opens the textfields for a lievalue input
         *
         * See the lier class
         */
        lie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lier lie = new Lier();
                if (!GameService.getInstance().onPlayerLie(lie.getLie())){
                    throw new RuntimeException("This BUTTON should be DISABLED! <Lie>");
                }
            }
        });

        frame.add(shake);
        frame.add(endturn);
        frame.add(believe);
        frame.add(doubt);
        frame.add(truth);
        frame.add(lie);

        //----------------------------------------------
        //Configures and adds the Drunkometers to the UI

        drunkmeterp1.setValue(0);
        drunkmeterp1.setBounds(frame.getWidth()-(250+30), frame.getHeight()/16, 250, 30);
        drunkmeterp1.setStringPainted(true);

        des_barp1.setBounds(frame.getWidth()-(drunkmeterp1.getWidth()+30),frame.getHeight()/16 - 30,250,30);
        String description1 = "<html><u><b>Drunk-O-Meter P1:</b></u></html>";
        des_barp1.setText(description1);
        des_barp1.setFont(new Font("Sans-serif", Font.PLAIN, 18));


        drunkmeterp2.setValue(0);
        drunkmeterp2.setBounds(frame.getWidth()-(250+30), frame.getHeight()/16 + 100, 250, 30);
        drunkmeterp2.setStringPainted(true);

        des_barp2.setBounds(frame.getWidth()-(drunkmeterp2.getWidth()+30),frame.getHeight()/16 +  70,250,30);
        String description2 = "<html><u><b>Drunk-O-Meter P2:</b></u></html>";
        des_barp2.setText(description2);
        des_barp2.setFont(new Font("Sans-serif", Font.PLAIN, 18));


        frame.add(des_barp1);
        frame.add(drunkmeterp1);
        frame.add(des_barp2);
        frame.add(drunkmeterp2);

        //----------------------------------------------
        this.triggerUIUpdate();
        frame.setVisible(true);
    }

    /**
     * The main method for the execution of the program
     * @param args
     */
    public static void main(String[] args){
        MyGUI gui = new MyGUI();

    }
}
