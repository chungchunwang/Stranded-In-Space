package TextGameEngine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An object to represent a description in a place.
 */
public abstract class TGE_Description {
    String command; //the command to trigger the description

    /**
     * @param command The command used to display the description.
     */
    public TGE_Description(String command){
        this.command = command;
    }
    public abstract void run(HashMap<String, Integer> items, TGE_F_EndGame endGame); //function to print the description to the console

    /**
     * @return a "stringifyed" version of this object. This is used when displaying the list of available commands.
     */
    @Override
    public String toString() {
        String filler = "";
        for (int i = 0; i< 25 - command.length()-5;i++) filler += " ";
        if (command.length() == 0) return "|Description\t|"+"look                     |" + "Look around.";
        return "|Description\t|"+"look "+command +filler +"|" + "Look at the "+command+".";
    }
}
