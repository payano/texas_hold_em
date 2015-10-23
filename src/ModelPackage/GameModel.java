package ModelPackage;

import CardPackage.*;
import PlayerPackage.HumanPlayer;
import PlayerPackage.*;
import ViewPackage.GameView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * GameModel is the player logic in the game texas hold em.
 */
public class GameModel extends Observable implements Serializable {

    //private data members in game class:
    private ArrayList<Player> players;
    private final int stake = 1000; //this is the minimum bet for all rounds in the game.
    private Deck theDeck;
    private ArrayList<Boolean> stillInGame;
    private int highestBetPlayerId,bigBlind;
    private ArrayList<CardValueEnum> handRank;
    private ArrayList<Double> roundBet; //change to gameeBet later...
    private int currentPlayer,lastPlayer;
    private GameStatusEnum roundStatus;

    private ArrayList<Boolean> playerAllIn;


    /**
     * GameModel constructor - initializes the game
     */
    public GameModel() {
        theDeck = new Deck();
        players = new ArrayList<>();
        stillInGame = new ArrayList<>();
        handRank = new ArrayList<>();
        roundBet = new ArrayList<>();
        playerAllIn = new ArrayList<>();

        //add players temporary:
        players.add(new TablePlayer("TheTable"));
        players.add(new HumanPlayer("Arvid",50000));
        players.add(new HumanPlayer("Johan", 50000));

        //initiate array lists for users
        for (int i = 0; i < players.size() ; i++) {
            //creating seperate lists for each player to keep track of them.
            stillInGame.add(true);
            handRank.add(CardValueEnum.None);
            roundBet.add(0.0);
            playerAllIn.add(false);
        }

    }
    /**
     * createNewHands creates new hands for players
     * This is used when the game is loaded from a previous state.
     * the Hand is not serialized and is created as a null reference
     * This creates a hand for the players again.
     */
    public void createNewHands(){
        for(Player onePlayer : players){
            onePlayer.createNewHand();
        }
    }
    /**
     * This is from collection of books lab
     * saveGame saves the Player collection to file
     * @param filename is the filename to be saved
     * @throws IOException ObjectOutputStream might throw an exception that needs to be taken care of
     */
    public void saveGame(String filename) throws IOException {

        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(
                    new FileOutputStream(filename));
            out.writeObject(players);
        }
        finally {
            try {
                if(out != null)	out.close();
            } catch(Exception e) {
                //throw exception upwards
                throw new IOException(e);
            }
        }
    }
    /**
     * This is from collection of books lab
     * loadGame reads in the Player collection and stores it in an array list
     * @param filename is the file to be loaded
     * @throws IOException ObjectOutputStream might throw an exception that needs to be taken care of
     * @throws ClassNotFoundException might be thrown then readObject method is called
     */
    @SuppressWarnings("unchecked")
    public void loadGame(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(
                    new FileInputStream(filename));
            // readObject returns a reference of type Object, hence the down-cast
            players = (ArrayList<Player>) in.readObject();
        }
        catch(java.io.FileNotFoundException e){
            //if no file found...
            throw new FileNotFoundException(e.getMessage());
        }
        finally {
            try {
                if(in != null)	in.close();
            } catch(Exception e) {
                //throw exception upwards
                throw new IOException(e);
            }
        }
    }
    /**
     * setRoundStatus sets the current status of the game (which round is running)
     * @param status sets the current status of the game (which round is running)
     */
    public void setRoundStatus(GameStatusEnum status){roundStatus = status;}
    /**
     * getRoundStatus gets the current status of the game (which round is running)
     * @return gets the current status of the game (which round is running)
     */
    public GameStatusEnum getRoundStatus(){return roundStatus;}
    /**
     * getStillInGame returns a boolean if the current player is still in game
     * @param playerStillInGame is the index of the player
     * @return an boolean if the player is currently in this game
     */
    public boolean getStillInGame(int playerStillInGame){return stillInGame.get(playerStillInGame);}
    /**
     * setStillInGame sets the player to be in the game
     * @param playerStillInGame is the index of the player
     * @param value is the value to be set
     */
    private void setStillInGame(int playerStillInGame,boolean value){stillInGame.set(playerStillInGame,value);}
    /**
     * getHandPoints gets the player handPoints
     * @param player is the index of the player
     * @return an int of the current handPoint value
     */
    public int getHandPoints(int player){return players.get(player).getHandPoints();}
    /**
     * getHandRank gets the current handRank of the player
     * @param player is the index of the player
     * @return an CardValueEnum of the rank.
     */
    public CardValueEnum getHandRank(int player){return handRank.get(player);}
    /**
     * setHighestBetPlayerId sets the player who made the highest bet
     * @param playerId is the index of the player
     */
    private void setHighestBetPlayerId(int playerId){
        if(playerId >= players.size()){
            throw new NoSuchPlayerException("no such player with id:" + playerId);
        }
        highestBetPlayerId = playerId;
    }
    /**
     * getPlayer gets a player object
     * @param playerId is the index of the player
     * @return a player object
     */
    public Player getPlayer(int playerId){
        return players.get(playerId);
    }
    /**
     * getPlayers returns an array list of players
     * @return an array list of players
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }
    /**
     * getHighestBetPlayerId returns the id of the player who has the highest bet.
     * @return an index to the player who has the highest bet.
     */
    private int getHighestBetPlayerId(){return highestBetPlayerId;}
    /**
     * getPlayersInGame returns an int of playersStill in game
     * @return an integer of the players still in game
     */
    public int getPlayersInGame(){
        int numberOfplayersLeft = 0;
        //add all the players that are still in the game.
        for (int i = 0; i < players.size();i++) {
            if (players.get(i) instanceof TablePlayer){continue;}
            if (getStillInGame(i)){numberOfplayersLeft++;}
        }
        return numberOfplayersLeft;
    }
    /**
     * winnerByFold if all players folded except one this method gets called and the money is taken from
     * the table and given to the winner
     * @return the index of the winning player
     */
    public int winnerByFold(){
        int thePlayer = -1;
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i) instanceof TablePlayer){continue;}
            if(!getStillInGame(i)){continue;}
            thePlayer = i;
            break;

        }
        if(thePlayer == -1){
            throw new NoSuchPlayerException("No player is left to collect money from the winning game.");
        }
        players.get(thePlayer).addMoney(players.get(findTable()).withdrawMoney(players.get(findTable()).getMoney()));
        return thePlayer;
    }
    /**
     * setWinner sets the winner of the game
     * @return an arraylist of winners
     */
    public ArrayList<Integer> setWinner(){
        addTableCardsToPlayers(); //sets the best hand of each player
        int highestHandPoints = 0;
        ArrayList<Integer> winner = new ArrayList<>();

        //loop through the players and get the current leader:
        for (int i = 0 ; i < players.size();i++){
            //table can not win.
            if(players.get(i) instanceof TablePlayer){continue;}
            //player must still be in game to win
            if(!getStillInGame(i)){continue;}
            //set hand points on every player
            setHandPoints(i);
            if(getHandPoints(i) > highestHandPoints){
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
            if(getHandPoints(i) == highestHandPoints){
                winner.add(i);
            }
        }
        //give the winners their money
        for (int i = 0; i < winner.size(); i++) {
            players.get(winner.get(i)).addMoney(
                    players.get(findTable()).withdrawMoney(players.get(findTable()).getMoney() / winner.size()));
        }

        return winner;
    }
    /**
     * getHandPoints sets the HandRank (pair, two pairs .. and so on) to the handRank of the player
     * @param playerId is the index of the player
     * @param cardValue is the CardValue
     */
    public void getHandPoints(int playerId, CardValueEnum cardValue){handRank.set(playerId, cardValue);}
    /**
     * addTableCardsToPlayers adds table cards to the player hand and sorts them
     */
    public void addTableCardsToPlayers(){
        for(int i = 0; i < players.size();i++){
            if(players.get(i) instanceof TablePlayer){continue;}
            //add the Table hand to the player hands.
            players.get(i).addCard(players.get(findTable()).getCards());
            players.get(i).sortCardsByRank();
        }
    }
    /**
     * setHandPoints sets the highest hand points of the player
     * the player has seven cards ( 5 community cards and 2 own)
     * 21 hands are populated and compared with each other and the
     * highest hand will be returned.
     * @param playerId is the index of the player
     * @return returns the best hand of the player
     */
    private Hand setHandPoints(int playerId){
        ArrayList<Hand> allPossibleHands = new ArrayList<>();
        allPossibleHands.addAll(getAllHands(players.get(playerId).getPlayerHand()));
        int highestHand = 0;
        int highestHandId = 0;
        //loop through hands and get the best value.
        for (int i = 0; i < allPossibleHands.size();i++) {
            //
            if(getHandPoints(allPossibleHands.get(i)) > highestHand){
                highestHandId = i;
                highestHand = getHandPoints(allPossibleHands.get(i));
            }

        }
        //handpoints and handvalues are closely connected, update them both here.
        players.get(playerId).setHandPoints(highestHand);
        getHandPoints(playerId, setHandValue(allPossibleHands.get(highestHandId)));
        return allPossibleHands.get(highestHandId);
    }
    /**
     * smallAndBigBlind takes money from players that has the small and big blind
     */
    public void smallAndBigBlind(){
        //there has to be a check somewhere that the minimum amount of players(computer + human) >= 2.
        boolean smallBlindPlayer = true;
        for(int i = 0; i < players.size();i++) {
            //skip TablePlayer betting
            if (players.get(i) instanceof TablePlayer) {continue;}
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
                setHighestBetPlayerId(i);
            }else if(players.get(i).getMoney() < stake ){
                //the player is broke, next person must take the big-blind.
                setStillInGame(i,false);
            }else if(players.get(i).getMoney() >= stake){
                //player has taken the big blind and is still in game.
                players.get(findTable()).addMoney(players.get(i).withdrawMoney(stake));
                setStillInGame(i,true);
                //add the amount to roundbet.
                setRoundBet(i,getRoundBet(i)+stake);
                setRoundBet(findTable(),getRoundBet(findTable())+stake);
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
    /**
     * roundComplete tells the controller if the game round is completed.
     * @return a boolean if the game round is completed.
     */
    public boolean roundComplete(){
        //tells controller if the round is done.
        if(getBigBlind() == getCurrentPlayerId() && getCurrentPlayerId() == getHighestBetPlayerId()) {
            //special case for first round with blinds.
            //the highest better might call its own bet, special case in the first round
            setBigBlind(findTable());
            setHighestBetPlayerId();
        }else if(lastPlayer == currentPlayer){
            //if all all players except one is all in, then the round is complete
            return true;
        }else if(getCurrentPlayerId() == getHighestBetPlayerId()){
            //this is first round bigblind only!!
            setNextPlayer(findTable());
            setHighestBetPlayerId(findTable()+1);
            //reset roundbet
            for (int i = 0; i < players.size(); i++) {
                setRoundBet(i,0);
            }
            return true;
        }
            return false;
    }
    /**
     * setBigBlind sets the player who got the big blind
     * @param playerId index of the player id
     */
    private void setBigBlind(int playerId){bigBlind = playerId;}
    /**
     * getBigBlind get the player id of the big blind player
     * @return index of the player
     */
    private int getBigBlind(){return bigBlind;}
    /**
     * getPlayablePlayers returns an int of how many playable players that is left
     * if one player is all in he is not "playable" because he cannot fold, check or raise anymore
     * @return how many players that can make decisions.
     */
    public int getPlayablePlayers(){
        int result = 0;
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i) instanceof TablePlayer){continue;}
            if(getPlayerAllIn(i)){continue;}
            if(!getStillInGame(i)){continue;}
            result++;
        }
        return result;
    }
    /**
     * getStake gets the stake that is for the game
     * @return the stake value.
     */
    public int getStake(){return stake;}
    /**
     * setPlayerAllIn sets the player to be all-in
     * @param playerId index of the player
     * @param value sets if the player is all in or not.
     */
    private void setPlayerAllIn(int playerId,boolean value){playerAllIn.set(playerId, value);}
    /**
     * getPlayerAllIn gets the status if the player is all in or not
     * @param playerId index of the player
     * @return the value of the all in status.
     */
    private boolean getPlayerAllIn(int playerId){return playerAllIn.get(playerId);}
    /**
     * getCurrentPlayer gets the current player that need to make a decision in the game.
     * @return the player object
     */
    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }
    /**
     * getCurrentPlayerId returns the current player id
     * @return index of the player
     */
    public int getCurrentPlayerId(){
        return currentPlayer;
    }
    /**
     * setHighestBetPlayerId is used for the first round.
     * This is used to make the player call his own bet.
     * In the first round the player with big blind calls
     * his owns bet and the game logic is that you never have
     * to call your own bet, this is a trick to go around that
     * logic
     */
    private void setHighestBetPlayerId(){
        //just for the first round, this is a special case
        highestBetPlayerId++;
        int counter = 0;
        for (int i = highestBetPlayerId;  ; i++,counter++) {
            if(i >= players.size()){
                i=0;
            }
            if(players.get(i) instanceof TablePlayer){continue;}
            else if(counter >= 100){throw new NoPlayerInGameException("method highestBetPlayerId cannot set the next player, no players still in game!");}
            else if(!getStillInGame(i)){continue;}
            else{highestBetPlayerId = i;break;}
        }
    }
    /**
     * getMissingBetAmount this returns the missing bet amount to call
     * @param playerId is the player index
     * @return the amount that is needed for the player to call
     */
    public double getMissingBetAmount(int playerId){
        return getRoundBet(findTable()) - getRoundBet(playerId);
    }

    /**
     * getLastPlayer returns the last player that made a move
     * @return the Player object of the last player
     */
    public Player getLastPlayer(){return players.get(lastPlayer);}
    /**
     * setNextPlayer sets the next player in line.
     */
    public void setNextPlayer(){
        lastPlayer = currentPlayer;
        currentPlayer++;
        int counter = 0;
        for (int i = currentPlayer;  ; i++,counter++) {
            if(i >= players.size()){
                i=0;
            }
            if(players.get(i) instanceof TablePlayer){continue;}
            else if(counter >= 100){throw new NoPlayerInGameException("method SetNextPlayer cannot set the next player, no players still in game!");}
            //else if(getPlayerAllIn(i)){continue;}
            else if(!getStillInGame(i)){continue;}
            else{currentPlayer = i;break;}
        }
    }
    /**
     * setNextPlayer sets the next player
     * @param nextPlayer index of the player
     */
    private void setNextPlayer(int nextPlayer){
        currentPlayer = nextPlayer;
        setNextPlayer();
    }
    /**
     * bet is the method that gets called when the player makes a bet
     * @param betAmount is the amount that will player bet is
     */
    public void bet(double betAmount){
        double difference = getRoundBet(findTable()) - getRoundBet(getCurrentPlayerId());
        //players.get(findTable()).addMoney(getCurrentPlayer().withdrawMoney(betAmount));

        players.get(findTable()).addMoney(getCurrentPlayer().withdrawMoney(betAmount + difference));
        //set the roundBet of table
        //setRoundBet(findTable(), betAmount + getRoundBet(findTable()) );
        setRoundBet(findTable(), betAmount + getRoundBet(findTable()) );
        //set roundBet of player
        setRoundBet(getCurrentPlayerId(), getRoundBet(findTable()));
        //setRoundBet(getCurrentPlayerId(), betAmount + getRoundBet(getCurrentPlayerId()));

        if(players.get(getCurrentPlayerId()).getMoney() == 0){
            //player went all in:
            setPlayerAllIn(getCurrentPlayerId(), true);
        }
        setHighestBetPlayerId(currentPlayer);
        setNextPlayer();
        setBigBlind(findTable());

        //The model has changed update observers
        this.setChanged();
        this.notifyObservers(new String("betDone"));
    }
    /**
     * check is the method if the player checks
     */
    public void check(){
        setNextPlayer();
    }
    /**
     * call calls the highest bet.
     */
    public void call(){
        double difference = getRoundBet(findTable()) - getRoundBet(getCurrentPlayerId());
        //withdraw the amount and give it to the table
        players.get(findTable()).addMoney(players.get(getCurrentPlayerId()).withdrawMoney(difference));
        //update your getRoundBet
        setRoundBet(getCurrentPlayerId(), getRoundBet(getCurrentPlayerId()) + difference);
        setNextPlayer();
    }
    /**
     * fold is the method that makes the player fold
     */
    public void fold(){
        stillInGame.set(getCurrentPlayerId(),false);
        setNextPlayer();
    }
    /**
     * dealCards deals cards to the players
     * @param numberOfCards how many cards that the players will be dealt.
     */
    public void dealCards(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            for (int j = 0;j < players.size();j++) {
                if (players.get(j) instanceof TablePlayer) {continue;}
                if (!getStillInGame(j)) {continue;}
                //Give each player one card at a time.
                players.get(j).addCard(theDeck.dealCard());
            }
        }
    }
    /**
     * dealTable deal cards to the table
     * @param numberOfCards how many cards that the table will be dealt.
     */
    public void dealTable(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            for (int j = 0; j < players.size();j++) {
                if (players.get(j) instanceof HumanPlayer) {continue;}
                if (!getStillInGame(j)) {continue;}
                //Give the table cards.
                players.get(j).addCard(theDeck.dealCard());
            }
        }
    }
    /**
     * getRoundBet gets the current bet of the round
     * @param playerId is the index of the player
     * @return the amount the player bet this round
     */
    public double getRoundBet(int playerId){
        return roundBet.get(playerId);
    }
    /**
     * setRoundBet sets the roundBet for the player
     * @param playerId index of the player
     * @param amount of money the player bet is
     */
    private void setRoundBet(int playerId, double amount){
        roundBet.set(playerId,amount);
    }
    /**
     * findTable find the table among all players
     * @return index of the table
     */
    public int findTable(){
        for(int i = 0 ; i < players.size();i++) {
            //find the table...
            if (players.get(i) instanceof TablePlayer) {
                return i;
            }
        }
        throw new NoPlayerInGameException("Can not find the table.");
    }
    /**
     * rotatePlayers rotate the player on the table
     */
    private void rotatePlayers(){
        //rotate players one step
        for(int i = 0 ; i < players.size(); i++){
            if(players.get(i) instanceof TablePlayer){continue;}
            players.add(players.remove(i));
            break;
        }
    }
    /**
     * initGame initiates and resets values for the game.
     */
    public void initGame(){
        theDeck.fillDeck();
        theDeck.shuffleCards();
        rotatePlayers();

        for (int i = 0; i < players.size();i++) {
            setStillInGame(i,true);
            players.get(i).removeAllCards();
            setRoundBet(i,0.0);
            setPlayerAllIn(i,false);
        }
        roundStatus = GameStatusEnum.PreFlop;
    }

    /**
     * checkFlush check if the player has a flush
     * @param oneHand is the hand of the player
     * @return the Suit of the flush, null if none.
     */
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
    /**
     * setHandValue sets what the player has in its hand.
     * @param oneHand is the player hand
     * @return returns the CardValueEnum (flush, straight and so on.)
     */
    private CardValueEnum setHandValue(Hand oneHand){
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
    /**
     * getLowestCardInStraight gets the lowest valued card in the straight
     * @param oneHand is the player hand
     * @return the Rank of the lowest card in the straight.
     */
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
    /**
     * checkRankAndSuitValue checks for special combinations of cards
     * @param oneHand is the player hand
     * @return returns the cardRank
     */
    private CardValueEnum checkRankAndSuitValue(Hand oneHand){
        CardValueEnum cardRank;
        Suit_ flush;
        //check if flush is set:
        flush = checkFlush(oneHand);
        cardRank = setHandValue(oneHand);

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
    /**
     * getMatchingCards searches through the hand for matching cards and how many occurrences of them
     * @param oneHand is the player hand
     * @param numberOfMCards how many. 1 = single card, 2 = pair, 3 = three of a kind, 4 = four of a kind.
     * @param occurrences you can search for 2 occurrences of pair: tha will be:
     * getMatchingCards(myHand,2,2)
     * @return an arraylist of the Ranks of the cards that was requested
     */
    private ArrayList<Rank_> getMatchingCards(Hand oneHand,int numberOfMCards,int occurrences){
        //returns the highest matching pair, three of a kind or four of a kind
        ArrayList<Rank_> result = new ArrayList<>();
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
                        //if there is a match, add it to the arraylist
                        result.add(s);
                    }
                }
                checkOccurences++;
                if(checkOccurences == occurrences){
                    //if everything is found, break the loop
                    break;
                }
            }
        }
        if(checkOccurences != occurrences || result.size() != occurrences){
            throw new NoMatchingCardException("Occurrences: " + occurrences + " did not match checkOccurrences:" + checkOccurences);
        }
        return result;
    }
    /**
     * getHandPoints sets the value of the player hand
     * the hand points are value based, so that the higher ranked CardValue enum is always higher
     * than the lower one.
     * @param oneHand is the player hand
     * @return the handRank of the hand
     */
    private int getHandPoints(Hand oneHand){
        int handPoints = 0;
        ArrayList<Rank_> tempCardRanks = new ArrayList<>();
        CardValueEnum tempCardValueEnum;
        tempCardValueEnum = checkRankAndSuitValue(oneHand);

        switch (tempCardValueEnum){
            case RoyalFlush:
                //if royal flush get the highest score available
                handPoints += 230000000;
                break;
            case StraightFlush:
                //if straight flush multiply the value to the lowest card in straight
                handPoints += 15000000 * getLowestCardInStraight(oneHand).getValue();
                break;
            case FourOfAKind:
                //if four of a kind, multiply with the value of the rank of four of a kind
                //add the last card to the value
                handPoints += 2000000 * getMatchingCards(oneHand,4,1).get(0).getValue();
                handPoints += getMatchingCards(oneHand,1,1).get(0).getValue();
                break;
            case FullHouse:
                //if full house, multiply the three of a kind with an constant
                //and multiply the pair with an constant
                handPoints += 264000 * getMatchingCards(oneHand,3,1).get(0).getValue();
                handPoints += 10 * getMatchingCards(oneHand,2,1).get(0).getValue();
                break;
            case Flush:
                //flush gives this much handpoints
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
        return handPoints;
    }
    /**
     * generateHands generates all hands and returns them
     * @param playerHand is the hand of the player
     * @return all possible hands
     */
    private ArrayList<Hand> getAllHands(Hand playerHand){
        //this generates 21 hands.
        //n choose k => 7 choose 5
        //http://stackoverflow.com/questions/8375452/how-to-loop-through-all-the-combinations-of-e-g-48-choose-5
        //for loop stolen:
        ArrayList<Hand> allPossibleHands = new ArrayList<>();
        Hand tempHand;
        int n = playerHand.getNoOfCards();
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

                            //get the list in order:
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