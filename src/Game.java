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
    private void betRound(){
        for(Player onePlayer : players){
            //skip TablePlayer betting
            if(onePlayer instanceof TablePlayer){continue;}
            if(!onePlayer.getStillInGame()){continue;}
            if(onePlayer instanceof HumanPlayer){
                System.out.println("What do you want to do " + onePlayer.getName() + " : 0:check, 1:bet, 2:all-in, 3:fold");
                System.out.println("Money left: " + onePlayer.getMoney());
                betCheckFold(onePlayer);
            }
        }
    }

    public void betCheckFold(Player onePlayer){
        switch (scan.nextInt()){
            case 0:
                break;
            case 1:
                System.out.println("how much? minimum is: " + stake);
                //check if is less than 50....
                for(Player t : players){
                    //find the table...
                    findTable().addMoney(onePlayer.withdrawMoney(scan.nextInt()));
                }
                break;
            case 2:
                System.out.println("All in choosen. Betting: " + onePlayer.getMoney());
                for(Player t : players){
                    //find the table...
                    findTable().addMoney(onePlayer.withdrawMoney(onePlayer.getMoney()));
                    onePlayer.setStillInGame(true);
                }
                break;
            case 3:
                System.out.println("folded...");
                onePlayer.setStillInGame(false);
        }
    }

    public Player findTable(){
        for(Player t : players) {
            //find the table...
            if (t instanceof TablePlayer) {
                return t;
            }
        }
        return null;
    }
    public void start(){

        System.out.println("SHUFFLING CARDS...");
        theDeck.shuffleCards();

        //make no player still in game
        for(Player onePlayer : players){
            onePlayer.setStillInGame(false);
        }

        //first step, check who is interested.
        //first player in arraylist must pay smallblind: 25, the rest must pay bigblind, raise or fold.
        //the first player must also make a choice, fold, call or raise.

        //first time is special:
        /*

        Example Betting Round 1

        There are five players at the table:

        Player 1 - Button

        Player 2 - Small blind (10¢)

        Player 3 - Big blind (25¢)

        Start of betting round = betCheckFold();

        Den som är SIST!!!! i våran arraylista av spelare har "knappen".
        När varje runda är slut så roterar man listan en gång
        Man borde inte rotera på tableplayer?
        eller så får man rotera två gånger, tableplayer och en computer/human player.
         */
        //NON WORKIN:
        boolean smallBlindPlayer = true;
        boolean bigBlindPlayer = true;
        for(Player onePlayer : players) {
            //skip TablePlayer betting
            if (onePlayer instanceof TablePlayer) {continue;}
            if(onePlayer.getMoney() >= stake/2 && smallBlindPlayer){
                //find table
                findTable().addMoney(onePlayer.withdrawMoney(stake/2));
                smallBlindPlayer = false;
            }else if(onePlayer.getMoney() >= stake && bigBlindPlayer){
                findTable().addMoney(onePlayer.withdrawMoney(stake));
                bigBlindPlayer = false;
                onePlayer.setStillInGame(true);
            }
            //here is betCheckFold.. and continuing on the next player...

        }


        //lets check,raise or fold:
        //betCheckFold();

        //lets deal the flop.

        //lets check,raise or fold:
        //betCheckFold();
        //lets deal the fourth card.

        //lets check,raise or fold:
        //betCheckFold();
        //lets deal the fourth card.

        //lets check,raise or fold:
        //betCheckFold();
        //round over!

/*        p1.addMoney(200);
        p1.addCard(theDeck.dealCard());
        System.out.println(p1.toString());
        */
    }
}
