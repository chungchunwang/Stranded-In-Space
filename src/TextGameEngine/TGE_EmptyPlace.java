package TextGameEngine;
/*
* NOT PART OF THE FINAL PROJECT, ONLY USED FOR TESTING. AS SUCH, THERE ARE NO COMMENTS.
* */
import java.util.ArrayList;
import java.util.HashMap;

public class TGE_EmptyPlace extends TGE_Place {
    public TGE_EmptyPlace() {
        super();
        ArrayList actions = new ArrayList();
        TGE_Action pickUpCoin = new TGE_Action("take", "Pick up something on the ground.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("Yay! You found a coin.");
                items.merge("coin", 1, Integer::sum);
                return new ArrayList<TGE_Action>();
            }
        };
        TGE_Action fallDown = new TGE_Action("fall", "Fall down.") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                endGame.call(0);
                return new ArrayList<TGE_Action>();
            }
        };
        TGE_Action humASong = new TGE_Action("hum", "Hum a song!") {
            @Override
            public ArrayList<TGE_Action> run(HashMap<String, Integer> state, TGE_F_EndGame endGame) {
                ArrayList<TGE_Action> actions = new ArrayList<TGE_Action>();
                System.out.println("You hummed a pretty song!");
                boolean findSomething = (int)(Math.random()*2) == 1;
                if(findSomething) {
                    System.out.println("Oh! I found something... What is that?");
                    actions.add(pickUpCoin);
                    actions.add(fallDown);
                };
                return actions;
            }
        };

        this.actions.add(humASong);
        this.name = "Empty Location";
        ArrayList<TGE_Description> descriptions = new ArrayList<TGE_Description>();
        descriptions.add(new TGE_Description("") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("Hmm, it seem like there is nothing here. Perhaps there may be something on the ground?");
            }
        });
        descriptions.add(new TGE_Description("ground") {
            @Override
            public void run(HashMap<String, Integer> items, TGE_F_EndGame endGame) {
                System.out.println("Nope! Nothing here either.");
            }
        });
        this.descriptions = descriptions;
    }
}
