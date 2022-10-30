package TextGameEngine;

import java.util.ArrayList;

/**
 * A place on the grid in the game.
 */
public abstract class TGE_Place {
    public String name; //The name of the place.
    public ArrayList<TGE_Description> descriptions; //A list of all the descriptions present in the place.
    public ArrayList<TGE_Action> actions; //A list of all the actions available in a place.

    /**
     * @param name The name of the place.
     * @param descriptions A list of all the descriptions present in the place.
     * @param actions A list of all the actions available in a place.
     */
    public TGE_Place(String name, ArrayList<TGE_Description> descriptions, ArrayList<TGE_Action> actions){
        this.name = name;
        this.descriptions = descriptions;
        this.actions = actions;
    }

    /**
     * Default Constructor.
     */
    public TGE_Place(){
        actions = new ArrayList<>();
        descriptions = new ArrayList<>();
    }
}
