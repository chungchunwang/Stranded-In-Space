package TextGameEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The main engine component responsible for running the game.
 */
public class TGE_Engine {
    //The grid of the game
    TGE_Place[][] grid;
    //The x and y position of the user in the game.
    int xPos = 1;
    int yPos = 1;
    //The number of steps remaining in the game
    int stepsLeft;
    //Map of items the user holds
    HashMap<String, Integer> items;
    //The name of the game
    String name;
    //The instructions of the game
    TGE_Screen instructions;
    //An array of possible endScreen of the game
    ArrayList<TGE_Screen> endScreens;
    //The screen that is displayed when the player runs out of steps
    TGE_Screen timeOutScreen;
    //A scanner object used to get input
    Scanner input;
    //Information for the stats page - names are self-explanatory
    int stat_NumActions = 0;
    int stat_NumSteps = 0;
    int stat_NumOpenStats = 0;

    /**
     * Constructor:
     * @param grid The grid map of the game (2D array), where each element is a place the player can stand on (a child of TGE_Place).
     * @param xPos An integer to define the x coordinate of the starting position on the grid.
     * @param yPos An integer to define the y coordinate of the starting position on the grid.
     * @param items A map representing the initial items a player has. The key is the name of the item and the value is the amount of said item one has.
     * @param name The name of the game in the form of a string.
     * @param duration How many steps can one play in the game.
     */
    public TGE_Engine(TGE_Place[][] grid, int xPos, int yPos, HashMap<String, Integer> items, String name, TGE_Screen instructions, TGE_Screen timeOutScreen, ArrayList<TGE_Screen> endScreens, int duration){
        //sets data fields from inputs.
        this.grid = grid;
        this.xPos = xPos;
        this.yPos = yPos;
        this.items = items;
        this.name = name;
        input = new Scanner(System.in);
        this.instructions = instructions;
        this.timeOutScreen = timeOutScreen;
        this.endScreens = endScreens;
        stepsLeft = duration;
    }

    /**
     * To move the player.
     * @param x The amount to shift the player by in the x direction.
     * @param y The amount to shift the player by in the y direction.
     * @return Whether the movement was successful.
     */
    private boolean movePlayer(int x, int y){
        //adds offset
        int newXPos = xPos + x;
        int newYPos = yPos + y;
        //Checks if coordinates are valid
        //1. Out of bounds check
        if(newXPos >= grid[0].length) return false;
        if(newYPos >= grid.length) return false;
        if(newXPos < 0) return false;
        if(newYPos < 0) return false;
        //2. Whether the place is a void
        if(grid[newYPos][newXPos] == null) return false;

        //Set value and return success
        xPos = newXPos;
        yPos = newYPos;
        stepsLeft--;
        stat_NumSteps++;
        return true;
    }

