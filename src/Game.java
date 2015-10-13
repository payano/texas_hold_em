import CardPackage.*;
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
    private final int stake = 50; //this is the minimum bet for all rounds in the game.
    private Deck theDeck;
    private Scanner scan = new Scanner(System.in);
    private ArrayList<Boolean> stillInGame,highestBid,bigBlind;
    private ArrayList<CardValueEnum> handValue;
    private ArrayList<Double> roundBet; //change to gameeBet later...
    private ArrayList<Integer> handPoints;



    public Game() {
        theDeck = new Deck();
        players = new ArrayList<Player>();
        stillInGame = new ArrayList<Boolean>();
        highestBid = new ArrayList<Boolean>();
        bigBlind = new ArrayList<Boolean>();
        handValue = new ArrayList<CardValueEnum>();
        roundBet = new ArrayList<Double>();
        handPoints = new ArrayList<Integer>();
        //add players temporary:
        players.add(new TablePlayer("TheTable"));
        players.add(new HumanPlayer("Arvid",1080));
        players.add(new HumanPlayer("Tratten", 550));
        players.add(new HumanPlayer("TrattVald", 1337));
        players.add(new HumanPlayer("Johan", 500));

        //initiate arraylists for users
        for (int i = 0; i < players.size() ; i++) {
            //creating seperate lists for each player to keep track of them.
            stillInGame.add(false);
            highestBid.add(false);
            bigBlind.add(false);
            handValue.add(CardValueEnum.None);
            roundBet.add(0.0);
            handPoints.add(0);
        }

    }

    private boolean getStillInGame(int i){return stillInGame.get(i);}
    private void setStillInGame(int i,boolean value){stillInGame.set(i,value);}
    private int getHandPoints(int i){return handPoints.get(i);}
    private CardValueEnum getHandValue(int i){return handValue.get(i);}
    public void setHighestBidder(int highestPlayer){
        for(int i = 0 ; i < players.size();i++){
            highestBid.set(i,false);
        }
        highestBid.set(highestPlayer,true);
    }
    public boolean getHighestBid(int i){return highestBid.get(i);}
    public int getHighestBidder(){
        for(int i = 0; i < players.size();i++){
            if(highestBid.get(i)){
                return i;
            }
        }
        throw new HighestBidderNotFoundException("Strange, there is no highest bidder..");
    }
    public void setWinner(){
        int currentLeader = 0;
        boolean firstRun = true;

        //check highest handValue.
        for(int i = 0; i < players.size();i++){
            //table can not win.
            if(players.get(i) instanceof TablePlayer){continue;}
            //player must still be in game to win
            if(!getStillInGame(i)){continue;}
            //get the first time leader
            System.out.println("player: " + players.get(i).getName() + ", handPoints: " + getHandPoints(i) + " CardValue: " + getHandValue(i));
            if(firstRun){currentLeader = i;firstRun=false;continue;}
            if(getHandPoints(i) > getHandPoints(currentLeader)){
                currentLeader = i;
            }else if(getHandPoints(i) == getHandPoints(currentLeader)){
                System.out.println("HEY MAN THIS IS A SPLIT!!");
            }
        }
        //check if more players has the same handValue.
        System.out.println("the winner is..... " + players.get(currentLeader).getName());
        System.out.println("he/she has: " + getHandValue(currentLeader).toString());
    }
    //this is the old getHandValues
    //should be setHandValues
    public void setBestHandPoints(int playerId){
        throw new SmallAndBigBlindException("JUST SOME BOGUS");
    }
    public int getBestHandPoints(int playerId){
        return handPoints.get(playerId);
    }
    public void setHandValues(){
        //set Hand Values for all players.
        for(int i = 0; i < players.size();i++){
            if(players.get(i) instanceof TablePlayer){continue;}
            //add the Table hand to the player hands.
            players.get(i).addCard(players.get(findTable()).getCards());
            players.get(i).sortCardsByRank();
            setHandPoints(i);
        }
    }
    public void betRound(){
        //go to the person to the left of the highest bidder.
        //getHighestBidder is the current bidder.
        for(int i = getHighestBidder()+1; ;i++){

            //if the counter gets larger than size make it zero.
            if(i >= players.size()){i = 0;}
            if(players.get(i) instanceof TablePlayer){
                if(getHighestBid(i)){
                    //if every player checked.
                    break;
                }
                continue;
            }
            if(!getStillInGame(i)){continue;}
            if(players.get(i) instanceof HumanPlayer){
                //magic happens here
                System.out.println("HIHGEST BIDDER: " + getHighestBidder());
                System.out.println("CURRENT ID: " + i);
                System.out.println("BIG BLIND: " + getBigBlind(i));
                if(getHighestBid(i) &&  getBigBlind(i)){
                    //if the player is the current highest bidder and the player has the bigblind
                    //if there is only one player left. He is the winner.
                    if(!winner()){ betCheckFold(i);}
                    else {
                        System.out.printf(players.get(getHighestBidder()) + " wins!!");
                        //Take the money from the table and give it to the winner.
                        players.get(i).addMoney(players.get(findTable()).withdrawMoney(players.get(findTable()).getMoney()));
                    }
                    setBigBlind(i);
                    if(getRoundBet(i) > stake){
                        //player placed a bet.
                        continue;
                    }
                }
                if(getHighestBid(i)){
                    //if current player is the highest bidder
                    break;
                }
                if(!winner()) betCheckFold(i);
                else {
                    System.out.printf(players.get(getHighestBidder()) + " wins!!");
                    //Take the money from the table and give it to the winner.
                    players.get(i).addMoney(players.get(findTable()).withdrawMoney(players.get(findTable()).getMoney()));
                }
            }
            //Have to chen if there is a winner here if the last player of that
            //"lap" folded.
            if (winner()){
                System.out.printf(players.get(i).getName() + " wins!!");
                //Take the money from the table and give it to the winner.
                players.get(i).addMoney(players.get(findTable()).withdrawMoney(players.get(findTable()).getMoney()));
            }
        }
        //ROUND x is done and done!
        //setup some things for the next round
        //make table the highest bidder
        //reset the bets:
        for(int i = 0 ; i < players.size();i++){
            resetRoundBet(i);
        }
        System.out.println("ROUND OVER!");
        setHighestBidder(findTable());

    }

    //replace with something like getPlayersInGame().
    public boolean winner(){
        int numberOfplayersLeft = 0;

        //add all the players that are still in the game.
        for (int i = 0; i < players.size();i++) {
            if (getStillInGame(i)) numberOfplayersLeft++;
        }

        //if there is more than one player and the table left. No one has won yet.
        if (numberOfplayersLeft > 2){
            System.out.println(numberOfplayersLeft);
            return false;
        }
        return true;
    }

    public void smallAndBigBlind(){
        //there has to be a check somewhere that the minimum amount of players(computer + human) >= 2.
        boolean smallBlindPlayer = true;
        for(int i = 0; i < players.size();i++) {
            //skip TablePlayer betting
            if (players.get(i) instanceof TablePlayer) {continue;}
            //kommmer detta fungera?
            if(players.get(i).getMoney() < stake/2 && smallBlindPlayer){
                //the player is broke, next person must take the smallblind.
                setStillInGame(i, false);
            }else if(players.get(i).getMoney() >= stake/2 && smallBlindPlayer){
                //player has taken small blind and is still in game.
                //this player must also call the stake or the bet.
                players.get(findTable()).addMoney(players.get(i).withdrawMoney(stake / 2));
                setStillInGame(i,true);
                smallBlindPlayer = false;
                //add the amount to roundBet.
                setRoundBet(i,(stake/2)+getRoundBet(i));
                System.out.println("Player: " + players.get(i).getName() + " got small blind, amount: " + stake / 2);
                setHighestBidder(i);
            }else if(players.get(i).getMoney() < stake ){
                //the player is broke, next person must take the big-blind.
                setStillInGame(i,false);
            }else if(players.get(i).getMoney() >= stake ){
                //player has taken the big blind and is still in game.
                players.get(findTable()).addMoney(players.get(i).withdrawMoney(stake));
                setStillInGame(i,true);
                //add the amount to roundbet.
                setRoundBet(i,getRoundBet(i)+stake);
                setRoundBet(findTable(),getRoundBet(findTable())+stake);
                System.out.println("Player: " + players.get(i).getName() + " got big blind, amount: " + stake);
                System.out.println("Table has: " + players.get(findTable()).getMoney() + " money and getRoundBet: " + getRoundBet(findTable()));
                //same as above...
                setHighestBidder(i);
                setBigBlind(i);
                break;
            }else {
                throw new SmallAndBigBlindException("Small and big blind could not be taken from players on the table.");
            }
        }

    }
    public void setBigBlind(int playerId){
        //only one can be BB
        for(int i = 0 ; i < players.size();i++){
            bigBlind.set(i,false);
        }
        bigBlind.set(playerId, true);
    }
    public boolean getBigBlind(int playerId){
        if(bigBlind.get(playerId)){
            return true;
        }
        return false;
    }
    public void betCheckFold(int playerId){
        System.out.println("\nyour share in this bettingRound so far: " + getRoundBet(playerId));
        System.out.println("Table has: " + players.get(findTable()).getMoney() + " money , the roundbet is: " + getRoundBet(findTable()));
        System.out.println("You need to bet at least: " + (getRoundBet(findTable()) - getRoundBet(playerId)));
        System.out.println(players.get(findTable()).toString());
        System.out.println(players.get(playerId).toString());
        System.out.println(players.get(playerId).getName() + " what do you want to do? 0:check, 1:call, 2:bet, 3:all-in, 4:fold");
        switch (scan.nextInt()){
            case 0:
                //check
                //You cant check if someone has bet more than you have put in.
                if(getRoundBet(playerId) < getRoundBet(findTable())){
                    System.out.println("You can't call, someone has placed a bet this round. You need to bet: " + (getRoundBet(findTable())-getRoundBet(playerId)));
                    System.out.println("Do you want to bet or fold? : 1:call 2:bet, 4:fold");
                    switch (scan.nextInt()){
                        case 1:
                            //call
                            //Table getRoundBet is current amount you have to call.
                            //player getRoundBet is how much you already betted.
                            //table.getRoundBet - player.getRoundBet gives the difference you have to call.
                            double difference = getRoundBet(findTable()) - getRoundBet(playerId);
                            //withdraw the amount and give it to the table
                            players.get(findTable()).addMoney(players.get(playerId).withdrawMoney(difference));
                            //update your getRoundBet
                            setRoundBet(playerId,difference+getRoundBet(playerId));
                            break;
                        case 2:
                            //bet
                            bet(playerId);
                            break;
                        case 4:
                            System.out.println("folded...");
                            setStillInGame(playerId,false);
                    }
                }
                break;
            case 1:
                //call
                //Table getRoundBet is current amount you have to call.
                //player getRoundBet is how much you already betted.
                //table.getRoundBet - player.getRoundBet gives the difference you have to call.
                double difference = getRoundBet(findTable()) - getRoundBet(playerId);
                //withdraw the amount and give it to the table
                players.get(findTable()).addMoney(players.get(playerId).withdrawMoney(difference));
                //update your getRoundBet
                setRoundBet(playerId, getRoundBet(playerId) + difference);
                break;
            case 2:
                //bet
                bet(playerId);
                break;
            case 3:
                //all in
                System.out.println("All in choosen. Betting: " + players.get(playerId).getMoney());
                System.out.println("split pot may happen here.. take care of it.");
                setRoundBet(findTable(),getRoundBet(findTable()) + players.get(playerId).getMoney());
                players.get(findTable()).addMoney(players.get(playerId).withdrawMoney(players.get(playerId).getMoney()));
                setStillInGame(playerId,true);
                break;
            case 4:
                //fold
                System.out.println("folded...");
                setStillInGame(playerId,false);
        }
    }


    //skillnaden mellan dealCards och dealTable är?
    //kan inte använda en funktion??
    public void dealCards(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            for (int j = 0;j < players.size();j++) {
                if (players.get(j) instanceof TablePlayer) {
                    continue;
                }
                if (!getStillInGame(j)) {
                    continue;
                }
                //Give each player one card at a time.
                players.get(j).addCard(theDeck.dealCard());
                System.out.println(players.get(j).toString());
            }
        }
    }

    public void dealTable(int numberOfCards) {
        //johan har meckat här, fungerar ej.
        for (int i = 0; i < numberOfCards; i++) {
            for (int j = 0; j < players.size();j++) {
                if (players.get(j) instanceof HumanPlayer) {
                    continue;
                }
                if (!getStillInGame(j)) {
                    continue;
                }
                //Give the table its 2 rivercards.
                players.get(j).addCard(theDeck.dealCard());
                System.out.println(players.get(j).toString());
            }
        }
    }

    public double getRoundBet(int playerId){
        return roundBet.get(playerId);
    }
    public void setRoundBet(int playerId, double amount){
        roundBet.set(playerId,amount);
    }
    public void resetRoundBet(int playerId){roundBet.set(playerId,0.0);}
    public void bet(int playerId){
        //this is beta release or .. alpha? :=)


        System.out.println("how much? minimum is: " + (stake - getRoundBet(playerId)));
        double bettedMoney = scan.nextInt();
        //Make sure the user bet at least the steaks.
        while (bettedMoney < (stake - getRoundBet(playerId)) ){
            System.out.println("You need to bet at least: " + stake + "\nTry again, bet: ");
            bettedMoney = scan.nextInt();
        }
        //if you raise before you have paid big blind, you have to pay for that aswell.
        players.get(findTable()).addMoney(players.get(playerId).withdrawMoney(bettedMoney));
        setRoundBet(findTable(), bettedMoney + getRoundBet(findTable()));
        //update the round bet
        setRoundBet(playerId, bettedMoney + getRoundBet(playerId));
        setHighestBidder(playerId);
    }

    private int findTable(){
        for(int i = 0 ; i < players.size();i++) {
            //find the table...
            if (players.get(i) instanceof TablePlayer) {
                return i;
            }
        }
        throw new NoPlayerInGameException("Can not find the table.");
    }

    public void rotatePlayers(){
        //rotate players one step
        for(int i = 0 ; i < players.size(); i++){
            if(players.get(i) instanceof TablePlayer){continue;}
            players.add(players.remove(i));
            break;
        }
    }

    public void start(){
        System.out.println("SHUFFLING CARDS...");
        theDeck.shuffleCards();


        System.out.println("Press :1 to start.");

//        while (scan.nextInt() == 1) {
            //make all players still in game and enabled to check

            for (int i = 0; i < players.size();i++) {
                setStillInGame(i,true);
            }

            //Give the blinds.
            //smallAndBigBlind(players);
            smallAndBigBlind();
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
            setHandValues();
            setWinner();

            //rotatePlayers();

            for (Player onePlayer : players) {
                onePlayer.sortCardsByRank();
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

    public Hand setHandPoints(int playerId){
        ArrayList<Hand> allPossibleHands = new ArrayList<Hand>();
        allPossibleHands.addAll(getAllHands(players.get(playerId).getPlayerHand()));
        int highestHand = 0;
        int highestHandId = 0;
        //loop through hands and get the best value.
        for (int i = 0; i < allPossibleHands.size();i++) {
            //
            if(setHandValue(allPossibleHands.get(i)) > highestHand){
                highestHandId = i;
                highestHand = setHandValue(allPossibleHands.get(i));
            }

        }
        handPoints.set(playerId,highestHand);

        return allPossibleHands.get(highestHandId);
    }
    private Suit_ checkFlush(Hand oneHand){
        int suitArray[] = new int[4];
        //populate rank and suit arrays:
        for(int i = 0; i < oneHand.getNoOfCards();i++){
            //add ace to first slot in the array aswell.
            suitArray[oneHand.getCard(i).getSuit()]++;
        }
        for(int i = 0 ; i < suitArray.length;i++){
            if(suitArray[i] == 5){
                for(Suit_ s : Suit_.values()){
                    if(s.getValue() == i) {
                        return s;
                    }
                }

            }
        }
        return null;
    }
    public CardValueEnum checkCardValue(Hand oneHand){
        int straightCount = 0;
        int lastCardValue = 0;
        int lowestStraight = 0;
        int pairCount = 0;
        boolean threeOfAKind = false;
        int rankArray[] = new int[15];
        for(int i = 0; i < oneHand.getNoOfCards();i++){
            //add ace to first slot in the array aswell.
            if(oneHand.getCard(i).getRank() == 14){rankArray[1]++;}
            rankArray[oneHand.getCard(i).getRank()]++;
        }

        for(int i = 1;i < rankArray.length;i++){
            //check for straight here
            //check with ace=1 aswell
            if(lastCardValue == i-1 && rankArray[i] > 0) {
                straightCount++;
                if (lowestStraight == 0) {
                    lowestStraight = i;
                }
                if (straightCount == 5) {
                    //this is redundant....
                    //CHECK THIS WARNING!
                    return CardValueEnum.Straight;
                }
            }else {
                straightCount = 0;
                lowestStraight = 0;
            }
            lastCardValue = i;
            //end check for straight
            if(i == 1){continue;}
            //check for pair, twopair, three of a kind, four of a kind
            if(rankArray[i] == 2){
                pairCount++;
            }else if(rankArray[i] == 3){
                threeOfAKind = true;
            }else if(rankArray[i] == 4){
                return CardValueEnum.FourOfAKind;
            }
        }

        if(threeOfAKind && pairCount == 1) {
            return CardValueEnum.FullHouse;
        }else if(threeOfAKind){
            return CardValueEnum.ThreeOfAKind;
        }else if(pairCount == 1){
            return CardValueEnum.OnePair;
        }else if(pairCount == 2){
            return CardValueEnum.TwoPair;
        }
        return CardValueEnum.None;
    }
    //GAME SPECIFIC
    private Rank_ getLowestCardInStraight(Hand oneHand){
        //lets first check if there is a straight here:
        //check for straight here
        //check with ace=1 aswell
        int lastCardValue = 0;
        int straightCount = 0;
        int lowestStraight = 0;
        int rankArray[] = new int[15];
        for(int i = 0; i < oneHand.getNoOfCards();i++){
            //add ace to first slot in the array aswell.
            if(oneHand.getCard(i).getRank() == 14){rankArray[1]++;}
            rankArray[oneHand.getCard(i).getRank()]++;
        }
        for(int i=0; i < rankArray.length;i++){
            //check if there is a straight in the hand.
            if(lastCardValue == i-1 && rankArray[i] > 0){
                straightCount++;
                if(lowestStraight == 0){lowestStraight = i;}
                if(straightCount == 5){break;}
            }else{
                lowestStraight = 0;
                straightCount = 0;
            }
            lastCardValue = i;
        }
        //search for the lowest card.
        //and return a rank_
        for(Rank_ s : Rank_.values()){
            if(s.getValue() == lowestStraight){
                return s;
            }
        }
        throw new NoCardValueStraightException("There is no straight in the list.");

    }
    private CardValueEnum checkRankAndSuitValue(Hand oneHand){
        CardValueEnum cardRank;
        Suit_ flush;
        //check if flush is set:
        flush = checkFlush(oneHand);
        cardRank = checkCardValue(oneHand);

        if(cardRank == CardValueEnum.Straight && flush != null){
            //Jackpot! both straight and flush!
            cardRank = CardValueEnum.StraightFlush;
            //hardcoded royal here:
            if(flush == Suit_.Hearts && getLowestCardInStraight(oneHand) == Rank_.Ten){
                //sweet jesus, you are a lucky soul.
                cardRank = CardValueEnum.RoyalFlush;
            }
        }else if(flush != null){
            cardRank = CardValueEnum.Flush;
        }
        return cardRank;
    }
    //GAME SPECIFIC
    private ArrayList<Rank_> getMatchingCards(Hand oneHand,int numberOfMCards,int occurrences){
        //returns the highest matching pair, three of a kind or four of a kind
        ArrayList<Rank_> result = new ArrayList<Rank_>();
        int rankArray[] = new int[15];
        int match;
        int checkOccurences=0;
        //populate arraylist
        for(int i = 0; i < oneHand.getNoOfCards();i++){
            rankArray[oneHand.getCard(i).getRank()]++;
        }
        for(int i = rankArray.length-1 ; i > 0;i--){
            if(rankArray[i] == numberOfMCards){
                match = i;
                for(Rank_ s : Rank_.values()){
                    if(s.getValue() == match){
                        result.add(s);
                    }
                }
                checkOccurences++;
                if(checkOccurences == occurrences){
                    break;
                }
            }
        }
        if(checkOccurences != occurrences || result.size() != occurrences){
            throw new NoMatchingCardException("Occurences: " + occurrences + " did not match checkOccurences:" + checkOccurences);
        }
        return result;
    }

    //GAME SPECIFIC
    public int setHandValue(Hand oneHand){
        int handPoints = 0;
        ArrayList<Rank_> tempCardRanks = new ArrayList<Rank_>();
        CardValueEnum tempCardValueEnum;
        tempCardValueEnum = checkRankAndSuitValue(oneHand);

        switch (tempCardValueEnum){
            case RoyalFlush:
                handPoints += 230000000;
                //TBD
                break;
            case StraightFlush:
                handPoints += 15000000 * getLowestCardInStraight(oneHand).getValue();
                break;
            case FourOfAKind:
                //handPoints += i * 264500; //four of a kind
                //handPoints += i; //fifth cared
                handPoints += 2000000 * getMatchingCards(oneHand,4,1).get(0).getValue();
                handPoints += getMatchingCards(oneHand,1,1).get(0).getValue();
                break;
            case FullHouse:
                //handPoints += 264000 * i; //three of a kind
                //handPoints += 10 * i; //highest pair
                handPoints += 264000 * getMatchingCards(oneHand,3,1).get(0).getValue();
                handPoints += 10 * getMatchingCards(oneHand,2,1).get(0).getValue();
                break;
            case Flush:
                //handPoints += 527000; //for flush
                //handPoints += i; //add the 5 cards together
                handPoints += 527000;
                //add the values for each card to handpoints
                for(int i = 0; i < oneHand.getNoOfCards();i++){
                    handPoints += oneHand.getCard(i).getRank();
                }
                break;
            case Straight:
                //handPoints += 52600 * lowestStraight;
                handPoints += 52600 * getLowestCardInStraight(oneHand).getValue();
                break;
            case ThreeOfAKind:
                //handPoints += 7500 * i; //for three of a kind
                //handPoints += i; //add the two other cards
                handPoints += 7500 * getMatchingCards(oneHand,3,1).get(0).getValue();
                tempCardRanks.addAll(getMatchingCards(oneHand,1,2));
                for(int i = 0; i < oneHand.getNoOfCards();i++){
                    handPoints += oneHand.getCard(i).getRank();
                }
                break;
            case TwoPair:
                //handPoints += 1000 * i; //highest pair
                //handPoints += 10 * i; //lowest pair
                //handPoints += i //last card
                tempCardRanks.addAll(getMatchingCards(oneHand,2,2));
                handPoints += 1000 * tempCardRanks.get(0).getValue();
                handPoints += 10 * tempCardRanks.get(1).getValue();
                handPoints += getMatchingCards(oneHand,1,1).get(0).getValue();
                break;
            case OnePair:
                //handPoints += 40*i; //for the pair
                //handPoints += i //for the rest of the cards
                handPoints += 40 * getMatchingCards(oneHand,2,1).get(0).getValue();
                tempCardRanks.addAll(getMatchingCards(oneHand,1,3));
                for(int i = 0; i < oneHand.getNoOfCards();i++){
                    handPoints += oneHand.getCard(i).getRank();
                }
                break;
            case None:
                //handPoints += i //for all five cards
                tempCardRanks.addAll(getMatchingCards(oneHand,1,5));
                for(int i = 0; i < oneHand.getNoOfCards();i++){
                    handPoints += oneHand.getCard(i).getRank();
                }
                break;
        }
        //DO MAGIC here..
        //oneHand.setHandPoints(handPoints);
        return handPoints;
    }
    private ArrayList<Hand> getAllHands(Hand playerHand){
        ArrayList<Hand> allPossibleHands = new ArrayList<Hand>();
        //get all possible hands from generateHands.
        allPossibleHands.addAll(generateHands(playerHand));
        return allPossibleHands;
    }
    //GAME SPECIFIC
    private ArrayList<Hand> generateHands(Hand playerHand){
        //this generates 21 hands.
        //http://stackoverflow.com/questions/8375452/how-to-loop-through-all-the-combinations-of-e-g-48-choose-5
        //for loop stolen:
        ArrayList<Hand> allPossibleHands = new ArrayList<Hand>();
        Hand tempHand;
        int n = 7; //might change in tha future...
        for ( int i = 0; i < n; i++ ) {
            for ( int j = i + 1; j < n; j++ ) {
                for ( int k = j + 1; k < n; k++ ) {
                    for (int l = k + 1; l < n; l++) {
                        for (int m = l + 1; m < n; m++) {
                            //generate hands:
                            tempHand = new Hand();
                            tempHand.addCard(playerHand.getCard(i));
                            tempHand.addCard(playerHand.getCard(j));
                            tempHand.addCard(playerHand.getCard(k));
                            tempHand.addCard(playerHand.getCard(l));
                            tempHand.addCard(playerHand.getCard(m));

                            //try to get the list in order:
                            tempHand.sortCardsBySuit();
                            tempHand.sortCardsByRank();
                            tempHand.sortCardsBySuit();
                            tempHand.sortCardsByRank();

                            allPossibleHands.add(tempHand);
                        }
                    }
                }
            }
        }
        return allPossibleHands;
    }





}
