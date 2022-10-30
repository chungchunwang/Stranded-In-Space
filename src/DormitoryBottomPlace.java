import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class DormitoryBottomPlace extends TGE_Place {
    //state of place, names self-explanatory
    boolean hasPickedUpBook = false;
    boolean hasSecurityCard = true;
    public DormitoryBottomPlace() {
        super(); //call parent constructor
        this.name = "Bottom of the Dormitory"; //set place name
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious. Comments will be included for descriptions that vary based on state.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is the part of the dormitory where you play games and relax. There is a table with a gaming PC on it. In a corner there is also a wooden barrel.");
            }
        });
        this.descriptions.add(new TGE_Description("computer") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("The computer screen is on. You see that Overwatch 2 is loaded, however it is still stuck in queue. " +(hasPickedUpBook?"There is also a book on the desk.": "A book has also fallen to the floor.")); //updates description based on whether the book has been picked up
            }
        });
        this.descriptions.add(new TGE_Description("barrel") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This seems to be a replica of the wooden barrels in Donkey Kong. " + (hasSecurityCard?"There seems be be something inside the barrel.":"")); //updates based on whether a security card is still in the barrel
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        this.actions.add(new TGE_Action("pick book", "Pick up the book on the ground and place it back on the desk.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(hasPickedUpBook) System.out.println("The book is already on the desk."); //action fails if already performed
                else {
                    System.out.println("You pick up the book, and you flip through the pages. It does not interest you, and you put it on the desk.");
                    hasPickedUpBook = true; //update the state so that the book has been picked up
                }
                return new ArrayList<>();
            }
        });
        this.actions.add(new TGE_Action("search barrel", "See if there is something inside the barrel.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(!hasSecurityCard) System.out.println("No, there is nothing inside the barrel."); //action fails if already performed
                else {
                    System.out.println("You have found a security card inside the barrel. It might be useful if you want to open a door.");
                    items.merge("security card", 1, Integer::sum); //add the security card to items
                    hasSecurityCard = false; //update state so that there is no security card left
                }
                return new ArrayList<>();
            }
        });
    }
}
