/*
 * TCSS 305
 * 
 * Assignment 6 - Tetris
 */
package view;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This is a main class of tetris to run the program.
 * @author Arjun Prajapati
 * @version December 1, 2017
 * 
 */
public final class TetrisMain {
    /**
     * default constructor.
     */
    private TetrisMain() {
        
    }
    /**
     * This is a main method to call the TetrisGUI class.
     * @param theArgs , command line argument.
     */
    public static void main(final String[] theArgs) {
        /* 
         * Use an appropriate Look and Feel 
         * https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         * 
         */
        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            private PowerPaintGui myGui = new PowerPaintGui();
            public void run() {
                new TetrisGUI();
            }
        });
    }
}
