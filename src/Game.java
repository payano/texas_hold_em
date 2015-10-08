import CardPackage.Deck;
import PlayerPackage.HumanPlayer;
import PlayerPackage.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Arvid on 2015-09-15.
 *
 */
public class Game {

    private ArrayList<Player> players;
    //computer;
    Deck theDeck;
    Scanner scan = new Scanner(System.in);
    public Game() {
        theDeck = new Deck();
        players = new ArrayList<Player>();
        //add players temporary:
        players.add(new TablePlayer("Table"));
        players.add(new HumanPlayer("Johan"));
        players.get(1).addMoney(50);

        players.add(new HumanPlayer("Arvid"));
        players.get(2).addMoney(40);
        players.add(new ComputerPlayer("SuperAI"));
        players.get(3).addMoney(1000);

    }
    public void start(){
        int stake = 50;
        System.out.println("DEALING CARDS...");
        theDeck.shuffleCards();
        System.out.println("Place your bets: it costs 50.");
        for(Player onePlayer : players){
            if(onePlayer.getMoney() >= stake) {
                if (onePlayer instanceof HumanPlayer) {
                    System.out.println(onePlayer.getName() + " are you in? 1=yes, 0=no");
                    if (scan.nextInt() == 1) {
                        for (Player t : players) {
                            if (t instanceof TablePlayer) {
                                //Humanplayer gives money to the table
                                t.addMoney(onePlayer.withdrawMoney(stake));
                            }
                        }
                    }
                } else if (onePlayer instanceof ComputerPlayer) {
                    System.out.println("computer wants to play..");
                    for (Player t : players) {
                        t.addMoney(onePlayer.withdrawMoney(stake));
                    }
                    System.out.println("your are a computer");
                }
            }else{
                if(!(onePlayer instanceof TablePlayer)){
                    System.out.println("you are broke "+ onePlayer.getName() + ", money left: " + onePlayer.getMoney());
                }

            }
            //System.out.println(onePlayer.toString());
        }
/*        p1.addMoney(200);
        p1.addCard(theDeck.dealCard());
        System.out.println(p1.toString());
        */
    }
}
