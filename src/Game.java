import CardPackage.Deck;
import PlayerPackage.HumanPlayer;
import PlayerPackage.*;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import java.util.ArrayList;

/**
 * Created by Arvid on 2015-09-15.
 *
 */
public class Game {

    private ArrayList<Player> players;
    //computer;
    Deck theDeck;

    public Game() {
        theDeck = new Deck();
        players = new ArrayList<Player>();
        theDeck.shuffleCards();
        //add players temporary:
        players.add(new TablePlayer("Table00"));
        players.add(new HumanPlayer("Johan"));
        players.add(new HumanPlayer("Arvid"));
        players.add(new ComputerPlayer("SuperAI"));

    }
    public void start(){
        System.out.println("TEST");
        for(Player onePlayer : players){
            if(onePlayer instanceof HumanPlayer){
                System.out.println("you are a human!");
            }else if(onePlayer instanceof ComputerPlayer){
                System.out.println("your are a computer");
            }else if (onePlayer instanceof TablePlayer){
                System.out.println("you are a table!");
            }
            System.out.println(onePlayer.toString());
        }
/*        p1.addMoney(200);
        p1.addCard(theDeck.dealCard());
        System.out.println(p1.toString());
        */
    }
}
