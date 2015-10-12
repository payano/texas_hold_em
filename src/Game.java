import CardPackage.Deck;
import PlayerPackage.HumanPlayer;
import PlayerPackage.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Arvid on 2015-09-15.
 *
 */
public class Game {

    private ArrayList<Player> players;
    private final int stake = 50; //this is the minimum bet for all rounds in the game.
    //computer;
    Deck theDeck;
    Scanner scan = new Scanner(System.in);
    public Game() {
        theDeck = new Deck();
        players = new ArrayList<Player>();
        //add players temporary:
        players.add(new TablePlayer("TheTable"));
        players.add(new HumanPlayer("Arvid",1080));
        players.add(new HumanPlayer("Tratten", 550));
        players.add(new HumanPlayer("TrattVald", 1337));
        players.add(new HumanPlayer("Johan", 500));
        //One player that is not a blind holder.
//        players.add(new ComputerPlayer("SuperAI"));
//        players.get(3).addMoney(100);

    }

    private void setWinner(){
        int currentLeader = 0;
        boolean firstRun = true;

        //check highest handValue.
        for(int i = 0; i < players.size();i++){
            //table can not win.
            if(players.get(i) instanceof TablePlayer){continue;}
            //player must still be in game to win
            if(!players.get(i).getStillInGame()){continue;}
            //get the first time leader
            //System.out.println("player: " + players.get(i).getName() + ", handValue: " + players.get(i).getHandValue().getValue());

            System.out.println("player: " + players.get(i).getName() + ", handPoints: " + players.get(i).getHandPoints() + " CardValue: " + players.get(i).getHandValue());
            if(firstRun){currentLeader = i;firstRun=false;continue;}
            if(players.get(i).getHandPoints() > players.get(currentLeader).getHandPoints()){
                currentLeader = i;
            }else if(players.get(i).getHandPoints() == players.get(currentLeader).getHandPoints()){
                System.out.println("HEY MAN THIS IS A SPLIT!!");
            }
        }

        //check if more players has the same handValue.
        System.out.println("the winner is..... " + players.get(currentLeader).getName());
        System.out.println("he/she has: " + players.get(currentLeader).getHandValue().toString());
    }
    private void getHandValues(){
        for(Player onePlayer : players){
            if(onePlayer instanceof TablePlayer){continue;}
            //add the Table hand to the player hands.
            onePlayer.addCard(findTable().getCards());
            onePlayer.sortCardsByRank();
            //onePlayer.setHandValue();
            onePlayer.getBestHand();
        }
    }
    //new version.
    private void betRound(){
        //go to the person to the left of the highest bidder.
        //getHighestBidder is the current bidder.
        for(int i = getHighestBidder()+1; ;i++){

            //if the counter gets larger than size make it zero.
            if(i >= players.size()){i = 0;}
            if(players.get(i) instanceof TablePlayer){
                if(players.get(i).getHighestBid()){
                    //if every player checked.
                    break;
                }
                continue;
            }
            if(!players.get(i).getStillInGame()){continue;}
            if(players.get(i) instanceof HumanPlayer){
                //magic happens here
                System.out.println("HIHGEST BIDDER: " + getHighestBidder());
                System.out.println("CURRENT ID: " + i);
                System.out.println("BIG BLIND: " + players.get(i).getBigBlind());
                if(players.get(i).getHighestBid() == true &&  players.get(i).getBigBlind()){
                    //if the player is the current highest bidder and the player has the bigblind
                    //if there is only one player left. He is the winner.
                    if(!winner(players.get(i))) betCheckFold(players.get(i));
                    else {
                        System.out.printf(players.get(getHighestBidder()) + " wins!!");
                        //Take the money from the table and give it to the winner.
                        players.get(i).addMoney(findTable().withdrawMoney(findTable().getMoney()));
                    }
                    players.get(i).setBigBlind(false);
                    if(players.get(i).getRoundBet() > stake){
                        //player placed a bet.
                        continue;
                    }
                }
                if(players.get(i).getHighestBid()){
                    //if current player is the highest bidder
                    break;
                }
                if(!winner(players.get(i))) betCheckFold(players.get(i));
                else {
                    System.out.printf(players.get(getHighestBidder()) + " wins!!");
                    //Take the money from the table and give it to the winner.
                    players.get(i).addMoney(findTable().withdrawMoney(findTable().getMoney()));
                }
            }
            //Have to chen if there is a winner here if the last player of that
            //"lap" folded.
            if (winner(players.get(i))){
                System.out.printf(players.get(i).getName() + " wins!!");
                //Take the money from the table and give it to the winner.
                players.get(i).addMoney(findTable().withdrawMoney(findTable().getMoney()));
            }
        }
        //ROUND x is done and done!
        //setup some things for the next round
        //make table the highest bidder
        //reset the bets:
        for(Player onePlayer : players){
            onePlayer.resetRoundBet();
        }
        System.out.println("ROUND OVER!");
        setHighestBidder(findTable());

    }

    public boolean winner(Player mightBeWinner){
        int numberOfplayersLeft = 0;

        //add all the players that are still in the game.
        for (Player onePLayer : players) {
            if (onePLayer.getStillInGame()) numberOfplayersLeft++;
        }

        //if there is more than one player and the table left. No one has won yet.
        if (numberOfplayersLeft > 2){
            System.out.println(numberOfplayersLeft);
            return false;
        }
        return true;
    }

