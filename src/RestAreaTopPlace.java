import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class RestAreaTopPlace extends TGE_Place {
    boolean hasButton = true; //State of the place: whether there is a button on the fridge.
    boolean hasChargingAdaptor = true; //State of the place: whether there is a charging adaptor in the coffee machine.
    public RestAreaTopPlace() {
        super(); //calls parent constructor
        this.name = "Top of the Rest Area"; //sets the name of the place
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious. Comments will be included for descriptions that vary based on state.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("Here you can buy food and drinks. There is a large, professional coffee machine and a fridge.");
            }
        });
        this.descriptions.add(new TGE_Description("coffee machine") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("It seem like this coffee machine is broken...wait, what is that? There is a green keyhole on it.");
            }
        });
        this.descriptions.add(new TGE_Description("fridge") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("The fridge smells terrible. All of the food inside has gone bad. " + (hasButton?"There is a button stuck to the door.": "")); //vary description based on whether there is a button on the fridge.
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        this.actions.add(new TGE_Action("take button", "Take the button on the fridge.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(hasButton) { //if there is a button, but it in the inventory and then update state to reflect that it has been taken.
                    System.out.println("You put the button in your bag.");
                    items.merge("button", 1, Integer::sum);
                    hasButton = false;
                }
                else System.out.println("You have already taken the button."); //if there is not a button tell the player.
                return new ArrayList<>();
            }
        });
        this.actions.add(new TGE_Action("open coffee machine", "Open the coffee machine.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                if(items.get("green key") != null && items.get("green key") > 0){ //requires a green key to open the coffee machine
                    System.out.println("There is a secret compartment in the coffee machine. How weird?");
                    if(hasChargingAdaptor){
                        System.out.println("Inside the coffee machine you find a charging adaptor. You put it in your bag.");
                        items.merge("charging adaptor", 1, Integer::sum); //adds charging adaptor to items
                        hasChargingAdaptor = false;
                    }
                    else System.out.println("There used to be a charging adaptor here, but you took it.");
                }
                else System.out.println("You need a green key to open this coffee machine."); //prompt the user that they need a green key to open the coffee machine.
                return new ArrayList<>();
            }
        });
    }
}
