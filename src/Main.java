/*
* APCSA Q1P – Text Adventure
* By: Jason Wang, Jayden Chai
*
* How the code is organized:
* The main game mechanics are contained inside the TextGameEngine package (not imported, we wrote it by ourselves).
* The package contains a TGE_Engine object which will take in a couple of settings and then run the game.
* The settings allow us to customize the map and what can be done in each place.
* This organization allows us to work in parallel, writing the core code at the same time the storyline is being developed.
*
* Additional Notes:
* This is mentioned in some comments, but then became too tedious to explain every time, so I will note it here instead. For adding and removing items from the player, some actions use the merge method and others use the put method. The merge method (not merge in general but the version I use in my actions) increments the value of the given key, and considers unset keys as having a value of 0. In some other parts of the code I instead use the put method to set the value of a key to 1. Both methods do the same thing as there is only 1 of any item in the game. Some actions used merge because there were initial plans to be able to have multiple of an item.
 * */
import TextGameEngine.*;

import java.util.ArrayList;
import java.util.HashMap;

/*
* This is the entry point of the program.
* */
public class Main {
    public static void main(String[] args) {
        //Sets up the initial map in a 2D array, each place on the map is its own class.
        TGE_Place[][] grid = {
              //{null,                  null,                   null,                   null,                   null,                   null,                   null                } - For reference
                {null,                  new StorageAreaPlace(),   new RestAreaTopPlace(),   new DormitoryTopPlace(),   new ElectricityControlPlace()},
                {new CockpitPlace(),  new CorridorPlace(),   new RestAreaCenterPlace(),   new DormitoryCenterPlace(),   new EngineChamberPlace()},
                {null,                  new TrashProcessingCenterPlace(),   new RestAreaBottomPlace(),   new DormitoryBottomPlace(),   new ToiletPlace()}
        };

        //Define the instructions to be shown in the game.
        TGE_Screen instructions = new TGE_Screen(
                """ 
                You are Tim, the universe's greatest spacecraft pilot.
                Due to a mechanical failure in the nuclear fusion cell in the ship, a surge of energy was sent to your engine, causing it to explode.
                This surge also caused the electric grid on the ship to shut down.
                The ship is now leaking oxygen from where the engine used to be, and you have only an hour before you asphyxiate.
                As this is a large spaceship, it takes you a minute to move one space.
                Luckily, since you are such a skilled pilot, it will take you virtually no time to perform any actions.
                To fix the ship, you will need to assemble an new engine. Parts of the old engine are scattered together around the ship, and other materials also lay around the place.
                You need to gather and put these parts together - before its too late.
                Press Enter to open the game..."""
        );
        //Define the death screen
        TGE_Screen deathScreen = new TGE_Screen(
                """
                    |########################################################################################|
                    |  -----  You Died:                                                                      |
                    | (x   x) You and your crew suffocated inside your own ship.                             |
                    |  \\ - /  If only you fixed the engine faster!                                          |
                    |########################################################################################|"""
        );
        //Define the fall screen
        TGE_Screen explosionScreen = new TGE_Screen("""
                |########################################################################################|
                |   \\(*-*)/    You Blew Up:                                                              |
                |      |       You entered the number the robot on the phone told you.                   |
                |      ^       However, you remembered incorrectly, and the fitness equipment blew up.   |
                |########################################################################################|""");
        TGE_Screen electrocutedScreen = new TGE_Screen("""
                |########################################################################################|
                |    ///       You Got Electrocuted:                                                     |
                |    \\\\\\       You pulled the wrong lever.                                               |
                |    ///       It sent a high voltage shock through your body.                           |
                |########################################################################################|""");

        //Define the win screen
        TGE_Screen winScreen = new TGE_Screen("""
                |########################################################################################|
                |               You Win:                                                                 |
                |  ※\\(^o^)/※   You survived! This is why you are one of the greatest pilots in the      |
                                entire universe.                                                         |
                |########################################################################################|""");
        //Add the end screens into an arrayList
        ArrayList<TGE_Screen> endScreens = new ArrayList<>();
        endScreens.add(explosionScreen);
        endScreens.add(electrocutedScreen);
        endScreens.add(winScreen);
        //Creates a new game engine object settings.
        TGE_Engine engine = new TGE_Engine(grid,3,1,new HashMap<String, Integer>(), "Stranded In Space",instructions,deathScreen,endScreens, 60);
        //Use the game engine to start the game.
        engine.run();
    }
}
//Ignore below, just for reference purposes.
/*
* Legacy Map:
        TGE_Place[][] grid = {
              //{null,                  null,                   null,                   null,                   null,                   null,                   null                } - For reference
                {null,                  null,                   null,                   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   null,                   null                },
                {null,                  new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   null                },
                {new TGE_EmptyPlace(),  new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace()},
                {new TGE_EmptyPlace(),  new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace()},
                {null,                  new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   null                },
                {null,                  null,                   null,                   new TGE_EmptyPlace(),   new TGE_EmptyPlace(),   null,                   null                },
        };
* */