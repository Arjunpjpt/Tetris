/*
 * TCSS 305
 * 
 * Assignment 6 - Tetris
 */
package view;

import java.awt.BorderLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import model.Board;

/**
 * This is a class to setup GUI of Tetris.
 * @author Arjun Prajapati
 * @version December 1, 2017
 */
public class TetrisGUI implements Observer {
    /** directory for the UW image. */
    public static final String ICON_DIRECTORY = "images/uw.png";
    
    /** to setup UW icon. */
    public static final Icon ICON = new ImageIcon(ICON_DIRECTORY);
    /**End Game class.*/
    protected EndGame myEndGameManually = new EndGame();
    
    /**Window frame.*/
    private JFrame myFrame;
    
//    private TetrisGUI myTetrisGUI = new TetrisGUI() ;
    /**Game Board.*/
    private GameBoardPanel myGameBoardPanel;
    
    /**Next Piece Panel.*/
    private NextPiecePanel myNextPiecePanel;
    
    /**Music Player class declaration.*/
    private MusicPlayer myMusicPlayer;
    
    /**Score Panel.*/
    private Score myScore;
    
    /////
    /**left key command to move tetris.*/
    private String myLeft = "a";
    /**right key command to move tetris.*/
    private String myRight = "d";
    /**up key command to rotate tetris.*/
    private String myRotate = "w";
    /**down key command to move tetris.*/
    private String myDown = "s";
    /**drop key command to move tetris.*/
    private String myDrop = "";
    
    /**pause Key.*/
    private String myPauseKey = "p";
   
    /**Text field width.*/
    private final int myTextFieldWidth = 5;
    
//  /**Declare panel for editing the keys JOption .*/
    /**Panel for editing key.*/
    private JPanel myPanel;

    /** for Jmenu item of file.*/
    private final JMenuItem myPauseGame;
    
    /** for Jmenu item of file.*/
    private final JMenuItem myEndGameFileMenu;
    
    /**Declaring to ControlButtons class.*/
    private ControlButtons myControlButtons;
    
    /**Declaring GameStatus class.*/
    private GameStatus myGameStatus;
    
    /**Reference to the Board class.*/
    private Board myBoard;
    
    /**To know the timeState.*/
    private Boolean myTimeState;
    
    /**RDeclaring timer.*/
    private Timer myTime;

    /**Game Processing status.*/
    private boolean myGameProgress;
    
    /**Declaring reference for the PausePlayGame class.*/
    private PausePlayGame myPausePlayButton;
    
     /**Timer time for 1 second.*/
    private final int myTimerTime = 1000;
    
    /**ArrayList to store the key controls.*/
    private ArrayList<String> myControlList = new ArrayList<String>();
    
    /**Final score to display.*/
    private int myFinalScore;
    
    /**For the end game by user.*/
    private boolean myEndGame;
    

    /**
     * This is a default constructor.
     */
    public TetrisGUI() {
        myPauseGame = new JMenuItem("");
        myEndGameFileMenu = new JMenuItem("End Game");
        createAndShowGUI();
    }
     
    /**
     * this method update the score.
     * @param theScore ,  current score
     */
    public void setScore(final int theScore) {
//        System.out.println("from Tetris   GUI" + theScore);
        myFinalScore = theScore;
    }
    
    /**
     * This method returns the current score.
     * @return int
     */
    public int getScore() {
        return myFinalScore;
    }
    
     
    /**
     * Get time Start value.
     * @return boolean , timeState
     */
    public boolean getTimeStart() {
        return myTimeState;
    }
    
    /**
     * Set time Start value.
     * @param theState , true or false
     */
    public void  setTimeStart(final boolean theState) {
        myTimeState = theState;
    }
    
    
    /**
     * ToPlay Theme Music.
     */
    public void themeMusic() {
        myMusicPlayer.play("myTheme");
    }
    /**
     * This method to setup a frame.
     */
    public void createAndShowGUI() {
//        myKeyControla = new KeyControl(myBoard);
        myFrame = new JFrame("Tetris");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setFocusable(true);
        myFrame.requestFocus();
        myFrame.addKeyListener(new KeyControlListener());
        
//        myKeyControl.setLeftKey("a");
        myMusicPlayer = new MusicPlayer();
        themeMusic();
//        myMusicPlayer.display();
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildFileMenu());
        menuBar.add(buildControlKeyMenu());
        menuBar.add(buildHelpMenu());
        
        myFrame.setJMenuBar(menuBar);
        
        myGameBoardPanel = new GameBoardPanel(this);
        myFrame.add(myGameBoardPanel, BorderLayout.CENTER);
            
        myNextPiecePanel = new NextPiecePanel();
        myGameStatus = new GameStatus(this);
        myScore = new Score(this);
//        myFrame.add(myNextPiecePanel, BorderLayout.EAST);

        
        myBoard = new Board();
        
