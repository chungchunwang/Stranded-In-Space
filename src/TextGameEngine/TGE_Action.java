package TextGameEngine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An action that can be performed at a location.
 */
public abstract class TGE_Action {
    String command; //The command to trigger the action.
    String description; //A description of what the action does.

    /**
     * @param items The items a player has.
     * @param endGame An object with a run function to end the game.
     * @return
     */
    public abstract ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame);

    /**
     * @return a "stringifyed" version of this object. This is used when displaying the list of available commands.
     */
    @Override
    public String toString() {
        String filler = "";
        for (int i = 0; i< 25 - command.length();i++) filler += " ";
        return "|Action\t\t\t|"+command +filler+"|" + description;
    }

    /**
     * @param command The command to trigger the action.
     * @param description A description of what the action does.
     */
    public TGE_Action(String command, String description){
        this.command = command;
        this.description = description;
    }
}
