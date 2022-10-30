import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class CockpitPlace extends TGE_Place {
    //State of the cockpit, names self-explanatory
    boolean hasTakenPhoneCall = false;
    boolean takenPhoneRemains = false;
    boolean hasEngineGrease = true;
    boolean hasWeldingMachine = true;
    public CockpitPlace() {
        super(); //Call parent constructor.
        this.name = "Cockpit"; //Define the name of the place.

        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious. Comments will be included for descriptions that vary based on state.
        this.descriptions.add(new TGE_Description("") { //Main description
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is where you drive your spaceship. There is a door leading to a unmarked room to the right, and a small drawer to the left. " + (!hasTakenPhoneCall?"There is is phone that is ringing.":"")); // Change description to reflect whether phone call has been taken or not.
            }
        });
        this.descriptions.add(new TGE_Description("phone") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                //Vary the descriptions based on whether the phone call has been taken and whether the remains are still there.
                if(!hasTakenPhoneCall) System.out.println("It is a phone making a loud ringing noise. You should probably pick us.");
                else if(!takenPhoneRemains) System.out.println("All you see are remains of a broken phone. Maybe they might be useful.");
                else System.out.println("There used to be a broken phone here, but you took it.");
            }
        });
        this.descriptions.add(new TGE_Description("door") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("There seems to be something behind this door, but you need a security key to open it.");
            }
        });
        this.descriptions.add(new TGE_Description("small drawer") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("A small drawer is locked. You can open it by inserting a red key.");
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        TGE_Action takePhonePieces = new TGE_Action("take phone pieces", "Take the remains of the phone.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("You took the broken pieces of the phone.");
                takenPhoneRemains = true; //update state
                items.merge("phone fragments", 1, Integer::sum); //Increment the number in "phone fragments" in the map (all strings by default are considered as 0)
                return new ArrayList<>();
            }
        };
        this.actions.add(new TGE_Action("pick up phone", "Pick up ringing phone.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                ArrayList<TGE_Action> returnActions = new ArrayList<>(); //list of actions to be returned
                if(!hasTakenPhoneCall){ //runs if phone calls has not been made
                    System.out.println("Mysterious Man (in a muffled voice): Dear Commander, you will find a pencil and paper in the Trash Processing Center. Now this next thing you must do is very important... !!! Connection Closed !!!");
                    System.out.println("The phone explodes into pieces. Maybe they might be useful?");
                    hasTakenPhoneCall = true; //update state to reflect that phone call has been made
                    returnActions.add(takePhonePieces); //adds take phone pieces as a possible followup action
                }
                else { //runs if phone call has already been made
                    System.out.println("You have already taken the phone call.");
                    if(!takenPhoneRemains){ //allow the player to take debris is they did not do so earlier
                        System.out.println("However, you can still take the remains from the exploded phone.");
                        returnActions.add(takePhonePieces); //adds take phone pieces as a possible followup action
                    }
                }
                return returnActions;
            }
        });
        this.actions.add(new TGE_Action("open door", "Try to open the door.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(items.get("security card") != null &&items.get("security card") > 0) { //requires security card as an item to perform action
                    System.out.print("You open the door with the security card you found. ");
                    if(hasEngineGrease){ //only give the player item if they have not already gotten it
                        System.out.println("You found some engine grease. This will be useful in building your new engine.");
                        items.put("engine grease", 1); //give the player 1 engine grease, use put as there is no second of this item in game
                        System.out.println("You close the door.");
                        hasEngineGrease = false; //update state
                    }
                    else System.out.println("There is nothing inside. You close the door again.");
                }
                else  System.out.println("Sorry: You need a security card to open this door."); //notify the player that a security card is required
                return new ArrayList<>();
            }
        });
        this.actions.add(new TGE_Action("open drawer", "Open the small drawer.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(items.get("red key") != null &&items.get("red key") > 0) { //requires red key as an item to perform action
                    System.out.print("You open the door with the red key you found. ");
                    if (hasWeldingMachine) { //only give the player item if they haven't gotten it before
                        System.out.println("Inside the drawer you find a welding machine. This might be useful when assembling your new engine.");
                        items.put("welding machine", 1); //give the player 1 engine grease, use put as there is no second of this item in game
                        System.out.println("You close the drawer.");
                        hasWeldingMachine = false;//update state
                    } else System.out.println("There is nothing inside. You close the drawer again.");
                }
                else  System.out.println("Sorry: You need a red key to open this door."); //notify the player they need a red key
                return new ArrayList<>();
            }
        });
    }
}
