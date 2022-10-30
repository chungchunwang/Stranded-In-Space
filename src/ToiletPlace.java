import TextGameEngine.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ToiletPlace extends TGE_Place {
    //State of the place
    boolean visited = false; //whether the place has already been visited
    boolean mirrorBroken = false; //whether the mirror is broken
    boolean hasToiletPaper = true; //whether the toilet has toilet paper
    boolean showerheadReplaced = false; //whether the showerhead has been replaced
    public ToiletPlace() {
        super(); //calls the parent constructor
        this.name = "Toilet"; //set the name of the place
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious. Comments will be included for descriptions that vary based on state.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is where you maintain your personal hygiene. There is a toilet seat (and toilet paper), a shower (with a showerhead), and a mirror. " + (visited?"":"You hear a very loud sound, but you don't know what happened."));
                visited = true; //updates state to reflect that the place has been visited. (Default description is called whenever the player enters the place.)
            }
        });
        this.descriptions.add(new TGE_Description("mirror") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(mirrorBroken) System.out.println("You see a smashed mirror."); //updates description based on whether the mirror is smashed or not.
                else System.out.println("You see that the mirror is not reflective, its transparent! Maybe there is something behind it.");
            }
        });
        this.descriptions.add(new TGE_Description("showerhead") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(!showerheadReplaced)System.out.println("Oh, it fell. The sound you heard earlier here was caused by it."); //updates description based on whether the showerhead has been replaced or not.
                else System.out.println("The showerhead is in its place on the wall. A little water is dripping from it.");
            }
        });
        this.descriptions.add(new TGE_Description("toilet seat") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.print("The toilet seat is made of white ceramic. It is very conformable but a bit cold. ");
                if(hasToiletPaper) System.out.println("There is toilet paper near the toilet seat. Maybe you should take it, it might be useful."); //Updates description based on whether there is toilet paper or not.
                else System.out.println("There's no toilet paper nearby! Oh, right, I took it.");
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        this.actions.add(new TGE_Action("break mirror", "Smash the mirror to see what's on the other side.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if (mirrorBroken) System.out.println("The mirror is already broken."); //prompts the user if the mirror is already broken
                else { //add the detergent behind the mirror to items and then update state to reflect that the mirror has been broken
                    System.out.println("You smash the glass and see a bottle of detergent inside. How weird? You put it in your bag.");
                    items.merge("detergent", 1, Integer::sum);
                    mirrorBroken = true;
                }
                return new ArrayList<>();
            }
        });
        this.actions.add(new TGE_Action("shower", "Go into the showers.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("You took a shower. Now you feel refreshed. You put the showerhead back in its proper place.");
                showerheadReplaced = true; //update state
                return new ArrayList<>();
            }
        });
        this.actions.add(new TGE_Action("take tp", "Take the toilet paper.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(hasToiletPaper) { //if there is toilet paper, add it to items and then update state to reflect that it has been taken
                    System.out.println("You took the toilet paper... but at what cost ... what will happen to the next person to use the toilets?");
                    items.merge("toilet paper", 1, Integer::sum);
                    hasToiletPaper = false;
                }
                else System.out.println("There is no toilet paper left."); //prompt the player that there is no toilet paper left.
                return new ArrayList<>();
            }
        });
    }
}