    /**
     * Starts the game.
     */
    public void run(){
        //Print the introductory screens, flipped by pressing Enter
        clearConsole();
        System.out.println("Welcome to " + name + "!");
        System.out.println("Press Enter to continue...");
        input.nextLine();
        clearConsole();
        System.out.println("Let's go over the instructions and basic controls!");
        System.out.println("Press Enter to continue...");
        input.nextLine();
        clearConsole();
        printInstructions();
        clearConsole();
        printHelp();
        clearConsole();
        //Stores a message generated from the previous cycle, usually an error message
        String message = "";
        //Begins the game cycle. Prints information about the game based on the current game state, and any new input
        //will trigger a new cycle, due to the state change.
        while(true){
            //Clear the console to print the new page
            clearConsole();
            //If there are no steps left exit the game.
            if(stepsLeft < 0 ) {
                timeOutScreen.display();
                System.exit(1);
            }
            //Gets the players current position on the grid.
            TGE_Place place = grid[yPos][xPos];

            //Prints Messages, if there are any, then resets the message field.
            if(message.length()>0) {
                System.out.println("|-------------------------------------Notice:-------------------------------------|");
                System.out.println(" > " + message);
                System.out.println("|---------------------------------------------------------------------------------|");
                System.out.println();
            }
            message = "";
            //Prints the number of steps left (time)
            System.out.println("Clock: " + stepsLeft + " Minutes Left");
            //Prints the minimap
            System.out.println("|##################################### Map: ######################################|");
            System.out.println();
            System.out.print("  ");
            for (int j = 0; j< grid[0].length; j++) System.out.print(" " + j + " ");
            System.out.println();
            for (int i = 0; i < grid.length; i++){
                String mapLine = i + " ";
                for (int j = 0; j< grid[0].length; j++){
                    if(grid[i][j] == null) {
                        mapLine += "   ";
                    }
                    else if (xPos == j && yPos == i) {
                        mapLine += " o ";
                    } else {
                        mapLine += " # ";
                    }
                }
                System.out.println(mapLine);
            }
            System.out.println("Your Position: (" + xPos + "," + yPos + ")");
            //Prints the player's items
            System.out.println("|################################### Items: ######################################|");
            for(Map.Entry<String, Integer> item : items.entrySet()) {
                String name = item.getKey();
                Integer count = item.getValue();
                System.out.print(" ## Count: "+count + " | Name: " + name + " ## ");
            }
            System.out.println();
            //Prints a description of the place the player is at
            System.out.println("|############################# Place Description: ################################|");
            System.out.println("You are at: " + place.name);
            System.out.print("Description: ");
            for (int i = 0; i< place.descriptions.size(); i++){
                if(place.descriptions.get(i).command == "")
                    place.descriptions.get(i).run(items, (int endScreen) -> {
                        clearConsole();
                        endScreens.get(endScreen).display();
                        System.exit(1);
                    });
            }
            //Prints the commands available to the player
            System.out.println("|################################### Commands: ###################################|");
            System.out.println("|Type           |Command                  |Description                            |");
            for (TGE_Action action : place.actions) {
                System.out.println(action.toString());
            }
            for (TGE_Description description : place.descriptions) {
                System.out.println(description.toString());
            }
            if(place.actions.size() == 0) System.out.println("None available.");
            //Prints prompt asking for command input
            System.out.println();
            System.out.println("|---------------------- Call an command from the list above. ---------------------|");
            System.out.print("Enter a command: ");
            //gets command
            String output = input.nextLine();

            //Search actions for command
            boolean exitActionScreen = false; //whether to exit the action screen
            boolean actionTriggered = false; //whether an action has been triggered by the command
            ArrayList<TGE_Action> actions = place.actions; //stores the current available actions
            while(!exitActionScreen){
                exitActionScreen = true; //If no action is triggered, we should exit the while loop
                //Loops through all possible actions. If any matches with the inputted command trigger the action.
                //Technically this would be more effective with a hashmap, however it is not necessary as the amount of actions possible would be only single digits.
                for(int i = 0; i < actions.size();i++){
                    if(actions.get(i).command.equals(output)){
                        //Get the action that corresponds the command.
                        TGE_Action action = actions.get(i);
                        clearConsole();
                        System.out.println("|################################# Action Screen #################################|");
                        //Run the action, provide items and a method to end the game, save returned actions.
                        actions = action.run(items, (int endScreen) -> {
                            clearConsole();
                            endScreens.get(endScreen).display();
                            System.exit(1);
                        });
                        //List returned actions
                        System.out.println("|################################ Further Actions: ###############################|");
                        System.out.println("|Type           |Command                  |Description                            |");
                        for (TGE_Action ac : actions) {
                            System.out.println(ac.toString());
                        }
                        if(actions.size() == 0) System.out.println("None available.");
                        System.out.println();

                        exitActionScreen = false; //an action is triggered, so we should continue the loop to respond to further actions
                        stat_NumActions++; //update statistic

                        //Get next command from user
                        boolean validInput = false; //Whether an input is valid
                        while (!validInput){ //get inputs until input is valid
                            //Prompt user for next command
                            System.out.println("|---- Call an command from the list above or press Enter to exit this action. ----|");
                            System.out.print("Enter a command: ");
                            output = input.nextLine(); //store command in output
                            for (TGE_Action a : actions) { //loop through possible actions to find out is command is valid
                                if (a.command.equals(output)) validInput = true;
                            }
                            if (output.equals("")) validInput = true; //if input is empty, it signifies the users wishes to exit the screen, so it is valid.
                            if(!validInput) System.out.println("Invalid input, try again."); //if invalid, prompt the user
                        }
                        break;
                    }
                }
                //If exiting an action once an action has already triggered,
                //we set this boolean to true so that we can bypass further checks on the input.
                if(!exitActionScreen) actionTriggered = true;
            }
            if(actionTriggered) continue; //If an action has been triggered, the command has been used up, so we reload the page.

            boolean descriptionTriggered = false; //Whether the command matches with a description.
            if (output.startsWith("look")){ // A description command must start with "look"
                //Get the command appended to look, and compare it with the list of description in the current place.
                String lookSuffix = "";
                if(output.length()>5) lookSuffix = output.substring(5);
                for (int i = 0; i<place.descriptions.size(); i++){
                    if(place.descriptions.get(i).command.equals(lookSuffix)){
                        //If the code enters here there is a description that matches.
                        descriptionTriggered = true; //update the boolean to reflect the match
                        //Outputs the description.
                        clearConsole();
                        System.out.println("|################################## Description Screen ##################################|");
                        place.descriptions.get(i).run(items, (int endScreen) -> {
                            clearConsole();
                            endScreens.get(endScreen).display();
                            System.exit(1);
                        });
                        System.out.println("|######################## Press Enter to exit this description. #########################|");
                        output = input.nextLine();
                        break;
                    }
                }
                if(!descriptionTriggered) message = "Syntax Error - Your command was not able to be parsed"; //If not description matches there must be a syntax error, so we set that as a message.
            }
            else{
                switch (output){ //Search through other default commands
                    case "north":
                        if(!movePlayer(0,-1)) message = "Movement Failed - There isn't any space to move to in the north."; //move player north, if actions fails, set error message
                        break;
                    case "south":
                        if(!movePlayer(0,1)) message = "Movement Failed - There isn't any space to move to in the south."; //move player south, if actions fails, set error message
                        break;
                    case "east":
                        if(!movePlayer(1,0)) message = "Movement Failed - There isn't any space to move to in the east."; //move player east, if actions fails, set error message
                        break;
                    case "west":
                        if(!movePlayer(-1,0)) message = "Movement Failed - There isn't any space to move to in the west."; //move player west, if actions fails, set error message
                        break;
                    case "exit":
                        System.out.println("Thanks for playing the game! The program will stop now."); //Exit the game by returning the function.
                        return;
                    case "instructions": //clears console and prints instructions
                        clearConsole();
                        printInstructions();
                        break;
                    case "help": //clears console and prints help menu
                        clearConsole();
                        printHelp();
                        break;
                    case "stats": //clears console and prints stats menu
                        clearConsole();
                        printStats();
                        break;
                    case "inv": //clears console and prints inventory menu
                        clearConsole();
                        printInventory();
                        break;
                    default: //sets message for if the command can not be parsed
                        message = "Syntax Error - Your command was not able to be parsed";
                }
            }
        }
    }

