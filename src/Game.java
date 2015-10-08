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
    private final int stake = 50;
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
    private void betCheckFold(){
        for(Player onePlayer : players){
            //skip TablePlayer betting
            if(onePlayer instanceof TablePlayer){continue;}
            if(!onePlayer.getStillInGame()){continue;}
            if(onePlayer instanceof HumanPlayer){
                System.out.println("What do you want to do " + onePlayer.getName() + " : 0:check, 1:bet, 2:all-in, 3:fold");
                System.out.println("Money left: " + onePlayer.getMoney());
                switch (scan.nextInt()){
                    case 0:
                        break;
                    case 1:
                        System.out.println("how much? minimum is: " + stake);
                        //check if is less than 50....
                        for(Player t : players){
                            //find the table...
                            if(t instanceof TablePlayer){
                                t.addMoney(onePlayer.withdrawMoney(scan.nextInt()));
                                onePlayer.setStillInGame(true);
                            }
                        }
                        break;
                    case 2:
                        System.out.println("All in choosen. Betting: " + onePlayer.getMoney());
                        for(Player t : players){
                            //find the table...
                            if(t instanceof TablePlayer){
                                t.addMoney(onePlayer.withdrawMoney(onePlayer.getMoney()));
                                onePlayer.setStillInGame(true);
                            }
                        }
                        break;
                    case 3:
                        System.out.println("folded...");
                        onePlayer.setStillInGame(false);
                }
            }
        }
    }
    public void start(){

        System.out.println("SHUFFLING CARDS...");
        theDeck.shuffleCards();

        //first step, check who is interested.
        System.out.println("Place your bets: it costs 50.");
        //ERROR!!! 50 DOES NOT WITHDRAW SOMEHOW!!!!!
        //CHECK IT UP!!!!!
        for(Player onePlayer : players){
            //skip TablePlayer betting
            if(onePlayer instanceof TablePlayer){continue;}
            if(onePlayer.getMoney() >= stake) {
                if (onePlayer instanceof HumanPlayer) {
                    System.out.println(onePlayer.getName() + " are you in? 1=yes, 0=no");
                    if (scan.nextInt() == 1) {
                        for (Player t : players) {
                            //find the table, it can rotate between positions..
                            if (t instanceof TablePlayer) {
                                //Humanplayer gives money to the table
                                System.out.println("im here");
                                t.addMoney(onePlayer.withdrawMoney(stake));
                                System.out.println("pla: " + onePlayer.getMoney());
                                onePlayer.setStillInGame(true);
                            }
                        }
                    }else{onePlayer.setStillInGame(false);}
                } else if (onePlayer instanceof ComputerPlayer) {
                    System.out.println("computer wants to play..");
                    for (Player t : players) {
                        t.addMoney(onePlayer.withdrawMoney(stake));
                        onePlayer.setStillInGame(true);
                    }
                }
            }else{
                System.out.println("you are broke "+ onePlayer.getName() + ", money left: " + onePlayer.getMoney());
            }
            //System.out.println(onePlayer.toString());
        }
        System.out.println("player: " + players.get(1).getName() + " money: " + players.get(1).getMoney());
        //lets check,raise or fold:
        betCheckFold();
        betCheckFold();
        //lets deal the flop.

        //lets check,raise or fold:

        //lets deal the fourth card.

        //lets check,raise or fold:

        //lets deal the fourth card.

        //lets check,raise or fold:

        //round over!

/*        p1.addMoney(200);
        p1.addCard(theDeck.dealCard());
        System.out.println(p1.toString());
        */
    }
}