        final Box sideContainerBox = new Box(BoxLayout.PAGE_AXIS);
        myFrame.add(sideContainerBox, BorderLayout.EAST);
//        sideContainerBox
        myControlButtons = new ControlButtons(this);
        sideContainerBox.add(myNextPiecePanel);
        sideContainerBox.add(myControlButtons);
        sideContainerBox.add(myScore);
        sideContainerBox.add(myGameStatus);
        

        myBoard.addObserver(myGameBoardPanel);
        myBoard.addObserver(myNextPiecePanel);
        myBoard.addObserver(myScore);
        myBoard.addObserver(myControlButtons);
        myBoard.addObserver(myGameStatus);
        
        
        myBoard.newGame();
        
        myFrame.setResizable(true);
        myFrame.pack();
        myFrame.setVisible(true);

        
        myTime = new Timer(myTimerTime, new TimeAction());
        
        myTime.start();
        myTimeState = true;
//        new EditKeyButton(myTime,pauseGame );
        myGameProgress = true;
        gameStatus();
        addListKeys();
    }
    /**
     * This method add the keys in a list.
     */
    public void addListKeys() {
        
        myControlList.add(myLeft);
        myControlList.add(myRight);
        myControlList.add(myRotate);
        myControlList.add(myDown);
        myControlList.add("P");
    }
    /**
     * This method returns timer.
     * @return Timer
     */
    public Timer getMyTime() {
        return myTime;
    }
    /**
     * get the left move key.
     * @return String, the left keyword
     */
    public String getLeftKey() {
        return myLeft;
    }
    
    /**
     * get the right move key.
     * @return String, the right keyword
     */
    public String getRightKey() {
        return myRight;
    }
    
    /**
     * get the rotate move key.
     * @return String, the rotate keyword
     */
    public String getRotateKey() {
        return myRotate;
    }
    
    /**
     * get the left move key.
     * @return String, the down keyword
     */
    public String getDownKey() {
        return myDown;
    }
    
    /**
     * get the drop move key.
     * @return String, the drop keyword.
     */
    public String getDropKey() {
        return myDrop;
    }
    
    /**
     * this method is to return if game is paused.
     */
    public void gameStatus() {
        if (myGameProgress) {
            myGameStatus.myMessageLabel().setText("Game On Process");
            
        } else {
            myGameStatus.myMessageLabel().setText("Game Paused");
        }
    }
    /**
     * This method is to print game over.
     */
    public void gameOver() {
        myGameStatus.myMessageLabel().setText("Game Over");
        myMusicPlayer.getAudioClip().stop();

        myMusicPlayer.play("myGameOver");

    }
    
    /**
     * music played after line is cleared.
     */
    public void lineClear() {
        myMusicPlayer.getAudioClip().stop();
        myMusicPlayer.play("myClear");
        themeMusic();
    }
    /**
     * music played when level is up.
     */
    public void levelUp() {
        myMusicPlayer.getAudioClip().stop();
        myMusicPlayer.play("myLevel");
        themeMusic();
    }
    /**
     * Create Control Key Menu.
     * @return JMenu
     */
    private JMenu buildControlKeyMenu() {
//        EditKeyButton editKeyBtn = new EditKeyButton();
        final JMenu controlMenu = new JMenu("Edit Key");
        controlMenu.setMnemonic(KeyEvent.VK_E);
        
        final JMenuItem editControlKey = new JMenuItem("Edit Control Key");
        editControlKey.setMnemonic(KeyEvent.VK_C);
        editControlKey.addActionListener(new ActionListener() {
//            private ControlButtons myBtnControlAction = new ControlButtons(this);
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
//                myTime.stop();
                myGameProgress = false;
                gameStatus();
                myPausePlayButton.gameAction();
                /////////
                ////
                /////
                modifyControlKeys();
                
            }
            
        });
        controlMenu.add(editControlKey);
        return controlMenu;
    }
    
    /**
     * Method to modify the buttons.
     */
    public void modifyControlKeys() {
        SwingUtilities.invokeLater(() -> {
            final JTextField leftKey = new JTextField(myTextFieldWidth);
            leftKey.setDocument(new JTextFieldLimit(1));
            leftKey.setText(getLeftKey());
            
            final JTextField rightKey = new JTextField(myTextFieldWidth);
            rightKey.setDocument(new JTextFieldLimit(1));
            rightKey.setText(getRightKey());
            
            final JTextField upKey = new JTextField(myTextFieldWidth);
            upKey.setDocument(new JTextFieldLimit(1));
            upKey.setText(getRotateKey());

            final JTextField downKey = new JTextField(myTextFieldWidth);
            downKey.setDocument(new JTextFieldLimit(1));
            downKey.setText(getDownKey());

            final JButton btnOk = new JButton("Update");
            leftKey.addActionListener(e -> {
                final KeyboardFocusManager manager 
                    = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
                
                
            });

            myPanel = new JPanel();
            myPanel.add(new JLabel("Left Key:"));
            myPanel.add(leftKey);
            myPanel.add(new JLabel("Right Key:"));
            myPanel.add(rightKey);

            myPanel.add(new JLabel("Rotate Key:"));
            myPanel.add(upKey);

            myPanel.add(new JLabel("Down Key:"));
            myPanel.add(downKey);
            myPanel.add(btnOk);

            btnOk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                    if (!checkDuplicate(leftKey.getText())) {
                        myLeft = leftKey.getText();
                        myRight = rightKey.getText();
                        myRotate = upKey.getText();
                        myDown = downKey.getText();
                        myControlButtons.leftLabel().setText("Left Move:Left Key / " + myLeft);
                        myControlButtons.rightLabel().setText("Right Move : Right Key or " 
                                    + myRight);
                        myControlButtons.rotateLabel().setText("Rotate Move : Up Key / " 
                                    + myRotate);
                        myControlButtons.downKeyLabel().setText("Down Move : Down Key or " 
                                    + myDown);
                    } else {
                        duplicateMessage();
                    }
                }
            });


            final String prompt = "Please Give Unique Key";
            final Object[] options = {"Close"};
            JOptionPane.showOptionDialog(null, myPanel, prompt,
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    options,
                    options[0]);
        });
        
    }
    /**
     * This method is to give a duplicate key message.
     */
    public void duplicateMessage() {
        JOptionPane.showMessageDialog(null, "No Duplicate Keys. "
                        + "Please give a Unique Keys for move."
                                      , "Error", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * this method to check if there is duplicate keys.
     * @param theString , string to check the duplicate
     * @return boolean
     */
    public boolean checkDuplicate(final String theString) {
        for (int i = 0; i < myControlList.size(); i++) {
            if (myControlList.get(i).toUpperCase().equals(theString) 
                            || myControlList.get(i).toLowerCase().equals(theString)) {
                return true;
            }
            if (myPauseKey.toUpperCase().equals(theString)
                || myPauseKey.toLowerCase().equals(theString)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * method to setup the help menu.
     * @return JMenu
     */
    private JMenu buildHelpMenu() {
        
        // create a menu and a menu item object
        final JMenu helpMenu = new JMenu("Help");  
        helpMenu.setMnemonic(KeyEvent.VK_H);
        
        final JMenuItem aboutButton = new JMenuItem("About...");
        aboutButton.setMnemonic(KeyEvent.VK_A);
        helpMenu.add(aboutButton);
        
        
        aboutButton.addActionListener(new ActionListener() {
           
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null, "Arjun Prajapati\nAutumn 2017\nTCSS "
                            + "305 Tetris", "About",
                                             JOptionPane.INFORMATION_MESSAGE, ICON);
            }
        });
        
        return helpMenu;
    }
    /**
     * 
     * This is the helping method of the new game.
     */
    public void newGameHelpingMethod() {
        myFinalScore = 0;
        myScore.clear();
        myPauseGame.setText(pauseString());
        setTimeStart(true);
        myEndGame = false;
    }
    /**
     * Create file Menu.
     * @return JMenu
     */
    private JMenu buildFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        final JMenuItem newGame = new JMenuItem("New Game");
        newGame.setMnemonic(KeyEvent.VK_N);
        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myScore.setCurrentScore(0);
                myBoard.newGame();
                myMusicPlayer.getAudioClip().stop();
                themeMusic();
                myBoard.addObserver(myGameBoardPanel);
                myGameProgress = true;
                gameStatus();
                myTime.start();
                newGameHelpingMethod();
            }
            
        });
        
        myEndGameFileMenu.setMnemonic(KeyEvent.VK_E);
        myEndGameFileMenu.addActionListener(myEndGameManually);

        
        myPauseGame.setText(pauseString());
        myPauseGame.setMnemonic(KeyEvent.VK_P);
        myPausePlayButton = new PausePlayGame();
        myPauseGame.addActionListener(myPausePlayButton);
     
        fileMenu.add(newGame);
        fileMenu.add(myEndGameFileMenu);
        fileMenu.add(myPauseGame);

        return fileMenu;
    }
    /**
     * this method return pause to change the label.
     * @return String
     */
    public String pauseString() {
        return "Pause";
    }
    
    @Override
    public void update(final Observable theObject, final Object theArg) {


    }
    /**
     * Key Lister class to move the Tetris object.
     * @author arjun
     *
     */
    public class KeyControlListener implements KeyListener {
        @Override
        public void keyTyped(final KeyEvent theEvent) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void keyPressed(final KeyEvent theEvent) {
            //storing Key Code value.
            
            final int keyCodeRight = 39;
            
            
            final char keys = theEvent.getKeyChar();
            final String st = Character.toString(keys);
            
            if (myPauseKey.toLowerCase().equals(st) 
                            || myPauseKey.toUpperCase().equals(st)) {
                myPausePlayButton.gameAction();
            }
            if (!myEndGame) {
                if (myGameProgress) {
                    if ((theEvent.getKeyCode() == keyCodeRight) 
                            || (st.equals(getRightKey().toLowerCase())) 
                            || (st.equals(getRightKey().toUpperCase()))) {
                        myBoard.right();
                    } else {
                        keyAction(theEvent, st);
            
                    }
                }
            }
        }
        /**
         * method to choose keyAction.
         * @param theEvent , event of key
         * @param theString , keycode to string converted
         */
        public void keyAction(final KeyEvent theEvent, final String theString) {
            final int keyCodeDown = 40;
            final int keyCodeSpaceBar = 32;
            final int keyCodeLeft = 37;
            
           
            if ((theEvent.getKeyCode() == keyCodeLeft) 
                            || (theString.equals(getLeftKey().toLowerCase())) 
                            || (theString.equals(getLeftKey().toUpperCase()))) {
                myBoard.left();
            } else if ((theEvent.getKeyCode() == keyCodeDown) 
                            || (theString.equals(getDownKey().toLowerCase())) 
                            || (theString.equals(getDownKey().toUpperCase()))) {
                myBoard.down();
            } else if (theEvent.getKeyCode() == keyCodeSpaceBar) {
                myBoard.drop();
            } else {
                rotateKeyAction(theEvent, theString);
            }
            
           
        }
        /**
         * method to choose keyAction.
         * @param theEvent , event of key
         * @param theString , keycode to string converted
         */
        public void rotateKeyAction(final KeyEvent theEvent, final String theString) {
            final int keyCodeUp = 38;
            if ((theEvent.getKeyCode() == keyCodeUp) 
                            || (theString.equals(getRotateKey().toLowerCase())) 
                            || (theString.equals(getRotateKey().toUpperCase()))) {
                myBoard.rotateCW();
            }
            
        }
        
        @Override
        public void keyReleased(final KeyEvent theEvent) {
            
        }
        
    }
    /**
     * This class is to end a game.
     * @author arjun
     *
     */
    public class EndGame implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            // TODO Auto-generated method stub
            myTime.stop();
            myBoard.deleteObserver(myGameBoardPanel);
            myEndGame = true;
            gameOver();