    /**
     * Prints the stats screen.
     */
    private void printStats() {
        stat_NumOpenStats++;
        System.out.println("|##################################### Stats Screen #####################################|");
        System.out.println("Steps Left: " + stepsLeft);
        System.out.println("Number of Steps Taken: " + stat_NumSteps);
        System.out.println("Number of Items: " + items.size());
        System.out.println("Number of Actions Performed: " + stat_NumActions);
        System.out.println("Number of Times You Opened This Stats Screen: " + stat_NumOpenStats);
        System.out.println("|######################## Press Enter to exit this stats screen. ########################|");
        input.nextLine();
    }

    /**
     * Print the inventory screen.
     */
    private void printInventory(){
        System.out.println("|################################### Inventory Screen ###################################|");
        for(Map.Entry<String, Integer> item : items.entrySet()) {
            String name = item.getKey();
            Integer count = item.getValue();
            System.out.println(" ## Count: "+count + " | Name: " + name + " ## ");
        }
        System.out.println("|###################### Press Enter to exit this inventory screen. ######################|");
        input.nextLine();
    }

    /**
     * Clears the console by outputting lots of newlines.
     */
    private static void clearConsole() {
        for (int i = 0; i<40; i++) System.out.println();
    }

    /**
     * Prints the instructions of the game.
     */
    private void printInstructions(){
        instructions.display();
    }

    /**
     * Prints the help menu of the game.
     */
    private void printHelp(){
        System.out.println("Interact with the game by typing commands into the console! View below for a list of commands you can use.");
        System.out.println("Additionally, particular places in the map may also have special commands you can perform, called actions. Instructions for them will be displayed whenever you enter such a place.");
        System.out.println("Commands:");
        System.out.println("Command         |Description");
        System.out.println("------------------------------------------------");
        System.out.println("north           |Walk north on the map.");
        System.out.println("south           |Walk south on the map.");
        System.out.println("east            |Walk east on the map.");
        System.out.println("west            |Walk west on the map.");
        System.out.println("exit            |Exits the game, no progress will be save.");
        System.out.println("help            |Opens this help page.");
        System.out.println("instructions    |Opens the instructions page.");
        System.out.println("stats           |Opens the stats page.");
        System.out.println("inv             |Opens the inventory page.");
        System.out.println("look            |Provides a description of you surroundings (although the same description is already provided on the dashboard).");
        System.out.println("look (something)|Provides a description of (something). Only available for certain object (all possible look commands will be listed on your screen).");
        System.out.println();
        System.out.println("Press Enter to open the game...");
        input.nextLine();
    }
}
