import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class RestAreaBottomPlace extends TGE_Place {
    public RestAreaBottomPlace() {
        super(); //call parent constructor
        this.name = "Bottom of the Rest Area"; //set the name of the place
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious. Comments will be included for descriptions that vary based on state.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is where you can read books and do exercises. There is a telephone in the corner and a high tech piece of fitness equipment in the center.");
            }
        });
        this.descriptions.add(new TGE_Description("fitness equipment") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is a new type of fitness equipment that is all in one. You can change it into any piece of equipment you would like by entering what you want on its screen.");
            }
        });
        this.descriptions.add(new TGE_Description("telephone") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("A paper near the telephone states: \"Please dial a number that reminds you of old technology.\"");
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        TGE_Action rememberNumber = new TGE_Action("remember number", "Remember the number the robot tells you.") { //Follow-up action for the player to remember the number. The memory is stored as an item.
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("You memorized the number, unfortunately incorrectly.");
                items.put("memory of fitness equipment number", 1);
                return new ArrayList<>();
            }
        };
        this.actions.add(new TGE_Action("dial telephone", "Dial the telephone in the room.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                ArrayList<TGE_Action> returnActions = new ArrayList<>();
                if(items.get("memory of cassette number") != null && items.get("memory of cassette number") > 0){ //only allow the player to dial the phone if they remember the phone number from the cassette
                    System.out.println("You called the number that was written on the cassette in the Storage Area. A robotic voice on the other end tells you to enter the number 263541 on the fitness equipment.");
                    returnActions.add(rememberNumber); //adds remember number as a followup action
                }
                else System.out.println("You don't know what number to call. Maybe you might find an important number elsewhere.");
                return returnActions;
            }
        });
        TGE_Action enterNumber = new TGE_Action("enter number", "Enter a number into the fitness equipment screen.") { //Follow-up action for the player to enter the remembered number into the fitness equipment. This will end the game.
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(items.get("memory of fitness equipment number") != null && items.get("memory of fitness equipment number") > 0){ //If the player has the incorrect memory, end the game with end screen 0.
                    endGame.call(0);
                }
                else System.out.println("You don't know what number to input. Try again after you find out what to enter."); //Otherwise, prompt the player that they do not know the input.
                return new ArrayList<>();
            }
        };
        this.actions.add(new TGE_Action("inspect fit equip", "Inspect the fitness equipment.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                ArrayList<TGE_Action> returnActions = new ArrayList<>();
                returnActions.add(enterNumber); //add enter number as a follow-up action
                System.out.println("You tap on the screen to start up the fitness equipment. On the screen, you can change the fitness equipment to be a treadmill, a push-up machine, or a rowboat machine. There is also a place to input a number at the top.");
                return returnActions;
            }
        });
    }
}
