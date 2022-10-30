import TextGameEngine.TGE_Action;
import TextGameEngine.TGE_Description;
import TextGameEngine.TGE_F_EndGame;
import TextGameEngine.TGE_Place;

import java.util.ArrayList;
import java.util.HashMap;

public class TrashProcessingCenterPlace extends TGE_Place {
    //State of the place
    boolean hasPencilAndPaper = true; //Whether there is pencil and paper in the blue bin
    boolean hasTire = true; //Whether the robot has a spare tire
    public TrashProcessingCenterPlace() {
        super(); //call the parent constructor
        this.name = "Trash Processing Center"; //set the name of the place
        //Add TGE_Description objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should print the description to the console. As most of the code is just anonymous classes with a run method that prints some output, code should be fairly obvious. Comments will be included for descriptions that vary based on state.
        this.descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This is where the crew-mates on this spaceship throw their trash. There are blue, black, and a variety of other color bins to separate waste for recycling. Next to the bins is a small robot. There is also a window with a nice view into space.");
            }
        });
        this.descriptions.add(new TGE_Description("window") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("Near the window, there is a picture of 4 type of trash bins. It says: \"Please sort your trash correctly.\"");
            }
        });
        this.descriptions.add(new TGE_Description("robot") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("This little robot eats up the trash and compresses them down to small cubes. It has 4 wheels to move around"+(hasTire?", and 1 spare wheel on the back.":".")); //Vary description based on whether the robot still has a spare tire.
            }
        });
        //Add TGE_Action objects to the place. Concisely adds these objects using anonymous classes that implement the run method, which should perform the action, print any output to the console, and return possible further actions.
        //Follow-up action for the player to take the pencil and paper from the black trash bin.
        TGE_Action takePencilAndPaper = new TGE_Action("take", "Take the pencil and paper.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                hasPencilAndPaper = false; //update state
                System.out.println("You put the pencil and paper into your bag.");
                items.merge("pencil and paper", 1, Integer::sum); //add to inventory
                return new ArrayList<>();
            }
        };
        this.actions.add(new TGE_Action("search black bin", "Search for things in the black trash bin.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                ArrayList<TGE_Action> returnActions = new ArrayList<>();
                if(hasPencilAndPaper){ //allow the user to take the pencil and pen using an action if they are still present
                    System.out.println("You found a pencil and sheet of paper.");
                    returnActions.add(takePencilAndPaper);
                }
                else System.out.println("You did not find anything important."); //prompt the user if there is nothing left in the bin
                return returnActions;
            }
        });
        this.actions.add(new TGE_Action("search blue bin", "Search for things in the blue trash bin.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("You did not find anything important.");
                return new ArrayList<>();
            }
        });
        this.actions.add(new TGE_Action("take tire", "Take the spare tire from the robot.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                ArrayList<TGE_Action> returnActions = new ArrayList<>();
                if(hasTire){ //action can only be called if the robot has a spare tire
                    if((int)(Math.random()*2) == 0){ //There is a 50% chance the robot will give you its tire. The player must be persistent until the robot give it to them.
                        System.out.println("You asked the robot for its tire and it agreed. You now have a tire.");
                        hasTire = false; //update state
                        items.merge("tire", 1, Integer::sum); //add tire to inventory
                    }
                    else {
                        System.out.println("You asked the robot for its tire, but it refused. Maybe you should ask again more nicely.");
                        returnActions.add(this); //add this action to further actions to allow the user to call it again since they did not get the tire.
                    }
                }
                else{
                    System.out.println("The robot does not have any spare tires left, you took the only one."); //prompt the user if the spare tire has already been taken.
                }
                return returnActions;
            }
        });
    }
}
