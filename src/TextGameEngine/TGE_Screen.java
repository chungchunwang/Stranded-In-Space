package TextGameEngine;

import java.util.Scanner;

/**
 * An object that represents a screen in the game.
 */
public class TGE_Screen {
    String text; //The text to be shown on the screen.

    /**
     * @param text The text to be shown on the screen.
     */
    public TGE_Screen(String text){
        this.text = text;
    }

    /**
     * Displays the screen.
     */
    public void display(){
        System.out.println(text);
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }
}
