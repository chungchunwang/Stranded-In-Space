import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class DormitoryTopPlace extends TGE_Place {
    //State of place, names self-explanatory
    boolean hasConnectingWire = true;
    boolean lampOn = false;
    public DormitoryTopPlace() {
        super(); //calls the parent constructor
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious. Comments will be included for descriptions that vary based on state.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is where you work. There is a table with a lamp on it. Underneath the table there is a cabinet.");
            }
        });
        this.descriptions.add(new TGE_Description("cabinet") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if (hasConnectingWire) System.out.println("There is a connecting wire in here, it seems you can use it to fix the damaged engine."); //varies description based on whether the connecting wire is present
                else System.out.println("There used to be a connecting wire here, but you took it.");
            }
        });
        this.descriptions.add(new TGE_Description("lamp") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("The lamp is "+(lampOn?"on":"off")+"."); //Changes the description to reflect whether the light is on or off.
            }
        });
        this.descriptions.add(new TGE_Description("table") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("There is a small container and a big container on the table, one for putting pencils in and the other for paper. Using these tools you should be able to draw here.");
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        this.actions.add(new TGE_Action("take conn wire", "Take the connecting wire in the cabinet.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(hasConnectingWire){ //if connecting wire is present, add it to items, then update state to reflect that it has been taken.
                    System.out.println("The connecting wire has been put in your bag.");
                    items.merge("connecting wire", 1, Integer::sum);
                    hasConnectingWire = false;
                }
                else System.out.println("You have already taken the wire here."); //if connecting wire is not present, tell the player.
                return new ArrayList<>();
            }
        });
        this.actions.add(new TGE_Action("open lamp", "Turn on the lamp.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(items.get("burnt fuse") != null && items.get("burnt fuse") > 0){ //can only turn on the light if there is electricity. There is only electricity if the player has a burnt fuse.
                    lampOn = true; //update state
                    System.out.println("You turned the lamp on.");
                }
                else System.out.println("There is no electricity, you can't turn it on."); //tell the player they need to turn on electricity
                return new ArrayList<>();
            }
        });
        this.actions.add(new TGE_Action("draw plan", "Draw a sketch of how you will fix the engine on the table.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if (lampOn){
                    if(items.get("pencil and paper") != null && items.get("pencil and paper") > 0){ //A plan can only be drawn if the lamp is on and the player has pencil and paper.
                        System.out.println("You drew a simple plan for how your new engine will look. You used up your pencil and paper. You put your plan in your bag.");
                        System.out.println("""
                                According to your plan, this is everything you need:
                                engine plan - the plan itself
                                burnt fuse - when sprayed with detergent can become a workable fuse for the engine
                                detergent - to make the burnt fuse functional
                                engine pieces - to form the main shell of the new engine
                                welding machine - to fuse the pieces of the engine together
                                charging adaptor - to provide energy to the engine on startup
                                phone fragments - use the phone screen for an interactive display
                                button - to press to start the engine
                                wire - to connect the electronics in the engine
                                connecting wire - specialized type of wire that facilitates the nuclear fusion engine
                                tire - to wrap around the entire engine as casing
                                engine grease - to make sure all the parts of the engine move smoothly                          
                                """);
                        items.remove("pencil and paper"); //remove pencil and paper from player's inventory
                        items.put("engine plan", 1); //add the plan to the player's inventory
                    }
                    else System.out.println("You need a pencil and paper to draw. Maybe you could find them somewhere."); //Tell the player they are missing paper and pencil
                }
                else {
                    System.out.println("It is too dark to draw here, you need to turn on the lamp first.");//Tell the player they need to turn on the light.
                }
                return new ArrayList<>();
            }
        });
        this.name = "Top of Dormitory"; //set the name of the place.
    }
}