//            myMusicPlayer.getAudioClip().stop();
            JOptionPane.showMessageDialog(null, "Game Over, Final Score is : " 
                            + getScore());
//            myEndGameFileMenu.
            
            
        }
        
    }
    /**
     * Pause/Play game.
     */
    public class PausePlayGame implements ActionListener {
        
//        TetrisGUI myTimeGui;
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            gameAction();
        }
        /**
         * this method is to play/pause game.
         */
        public void gameAction() {
           
            if (!myEndGame) {
                if (getTimeStart()) {
                    myMusicPlayer.getAudioClip().stop();
                    myMusicPlayer.play("myPause");
//                    myMusicPlayer.getAudioClip().stop();
                    myGameProgress = false;
                    gameStatus();
                    myBoard.deleteObserver(myGameBoardPanel);
                    myPauseGame.setText("Play");
                    myTime.stop();
                    setTimeStart(false);
                } else { 
                    myMusicPlayer.getAudioClip().stop();
                    themeMusic();
                    myTime.start(); 
                    myGameProgress = true;
                    gameStatus();
                    myBoard.addObserver(myGameBoardPanel);
                    myPauseGame.setText(pauseString());
                    setTimeStart(true);
                }
            
            }
        }
    }
   /**
     * @author arjun
     *
     */
    class TimeAction implements ActionListener {
        
        /**
         * actionlister for the time.
         * @param theEvent , event of a time
         */
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.step();
        }
        
    }


    /**
     * this class is to limit the maximum input for jtextfield.
     * @author Arjun
     * 
     */
    public final class JTextFieldLimit extends PlainDocument {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        /**Maximum character for JField.*/
        private int myLimit;
        
        /**
         * Consttructor of JTextFieldLimit.
         * @param theLimit , integer value.
         */
        private JTextFieldLimit(final int theLimit) {
          super();
            this.myLimit = theLimit;
        }

        /**
         * method to update JField property.
         * @param theOffset
         * @param theStr
         * @param theAttr
         */
        @Override
        public void insertString(final int theOffset, 
                                 final String theStr, final AttributeSet theAttr) 
                        throws BadLocationException {
            if (theStr == null) {
                return;
            }

            if ((getLength() + theStr.length()) <= myLimit) {
                super.insertString(theOffset, theStr, theAttr);
            }
        }
    }
}