    public void smallAndBigBlind(ArrayList<Player> players){
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

                //onePlayer.setSmallBlind(true);
                //muppigt?
                setHighestBidder(onePlayer);

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

                //same as above...
                setHighestBidder(onePlayer);
                onePlayer.setBigBlind(true);

                break;
            }else {
                //ghetto solution
                throw new SmallAndBigBlindException("Small and big blind could not be taken from players on the table.");
            }
        }

    }

    public void setHighestBidder(Player highestPlayer){
        for(Player onePlayer : players){
            onePlayer.setHighestBid(false);
        }
        highestPlayer.setHighestBid(true);
    }
    public int getHighestBidder(){
        for(int i = 0; i < players.size();i++){
            if(players.get(i).getHighestBid()){
                return i;
            }
        }
        throw new HighestBidderNotFoundException("Strange, there is no highest bidder..");
    }

    public void betCheckFold(Player onePlayer){
        System.out.println("\nyour share in this bettingRound so far: " + onePlayer.getRoundBet());
        System.out.println("Table has: " + findTable().getMoney() + " money , the roundbet is: "+ findTable().getRoundBet());
        System.out.println("You need to bet at least: " + (findTable().getRoundBet()-onePlayer.getRoundBet()));
        System.out.println(findTable().toString());
        System.out.println(onePlayer.toString());
        System.out.println(onePlayer.getName() + " what do you want to do? 0:check, 1:call, 2:bet, 3:all-in, 4:fold");
        switch (scan.nextInt()){
            case 0:
                //check
                //You cant check if someone has bet more than you have put in.
                if(onePlayer.getRoundBet() < findTable().getRoundBet()){
                    System.out.println("You can't call, someone has placed a bet this round. You need to bet: " + (findTable().getRoundBet()-onePlayer.getRoundBet()));
                    System.out.println("Do you want to bet or fold? : 1:call 2:bet, 4:fold");
                    switch (scan.nextInt()){
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
                            bet(onePlayer);
                            break;
                        case 4:
                            System.out.println("folded...");
                            onePlayer.setStillInGame(false);
                    }
                }
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
                bet(onePlayer);
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

    public void dealCards(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            for (Player onePlayer : players) {
                if (onePlayer instanceof TablePlayer) {
                    continue;
                }
                if (!onePlayer.getStillInGame()) {
                    continue;
                }
                //Give each player one card at a time.
                onePlayer.addCard(theDeck.dealCard());
                System.out.println(onePlayer.toString());
            }
        }
    }

    public void dealTable(int numberOfCards) {
        //johan har meckat här, fungerar ej.
        for (int i = 0; i < numberOfCards; i++) {
            for (Player onePlayer : players) {
                if (onePlayer instanceof HumanPlayer) {
                    continue;
                }
                if (!onePlayer.getStillInGame()) {
                    continue;
                }
                //Give the table its 2 rivercards.
                onePlayer.addCard(theDeck.dealCard());
                System.out.println(onePlayer.toString());
            }
        }
    }

    public void bet(Player onePlayer){
        //this is beta release or .. alpha? :=)

        System.out.println("how much? minimum is: " + (stake - onePlayer.getRoundBet()));
        double bettedMoney = scan.nextInt();
        //Make sure the user bet at least the steaks.
        while (bettedMoney < (stake - onePlayer.getRoundBet()) ){
            System.out.println("You need to bet at least: " + stake + "\nTry again, bet: ");
            bettedMoney = scan.nextInt();
        }
        //if you raise before you have paid big blind, you have to pay for that aswell.
        findTable().addMoney(onePlayer.withdrawMoney(bettedMoney));
        findTable().addRoundBet(bettedMoney);
        //update the round bet
        onePlayer.addRoundBet(bettedMoney);
        //stake should be the same all the time!
        //stake += bettedMoney;
        setHighestBidder(onePlayer);

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

    public void rotatePlayers(){
        //we need one list for the bets and one list for the table seating.
        //rotate players will be used for making the bets go around all players.
        System.out.println("\nBEFORE ROTATE");

        for (Player onePlayer: players){
            System.out.println(onePlayer.toString());
        }

        System.out.println("\nROTATING BEEP BOOP");

        for(int i = 0 ; i < players.size(); i++){
            if(players.get(i) instanceof TablePlayer){continue;}
            players.add(players.remove(i));
            break;

        }

        for(Player onePlayer: players){
            System.out.println(onePlayer.toString());
        }
    }

    public void start(){

        System.out.println("SHUFFLING CARDS...");
        theDeck.shuffleCards();


        System.out.println("Press :1 to start.");

//        while (scan.nextInt() == 1) {
            //make all players still in game and enabled to check

            for (Player onePlayer : players) {
                onePlayer.setStillInGame(true);
            }

            //Give the blinds.
            smallAndBigBlind(players);
            //Deal the players 2 cards each. One at a time.
            dealCards(2);

            //lets bet!
            //betRound();

            //Deal the flop
            dealTable(3);

            //lets bet!
            //betRound();

            //Deal one more card to the table
            dealTable(1);

            //Lets bet!
            //betRound();

            //Deal the river!
            dealTable(1);

            //lets bet!
            //betRound();


            System.out.println("\n\n\n\n");
            getHandValues();
            setWinner();

            //rotatePlayers();

            for (Player onePlayer : players) {
                System.out.println(onePlayer.toString());
            }
/*
            System.out.println("Want to play another round? 1:yes ");
        }
    */

        //Deal the river
        //dealRiver();

        //Lets bet again!
        //betRound();

        System.out.println("SLUT");


        //this is le beta
        //players.get(1).getBestHand();

    }
}
