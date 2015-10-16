import CardPackage.*;
import PlayerPackage.HumanPlayer;
import PlayerPackage.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Arvid and Johan Svensson on 2015-09-15.
 *
 */
public class GameModel {

    //private datamembers in game class:
    private ArrayList<Player> players;
    private final int stake = 50; //this is the minimum bet for all rounds in the game.
    private Deck theDeck;
    private Scanner scan = new Scanner(System.in);
    private ArrayList<Boolean> stillInGame;
    private int highestBetPlayerId,bigBlind;
    private ArrayList<CardValueEnum> handRank;
    private ArrayList<Double> roundBet; //change to gameeBet later...
    private int currentPlayer;
    private GameStatusEnum roundStatus;



    public GameModel() {
        theDeck = new Deck();
        players = new ArrayList<Player>();
        stillInGame = new ArrayList<Boolean>();
        handRank = new ArrayList<CardValueEnum>();
        roundBet = new ArrayList<Double>();
        //add players temporary:
        players.add(new TablePlayer("TheTable"));
        players.add(new HumanPlayer("Arvid",1080));
        //players.add(new HumanPlayer("Tratten", 550));
        //players.add(new HumanPlayer("TrattVald", 1337));
        players.add(new HumanPlayer("Johan", 500));

        //initiate arraylists for users
        for (int i = 0; i < players.size() ; i++) {
            //creating seperate lists for each player to keep track of them.
            stillInGame.add(true);
            handRank.add(CardValueEnum.None);
            roundBet.add(0.0);
        }

    }

    public void setRoundStatus(GameStatusEnum status){roundStatus = status;}
    public GameStatusEnum getRoundStatus(){return roundStatus;}
    public boolean getStillInGame(int i){return stillInGame.get(i);}
    private void setStillInGame(int i,boolean value){stillInGame.set(i,value);}
    public int getHandPoints(int i){return players.get(i).getHandPoints();}
    public CardValueEnum getHandRank(int i){return handRank.get(i);}
    private void setHighestBetPlayerId(int playerId){
        if(playerId >= players.size()){
            throw new NoSuchPlayerException("no such player with id:" + playerId);
        }
        highestBetPlayerId = playerId;
    }
    public Player getPlayer(int playerId){
        return players.get(playerId);
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }
    private int getHighestBetPlayerId(){return highestBetPlayerId;}
    public int getPlayersInGame(){
        int numberOfplayersLeft = 0;
        //add all the players that are still in the game.
        for (int i = 0; i < players.size();i++) {
            if (players.get(i) instanceof TablePlayer){continue;}
            if (getStillInGame(i)){numberOfplayersLeft++;}
        }
        return numberOfplayersLeft;
    }
    public ArrayList<Integer> setWinner(){
        setHandValues();
        int highestHandPoints = 0;
        ArrayList<Integer> winner = new ArrayList<Integer>();

        //loop through the players and get the current leader:
        for (int i = 0 ; i < players.size();i++){
            if(getHandPoints(i) > highestHandPoints){
                //table can not win.
                if(players.get(i) instanceof TablePlayer){continue;}
                //player must still be in game to win
                if(!getStillInGame(i)){continue;}
                highestHandPoints = getHandPoints(i);
            }
        }
        //check if there is someone else who has the same handPoints as the leader
        for(int i = 0; i < players.size();i++){
            //table can not win.
            if(players.get(i) instanceof TablePlayer){continue;}
            //player must still be in game to win
            if(!getStillInGame(i)){continue;}
            //get the first time leader
            //System.out.println("player: " + players.get(i).getName() + ", handPoints: " + getHandPoints(i) + " CardValue: " + getHandRank(i));
            if(getHandPoints(i) == highestHandPoints){
                winner.add(i);
            }
        }

        for (int i = 0; i < winner.size(); i++) {
            players.get(winner.get(i)).addMoney(
                    players.get(findTable()).withdrawMoney(players.get(findTable()).getMoney() / winner.size()));
                    System.out.println("player: " + players.get(winner.get(i)).getName() + ", handPoints: " + getHandPoints(winner.get(i)) + " CardValue: " + getHandRank(winner.get(i)));
        }

        return winner;
        //check if more players has the same handRank.
    }
    public void setHandRank(int playerId, CardValueEnum cardValue){
        handRank.set(playerId, cardValue);}
    public void setHandValues(){
        //set Hand Values for all players.
        for(int i = 0; i < players.size();i++){
            if(players.get(i) instanceof TablePlayer){continue;}
            //add the Table hand to the player hands.
            players.get(i).addCard(players.get(findTable()).getCards());
            players.get(i).sortCardsByRank();
            //setHandPoints(i);
            System.out.println("spelare: " + players.get(i).getName() + " Kort: " + setHandPoints(i).toString());;
        }
    }
    private Hand setHandPoints(int playerId){
        ArrayList<Hand> allPossibleHands = new ArrayList<Hand>();
        allPossibleHands.addAll(getAllHands(players.get(playerId).getPlayerHand()));
        int highestHand = 0;
        int highestHandId = 0;
        //loop through hands and get the best value.
        for (int i = 0; i < allPossibleHands.size();i++) {
            //
            if(checkHandRank(allPossibleHands.get(i)) > highestHand){
                highestHandId = i;
                highestHand = checkHandRank(allPossibleHands.get(i));
            }

        }
        //handpoints and handvalues are closely connected, update them both here.
        players.get(playerId).setHandPoints(highestHand);
        setHandRank(playerId, checkCardValue(allPossibleHands.get(highestHandId)));
        return allPossibleHands.get(highestHandId);
    }
    public void smallAndBigBlind(){
        //there has to be a check somewhere that the minimum amount of players(computer + human) >= 2.
        boolean smallBlindPlayer = true;
        for(int i = 0; i < players.size();i++) {
            System.out.println("i: " + i + " ");
            //skip TablePlayer betting
            if (players.get(i) instanceof TablePlayer) {continue;}
            //kommmer detta fungera?
            if(players.get(i).getMoney() < stake/2 && smallBlindPlayer){
                //the player is broke, next person must take the smallblind.
                setStillInGame(i, false);
            }else if(players.get(i).getMoney() >= stake/2 && smallBlindPlayer){
                //player has taken small blind and is still in game.
                //this player must also call the stake or the bet.
                System.out.println("player:" + players.get(i).getName());
                players.get(findTable()).addMoney(players.get(i).withdrawMoney(stake / 2));
                setStillInGame(i,true);
                smallBlindPlayer = false;
                //add the amount to roundBet.
                setRoundBet(i,(stake/2)+getRoundBet(i));
                System.out.println("Player: " + players.get(i).getName() + " got small blind, amount: " + stake / 2);
                setHighestBetPlayerId(i);
            }else if(players.get(i).getMoney() < stake ){
                //the player is broke, next person must take the big-blind.
                setStillInGame(i,false);
            }else if(players.get(i).getMoney() >= stake){
                System.out.println("player:" + players.get(i).getName());
                //player has taken the big blind and is still in game.
                players.get(findTable()).addMoney(players.get(i).withdrawMoney(stake));
                setStillInGame(i,true);
                //add the amount to roundbet.
                setRoundBet(i,getRoundBet(i)+stake);
                setRoundBet(findTable(),getRoundBet(findTable())+stake);
                System.out.println("Player: " + players.get(i).getName() + " got big blind, amount: " + stake);
                System.out.println("Table has: " + players.get(findTable()).getMoney() + " money and getRoundBet: " + getRoundBet(findTable()));
                //same as above...
                setHighestBetPlayerId(i);
                setBigBlind(i);
                setNextPlayer(i);
                break;
            }else {
                throw new SmallAndBigBlindException("Small and big blind could not be taken from players on the table.");
            }
        }
    }

