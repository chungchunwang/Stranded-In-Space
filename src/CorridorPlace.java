import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class CorridorPlace extends TGE_Place {
    public CorridorPlace() {
        super(); //call parent constructor
        this.name = "Corridor"; //set the name of the place
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is the corridor people go through to get to the cockpit. The floor is made of a brown carpet. On the floor lay 2-3 cardboard boxes, which you can open.");
            }
        });
        this.descriptions.add(new TGE_Description("carpet") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("The carpet seems to never have been cleaned. Closer inspection reveals that it is not a brown carpet but a very dirty white carpet.");
            }
        });
        this.descriptions.add(new TGE_Description("boxes") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("The boxes on the ground are brown and nondescript. They are scattered all around the floor - perhaps they fell here when the engine exploded.");
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        this.actions.add(new TGE_Action("open boxes", "Open the cardboard boxes.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("The boxes are completely empty.");
                return new ArrayList<>();
            }
        });
    }
}
