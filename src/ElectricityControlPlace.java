import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class ElectricityControlPlace extends TGE_Place {
    boolean pulled = false; ////State of the place: whether a lever has been pulled
    public ElectricityControlPlace() {
        super(); //calls the parent constructor
        this.name = "Electricity Control"; //sets the name of the place
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious. Comments will be included for descriptions that vary based on state.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("Here is where the electric network of the spaceship is controlled. There is a dashboard, and you can use it to restore the ship's electricity.");
            }
        });
        this.descriptions.add(new TGE_Description("dashboard") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("There are a bunch of levers that control the electricity to each part of the ship. You notice that the switch for the main power supply was turned off due to a surge in energy.");
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        this.actions.add(new TGE_Action("restore electricity", "Restore electricity in the spaceship.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(pulled) { //No need to pull the lever if electricity has already been restored.
                    System.out.println("Electricity has already been restored.");
                    return new ArrayList<>();
                }
                System.out.println("You can choose to pull one of 2 levers, the red lever or the blue lever.");
                ArrayList<TGE_Action> returnActions = new ArrayList<>();
                //add 2 choices, to pull the red lever or the blue lever
                returnActions.add(new TGE_Action("red lever", "Pull the red lever.") {
                    @Override
                    public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                        pulled = true; //set lever pulled to true - for consistency but technically not necessary since the game is about to end.
                        endGame.call(1); //end the game with screen 1 - see Main.java to see said screen
                        return new ArrayList<>();
                    }
                });
                returnActions.add(new TGE_Action("blue lever", "Pull the blue lever.") {
                    @Override
                    public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                        pulled = true;//set lever pulled to true
                        System.out.println("Electricity was restored. A burnt fuse was ejected and a new one was automatically inserted. You take the burnt fuse, as it might be useful.");
                        items.put("burnt fuse", 1); //put burnt fuse in items
                        return new ArrayList<>();
                    }
                });
                return returnActions;
            }
        });
    }
}
