/*
 * TCSS 305
 * 
 * Assignment 6 - Tetris
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * This class is to give game update.
 * @author Arjun Prajapati
 * @version December 1, 2017
 */
public class GameStatus extends JPanel implements Observer {
    /**
     * default serial id.
     */
    private static final long serialVersionUID = 1L;

    /**Size of the Panel.*/
    private static final Dimension SIZE = new Dimension(200, 50);

    /**JLabel message.*/
    private JLabel myMessage;
    
    /**
     * This is the constructor to initialize.
     * @param theTetrisGUI , tetris class reference
     */
    public GameStatus(final TetrisGUI theTetrisGUI) {
        super();
        final TitledBorder border = new TitledBorder("Game Update");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        setBorder(border);
        
        setBackground(Color.WHITE);
        setPreferredSize(SIZE);
        
        addComponent();
    }
    
    /**
     * Add component for panel.
     */
    public void addComponent() {
        myMessage = new JLabel();
        add(myMessage);
    }
    
    /**
     * This method returns myMessage label.
     * @return JLabel , label for game update.
     */
    public JLabel myMessageLabel() {
        return myMessage;
    }
    @Override
    public void update(final Observable theO, final Object theArg) {
        // TODO Auto-generated method stub
        
    }
    

}
