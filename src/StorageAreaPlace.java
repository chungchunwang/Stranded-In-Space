import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class StorageAreaPlace extends TGE_Place {
    boolean hasKeys = true; //State of the place: Whether there are still keys in the box.
    boolean hasRemembered = false; //State of the place: Whether the number on the cassette has already been remembered.
    public StorageAreaPlace() {
        this.name = "Storage Area"; //Set the name of the place.
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious. Comments will be included for descriptions that vary based on state.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is where you store things you don't need. There is a table with a box and a cassette on it. You might find useful things in this area.");
            }
        });
        this.descriptions.add(new TGE_Description("cassette") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This cassette is placed on a table. There is a number on it: 4738");
            }
        });
        this.descriptions.add(new TGE_Description("box") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(hasKeys) System.out.println("Inside there are 4 keys of different colors inside the box. You should probably take them just in case they are useful for opening certain locks.");
                else System.out.println("There is nothing inside the box."); //Varies the contents of the box based on whether there are still keys in it.
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        this.actions.add(new TGE_Action("memorize number", "Memorize the number on the cassette.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if (hasRemembered) System.out.println("You already memorized the number."); //Cancel the action if the player has already remembered the number
                else {
                    items.merge("memory of cassette number", 1, Integer::sum); //Add the memory of the number as an item
                    System.out.println("You memorized the number.");
                }
                return new ArrayList<>();
            }
        });
        this.actions.add(new TGE_Action("take keys", "Take the keys from the box.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(hasKeys){ //if the keys are present, add them to the players items and then update state to reflect that they have been taken.
                    System.out.println("You took the keys.");
                    items.merge("blue key", 1, Integer::sum);
                    items.merge("red key", 1, Integer::sum);
                    items.merge("green key", 1, Integer::sum);
                    items.merge("yellow key", 1, Integer::sum);
                    hasKeys = false;
                }
                else System.out.println("You have already taken the keys.");//if they have been taken, prompt the player.
                return new ArrayList<>();
            }
        });
    }
}
