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
        players.get(1).addMoney(180);

        players.add(new HumanPlayer("Arvid"));
        players.get(2).addMoney(550);
//        players.add(new ComputerPlayer("SuperAI"));
//        players.get(3).addMoney(100);

    }


    private void betRound(){
        boolean allPlayersChecked = false;
        while(!allPlayersChecked){
            for(Player onePlayer : players){
                //skip TablePlayer betting
                if(onePlayer instanceof TablePlayer){continue;}
                if(!onePlayer.getStillInGame()){continue;}
                if(onePlayer instanceof HumanPlayer){

                    betCheckFold(onePlayer);

                }
            }

            /*
            for(Player onePlayer : players){
                if(onePlayer instanceof TablePlayer){continue;}
                if(!onePlayer.getStillInGame()){continue;}
                if(onePlayer.getRoundBet() == findTable().getRoundBet()){
                    //the player has checked and its good.
                    System.out.println("here?");
                    allPlayersChecked = true;
                }else{
                    //the player has not checked, the round is not over yet.
                    System.out.println("there?");
                    allPlayersChecked = false;
                    break;
                }
            }*/
        }
    }
    public void smallAndBigBlind(){
        //there has to be a check somewhere that the minimum amount of players(computer + human) >= 2.
        //WARNING
        //Potential error: only two players but they dont have enough money to play.
        //a check to see if there is atleast two valid players with enough money must be checked.
        /*
        Example Betting Round 1
        There are five players at the table:
        Player 1 - Button <= Last player
        Player 2 - Small blind (10�) <= first player in arraylist
        Player 3 - Big blind (25�) <= second player in arraylist
        */
        boolean smallBlindPlayer = true;
        boolean bigBlindPlayer = true;
        for(Player onePlayer : players) {
            //skip TablePlayer betting
            if (onePlayer instanceof TablePlayer) {continue;}
            //kommmer detta fungera?
            if(onePlayer.getMoney() < stake/2 && smallBlindPlayer){
                //the player is broke, next person must take the smallblind.
                onePlayer.setStillInGame(false);
            }else if(onePlayer.getMoney() >= stake/2 && smallBlindPlayer){
                //player has taken small blind and is still in game.
                //this player must also call the stake or the bet.
                findTable().addMoney(onePlayer.withdrawMoney(stake / 2));
                onePlayer.setStillInGame(true);
                smallBlindPlayer = false;
                //add the amount to roundBet.
                onePlayer.addRoundBet(stake/2);

                System.out.println("Player: " + onePlayer.getName() + " got small blind, amount: " + stake / 2);
                //System.out.println("Table has: " + findTable().getMoney() + " money and getRoundBet: " + findTable().getRoundBet());

            }else if(onePlayer.getMoney() < stake && bigBlindPlayer){
                //the player is broke, next person must take the big-blind.
                onePlayer.setStillInGame(false);
            }else if(onePlayer.getMoney() >= stake && bigBlindPlayer){
                //player has taken the big blind and is still in game.
                findTable().addMoney(onePlayer.withdrawMoney(stake));
                //bigBlindPlayer = false;
                onePlayer.setStillInGame(true);
                //add the amount to roundbet.
                onePlayer.addRoundBet(stake);
                findTable().addRoundBet(stake);
                System.out.println("Player: " + onePlayer.getName() + " got big blind, amount: " + stake);
                System.out.println("Table has: " + findTable().getMoney() + " money and getRoundBet: " + findTable().getRoundBet());
                break;
            }else {
                //ghetto solution
                throw new SmallAndBigBlindException("Small and big blind could not be taken from players on the table.");
            }
        }

    }

    public void betCheckFold(Player onePlayer){
        System.out.println("\n\nMoney left: " + onePlayer.getMoney());
        System.out.println("your share in this bettingRound so far: " + onePlayer.getRoundBet());
        System.out.println("Table has: " + findTable().getMoney() + " money and getRoundBet: " + findTable().getRoundBet());
        System.out.println("What do you want to do " + onePlayer.getName() + " : 0:check, 1:call, 2:bet, 3:all-in, 4:fold");
        switch (scan.nextInt()){
            case 0:
                //check
                break;
            case 1:
                //call
                //Table getRoundBet is current amount you have to call.
                //player getRoundBet is how much you already betted.
                //table.getRoundBet - player.getRoundBet gives the difference you have to call.
                double difference = findTable().getRoundBet() - onePlayer.getRoundBet();
                //System.out.println("the difference is: " + difference);
                //withdraw the amount and give it to the table
                findTable().addMoney(onePlayer.withdrawMoney(difference));
                //update your getRoundBet
                onePlayer.addRoundBet(difference);
                break;
            case 2:
                //bet
                System.out.println("how much? minimum is: " + stake);
                //check if is less than 50....
                double bettedMoney = scan.nextInt();
                //if you raise before you have paid big blind, you have to pay for that aswell.
                findTable().addMoney(onePlayer.withdrawMoney(bettedMoney));
                findTable().addRoundBet(bettedMoney);
                //update the round bet
                onePlayer.addRoundBet(bettedMoney);
                break;
            case 3:
                //all in
                System.out.println("All in choosen. Betting: " + onePlayer.getMoney());

                findTable().addRoundBet(onePlayer.getMoney());
                findTable().addMoney(onePlayer.withdrawMoney(onePlayer.getMoney()));
                onePlayer.setStillInGame(true);
                break;
            case 4:
                //fold
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

        //make all players still in game and enabled to check
        for(Player onePlayer : players){
            onePlayer.setStillInGame(true);
            onePlayer.setAllowedToCheck(true);
        }



        smallAndBigBlind();
        //lets bet!
        betRound();


        //first step, check who is interested.
        //first player in arraylist must pay smallblind: 25, the rest must pay bigblind, raise or fold.
        //the first player must also make a choice, fold, call or raise.

        //first time is special:
        /*



        Start of betting round = betCheckFold();

        Den som �r SIST!!!! i v�ran arraylista av spelare har "knappen".
        N�r varje runda �r slut s� roterar man listan en g�ng
        Man borde inte rotera p� tableplayer?
        eller s� f�r man rotera tv� g�nger, tableplayer och en computer/human player.
         */
        //NON WORKIN:



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
