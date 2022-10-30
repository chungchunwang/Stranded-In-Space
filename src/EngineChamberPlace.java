import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class EngineChamberPlace extends TGE_Place {
    boolean hasPieces = true; //State of place: whether the engine pieces are still present
    public EngineChamberPlace() {
        super();//call parent constructor
        this.name = "Engine Chamber";//set the name of the place
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious. Comments will be included for descriptions that vary based on state.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is where the heart of the spaceship - the engine - was held. "+ (hasPieces?"All around you you see broken engine remains that you can take.":"")); //Add description of pieces if present.
            }
        });
        this.descriptions.add(new TGE_Description("engine pieces") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(hasPieces) System.out.println("There are pieces of the old engine scattered all over the ground. Most of them are chunks of carbon-fiber and steel. If you picked them up, they might be useful in constructing the new engine."); //vary description of pieces based on whether they are present.
                else System.out.println("There used to be engine pieces on the ground, but you picked them up.");
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        this.actions.add(new TGE_Action("pick pieces", "Pick up the engine pieces.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(hasPieces){ //add pieces to inventory if they are still present
                    System.out.println("You picked up the pieces.");
                    items.put("engine pieces", 1);
                    hasPieces = false; //update state
                }
                else System.out.println("You have already picked up the pieces.");
                return new ArrayList<>();
            }
        });
        this.actions.add(new TGE_Action("rebuild engine", "Rebuild the broken engine!") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                boolean pass = true; //whether the player has all the items needed to restore the engine
                String[] itemsNeeded = { //items needed to restore engine
                        "burnt fuse",
                        "engine pieces",
                        "welding machine",
                        "charging adaptor",
                        "engine plan",
                        "phone fragments",
                        "button",
                        "wire",
                        "detergent",
                        "connecting wire",
                        "tire",
                        "engine grease"
                };
                String stillNeededItems = "|| "; //items that the player still needs
                for (String itemNeeded : itemsNeeded) { //loop through all needed items to see if the user has them. If not, do not allow them to pass and add that item to the items needed list.
                    if(items.getOrDefault(itemNeeded, 0)<=0){
                        pass = false;
                        stillNeededItems += itemNeeded + " || ";
                    }
                }
                if(pass){ //if the items all pass, end the game with the win screen.
                    endGame.call(2);
                }
                else{ //otherwise update the user with what items they still need.
                    System.out.println("You don't have all the require items for the engine.");
                    System.out.println("You still need: ");
                    System.out.println(stillNeededItems);
                }
                return new ArrayList<>();
            }
        });
    }
}
