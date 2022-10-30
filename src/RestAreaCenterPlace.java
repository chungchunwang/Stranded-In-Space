import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class RestAreaCenterPlace extends TGE_Place {
    public RestAreaCenterPlace() {
        this.name = "Center of Rest Area"; //Name of the place
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is where the crew-mates go to relax. It is an open space with only chairs where they can play games and socialize.");
            }
        });
        this.descriptions.add(new TGE_Description("chairs") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("There are a lot of chair scattered around the place. They are usually placed orderly, but they must have moved when the engine exploded.");
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        this.actions.add(new TGE_Action("rest", "Take a rest.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("You can't, there is no time!");
                return new ArrayList<>();
            }
        });
    }
}