    public boolean roundComplete(){
        //tells controller if the round is done.
        //System.out.println("currentplayerid: " + getCurrentPlayerId() + " highestplayeid: " + highestBetPlayerId + " bigblind:" + getBigBlind());
        //System.out.println("player: " + getCurrentPlayer().getName());
        if(getBigBlind() == getCurrentPlayerId()) {
            //special case for first round with blinds.
            setBigBlind(findTable());
            setNextHigestBidPlayer();
        }else if(getCurrentPlayerId() == getHighestBetPlayerId()){
            //this is first round bigblind only!!
            setNextPlayer(findTable());
            setHighestBetPlayerId(findTable()+1);
            return true;
        }
            return false;
    }

    private void setBigBlind(int playerId){bigBlind = playerId;}
    private int getBigBlind(){return bigBlind;}

    public int getStake(){return stake;}
    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }
    public int getCurrentPlayerId(){
        return currentPlayer;
    }
    private void setNextHigestBidPlayer(){
        //just for the first round, this is a special case
        highestBetPlayerId++;
        int counter = 0;
        for (int i = highestBetPlayerId;  ; i++,counter++) {
            if(i >= players.size()){
                i=0;
            }
            //System.out.println("i:" + i + " bigblind:" + getBigBlind() + " highestBetPlayerId:" + highestBetPlayerId);
            if(players.get(i) instanceof TablePlayer){continue;}
            else if(counter >= 100){throw new NoPlayerInGameException("method highestBetPlayerId cannot set the next player, no players still in game!");}
            else if(!getStillInGame(i)){continue;}
            else{highestBetPlayerId = i;break;}
        }
        //System.out.println("Table has: " + players.get(findTable()).getMoney() + " money and getRoundBet: " + getRoundBet(findTable()));
    }
    public double getBetAmount(int playerId){
        return getRoundBet(findTable()) - getRoundBet(playerId);
    }

    public void setNextPlayer(){
        currentPlayer++;
        int counter = 0;
        for (int i = currentPlayer;  ; i++,counter++) {
            if(i >= players.size()){
                i=0;
            }
            //System.out.println("i:" + i + " bigblind:" + getBigBlind() + " highestbet:" + getHighestBetPlayerId());
            if(players.get(i) instanceof TablePlayer){continue;}
            else if(counter >= 100){throw new NoPlayerInGameException("method SetNextPlayer cannot set the next player, no players still in game!");}
            else if(!getStillInGame(i)){continue;}
            else{currentPlayer = i;break;}
        }
        //System.out.println("Table has: " + players.get(findTable()).getMoney() + " money and getRoundBet: " + getRoundBet(findTable()));
    }
    private int getNextPlayer(){return currentPlayer;}
    private void setNextPlayer(int nextPlayer){
        currentPlayer = nextPlayer;
        setNextPlayer();
    }
    public void bet(double betAmount){

        players.get(findTable()).addMoney(getCurrentPlayer().withdrawMoney(betAmount));
        setRoundBet(findTable(), betAmount + getRoundBet(findTable()));
        System.out.println("roundbet: " + getRoundBet(findTable()));
        setHighestBetPlayerId(currentPlayer);
        setNextPlayer();
    }
    public void check(){
        setNextPlayer();
    }
    public void call(){
        double difference = getRoundBet(findTable()) - getRoundBet(getCurrentPlayerId());
        //withdraw the amount and give it to the table
        players.get(findTable()).addMoney(players.get(getCurrentPlayerId()).withdrawMoney(difference));
        //update your getRoundBet
        setRoundBet(getCurrentPlayerId(), getRoundBet(getCurrentPlayerId()) + difference);
        setNextPlayer();
    }
    public void fold(){
        stillInGame.set(getCurrentPlayerId(),false);
            setNextPlayer();
    }
    public void dealCards(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            for (int j = 0;j < players.size();j++) {
                if (players.get(j) instanceof TablePlayer) {continue;}
                if (!getStillInGame(j)) {continue;}
                //Give each player one card at a time.
                players.get(j).addCard(theDeck.dealCard());
                System.out.println(players.get(j).toString());
            }
        }
    }
    public void dealTable(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            for (int j = 0; j < players.size();j++) {
                if (players.get(j) instanceof HumanPlayer) {continue;}
                if (!getStillInGame(j)) {continue;}
                //Give the table cards.
                players.get(j).addCard(theDeck.dealCard());
                System.out.println(players.get(j).toString());
            }
        }
    }
    public double getRoundBet(int playerId){
        return roundBet.get(playerId);
    }
    private void setRoundBet(int playerId, double amount){
        roundBet.set(playerId,amount);
    }
    public int findTable(){
        for(int i = 0 ; i < players.size();i++) {
            //find the table...
            if (players.get(i) instanceof TablePlayer) {
                return i;
            }
        }
        throw new NoPlayerInGameException("Can not find the table.");
    }

    private void rotatePlayers(){
        //rotate players one step
        for(int i = 0 ; i < players.size(); i++){
            if(players.get(i) instanceof TablePlayer){continue;}
            players.add(players.remove(i));
            break;
        }
    }

    public void initGame(){
        System.out.println("SHUFFLING CARDS...");
        theDeck.fillDeck();
        theDeck.shuffleCards();


        System.out.println("Press :1 to start.");

//        while (scan.nextInt() == 1) {
            //make all players still in game and enabled to check

            for (int i = 0; i < players.size();i++) {
                setStillInGame(i,true);
                players.get(i).removeAllCards();
                setRoundBet(i,0.0);
            }
            roundStatus = GameStatusEnum.PreFlop;
    }
    private Suit_ checkFlush(Hand oneHand){
        int suitArray[] = new int[Suit_.values().length];
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

    private CardValueEnum checkCardValue(Hand oneHand){
        int straightCount = 0;
        int lastCardValue = 0;
        int lowestStraight = 0;
        int pairCount = 0;
        boolean threeOfAKind = false;
        int rankArray[] = new int[Rank_.values().length+1];
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
    private Rank_ getLowestCardInStraight(Hand oneHand){
        //lets first check if there is a straight here:
        //check for straight here
        //check with ace=1 aswell
        int lastCardValue = 0;
        int straightCount = 0;
        int lowestStraight = 0;
        int rankArray[] = new int[Rank_.values().length+1];
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
    private ArrayList<Rank_> getMatchingCards(Hand oneHand,int numberOfMCards,int occurrences){
        //returns the highest matching pair, three of a kind or four of a kind
        ArrayList<Rank_> result = new ArrayList<Rank_>();
        int rankArray[] = new int[Rank_.values().length+1];
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
    private int checkHandRank(Hand oneHand){
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
        //oneHand.setHandRank(handPoints);
        return handPoints;
    }
    private ArrayList<Hand> getAllHands(Hand playerHand){
        ArrayList<Hand> allPossibleHands = new ArrayList<Hand>();
        //get all possible hands from generateHands.
        allPossibleHands.addAll(generateHands(playerHand));
        return allPossibleHands;
    }
    private ArrayList<Hand> generateHands(Hand playerHand){
        //this generates 21 hands.
        //n choose k => 7 choose 5
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
