import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class DormitoryCenterPlace extends TGE_Place {
    //State of place, names self-explanatory
    static boolean hasWire = true;
    static boolean hasPillow = true;
    public DormitoryCenterPlace() {
        super(); //calls parent constructor
        this.name = "Center of Dormitory"; //set the name of the place
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious. Comments will be included for descriptions that vary based on state.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is where you sleep. The bed is messy as you woke up to sirens about the engine failure. There is a sign hanging from the ceiling. " + (hasWire?"On the floor you notice a familiar wire.": "")); //Adds a bit of description if the wire is present.
            }
        });
        this.descriptions.add(new TGE_Description("wire") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(hasWire) System.out.println("It is a red wire that with the word “engine” on it. It seems to be a part of the damaged engine."); //varies description based on whether the wire is present.
                else System.out.println("There used to be a red wire here, but you took it.");
            }
        });
        this.descriptions.add(new TGE_Description("bed") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("Your bed is really messy. The blanket has fallen to the ground"+(hasPillow?" and your pillow has been kicked under the bed":"")+"."); //Add description if pillow is on the ground.
            }
        });
        this.descriptions.add(new TGE_Description("ceiling") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(items.get("burnt fuse") != null && items.get("burnt fuse") > 0) System.out.println("The flashing \"Electricity Failure \" sign is now off."); //varies description based on whether the electricity has been fixed in the spaceship (player gets a burnt fuse if it is fixed.)
                else System.out.println("There is a flashing sign with \"Electricity Failure\" written on it. It seems that the power system has short-circuited.");
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        this.actions.add(new TGE_Action("take wire", "Take the wire on the ground.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(hasWire){ //if wire still present, add the wire to items and then update state so that it is not present
                    System.out.println("The wire has been put in your bag.");
                    items.merge("wire", 1, Integer::sum);
                    hasWire = false;
                }
                else System.out.println("The wire has already been taken."); //tells the user the wire is not present
                return new ArrayList<>();
            }
        });
        this.actions.add(new TGE_Action("take pillow", "You don’t known whether this will be useful, but it might be nice to have.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(hasPillow){//if pillow is present, add it to items and then update state so that it is no longer present
                    System.out.println("The pillow has been put in your bag.");
                    items.merge("pillow", 1, Integer::sum);
                    hasPillow = false;
                }
                else System.out.println("The pillow has already been taken.");//tells the user the pillow is no longer present
                return new ArrayList<>();
            }
        });
    }
}
