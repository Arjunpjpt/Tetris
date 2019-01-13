/*
 * TCSS 305
 * 
 * Assignment 6 - Tetris
 */
package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is to return random color to built a tetris..
* @author Arjun Prajapati
 * @version December 1, 2017
 *
 */
public class GetColor {
    /**List to store the color.*/
    private ArrayList<Color> myColorList = new ArrayList<Color>();
    /**
     * default constructor to add colors in a list.
     */
    public GetColor() {
        myColorList.add(Color.RED);
        myColorList.add(Color.BLACK);
        myColorList.add(Color.BLUE);
        myColorList.add(Color.MAGENTA);
        myColorList.add(Color.PINK);
        myColorList.add(Color.CYAN);
        myColorList.add(Color.YELLOW);
        myColorList.add(Color.GREEN);
        myColorList.add(Color.WHITE);
        myColorList.add(Color.ORANGE);
//        Color col = Color.
    }
   
    /**
     * this color pass the random color from the list.
     * @return Color
     */
    public Color passColor() {
        final Color random =  (Color) myColorList.get(new Random().nextInt
                                                      (myColorList.size()));
        return random;
    }

}
