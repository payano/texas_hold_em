package PlayerPackage;

import CardPackage.*;
import ChipPackage.ChipCollection;
import MoneyPackage.Money;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;

/**
 * Created by Arvid Bodin och Johan Svensson on 07/10/15.
 *
 */

abstract public class Player {

    private String userName;
    private Hand playerHand;
    private ChipCollection chips;
    private Money money;
    private boolean stillInGame, highestBid, isBigBlind;
    private CardValueEnum handValue; //eller bara en int, får se vad som blir schysstast.
    private double roundBet;
    private int handPoints;

    public Player(String userName){
        this.handValue = CardValueEnum.None;
        this.userName = userName;
        this.playerHand = new Hand();
        this.chips = new ChipCollection();
        this.money = new Money();
        this.roundBet = 0;
    }
    public Player(String userName,double money){
        this.userName = userName;
        this.playerHand = new Hand();
        this.chips = new ChipCollection();
        this.money = new Money(money);
        this.roundBet = 0;
    }


    public SuitEnum checkFlush(Hand oneHand){
        int suitArray[] = new int[4];
        //populate rank and suit arrays:
        for(int i = 0; i < oneHand.getNoOfCards();i++){
            //add ace to first slot in the array aswell.
            suitArray[oneHand.getCard(i).getSuit()]++;
        }
        for(int i = 0 ; i < suitArray.length;i++){
            if(suitArray[i] == 5){
                for(SuitEnum s : SuitEnum.values()){
                    if(s.getValue() == i) {
                        return s;
                    }
                }

            }
        }
        //elakt att returnera null.. men det är ingen flush..
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
                    //straight = true;
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
                //fourOfAKind = true;
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
    //uber fresh function
    public RankEnum getLowestCardInStraight(Hand oneHand){
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
/*        if(straightCount != 5){
            throw new NoCardValueStraightException("There is no straight in the list.");
        }*/
        //dunno if this is working...

        //NO WORKING!!!!!!!!
        //throw new NoSuchCardException("come come");
        for(RankEnum s : RankEnum.values()){
            //System.out.println("loweststraight: " + lowestStraight + " s.getvalue: " + s.getValue());
            if(s.getValue() == lowestStraight){
                return s;
            }
        }
        throw new NoCardValueStraightException("There is no straight in the list.");

    }
    public CardValueEnum checkRankAndSuitValue(Hand oneHand){
        CardValueEnum cardRank;
        SuitEnum flush;

        //check if flush is set:
        flush = checkFlush(oneHand);
        cardRank = checkCardValue(oneHand);

        if(cardRank == CardValueEnum.Straight && flush != null){
            //Jackpot! both straight and flush!
            cardRank = CardValueEnum.StraightFlush;
            //hardcoded royal here:
            if(flush == SuitEnum.Hearts && getLowestCardInStraight(oneHand) == RankEnum.Ten){
                //sweet jesus, you are a lucky soul.
                cardRank = CardValueEnum.RoyalFlush;
            }
        }else if(flush != null){
            cardRank = CardValueEnum.Flush;
        }
        System.out.println(cardRank.toString());
        return cardRank;
    }
    public RankEnum getMatchingCard(Hand oneHand,int numberOfMCards){
        //returns the highest matching pair, three of a kind or four of a kind
        int rankArray[] = new int[15];
        int match = 0;
        //populate arraylist
        for(int i = 0; i < oneHand.getNoOfCards();i++){
            rankArray[oneHand.getCard(i).getRank()]++;
        }
        for(int i = rankArray.length-1 ; i > 0;i--){
            //System.out.println("i: " + i + " numberOfMCards: " + numberOfMCards + " rankArray[i]:" + rankArray[i]);
            if(rankArray[i] == numberOfMCards){
                //System.out.println("WTF DUDE");
                match = i;
                break;
            }
        }
     //   throw new NoSuchCardException("come come");
        //NO WORKING!!!!!!!!
        for(RankEnum s : RankEnum.values()){
            //System.out.println("match: " + match + " s.getvalue: " + s.getValue());
            if(s.getValue() == match){
                return s;
            }
        }
        throw new NoMatchingCardException("The number of matching cards does not exist: " + numberOfMCards);

    }

    public void setHandValue2(Hand oneHand){
        int handPoints = 0;
        switch (checkRankAndSuitValue(oneHand)){
            case RoyalFlush:
                handPoints += 230000000;
                //TBD
                //daskdjak
                break;
            case StraightFlush:
                handPoints += 15000000 * getLowestCardInStraight(oneHand).getValue();
                break;
            case FourOfAKind:
                //handPoints += i * 264500; //four of a kind
                //handPoints += i; //fifth cared
                handPoints += 2000000 * getMatchingCard(oneHand,4).getValue();
                break;
            case FullHouse:
                //handPoints += 264000 * i; //three of a kind
                //handPoints += 10 * i; //highest pair
                handPoints += 264000 * getMatchingCard(oneHand,3).getValue();
                handPoints += 10 * getMatchingCard(oneHand,2).getValue();
                break;
            case Flush:
                //handPoints += 527000; //for flush
                //handPoints += i; //add the 5 cards together
                break;
            case Straight:
                //handPoints += 52600 * lowestStraight;
                handPoints += 52600 * getLowestCardInStraight(oneHand).getValue();
                break;
            case ThreeOfAKind:
                //handPoints += 7500 * i; //for three of a kind
                //handPoints += i; //add the two other cards
                break;
            case TwoPair:
                //handPoints += 1000 * i; //highest pair
                //handPoints += 10 * i; //lowest pair
                //handPoints += i //last card
                break;
            case OnePair:
                //handPoints += 40*i; //for the pair
                //handPoints += i //for the rest of the cards
                break;
            case None:
                //handPoints += i //for all five cards
                break;
        }
//        System.out.println("HandValue: " + handValue + " HandPoints: " + handPoints);

    }
    public void getBestHand(){
        ArrayList<Hand> allPossibleHands = new ArrayList<Hand>();
        //get all possible hands from generateHands.
        allPossibleHands.addAll(generateHands());

        for( Hand oneHand: allPossibleHands){
            System.out.println("GIVE ME YOUR INFO: " + oneHand.toString());
            setHandValue2(oneHand);
        }


    }
    private ArrayList<Hand> generateHands(){
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


    public int getHandPoints(){return this.handPoints;}
    //this is texas specific...
    public void setHandValue(){
        int rankArray[] = new int[15];
        int suitArray[] = new int[4];
        
        int pairCount = 0;
        boolean threeOfAKind = false;
        boolean fourOfAKind = false;
        boolean flush = false;
        boolean royal = false;
        boolean straight = false;
        int straightCount = 0;
        int lastCardValue = 0;
        int lowestStraight = 0;

        this.handPoints = 0;
        /*
         1 = Ace
         2 = 2
         ....
         14 = Ace
         */
        //sort the hand
        //playerHand.sortCardsByRank();

        /*
        BUG: YOU CAN GET A STRAIGHT AND A FLUSH. != STRAIGHTFLUSH
        AND THE PROGRAM SEES IT AS A STRAIGHTFLUSH!!
         */

        for(int i = 0; i < playerHand.getNoOfCards();i++){
            //example:
            //getRank() = 2, then pairArray[2] adds one.
            //then we know how many pairs, three of a kind, four of a kind
            //full house we have.
            if(playerHand.getCard(i).getRank() == 14){
                rankArray[1]++; //ACE is both 13 and 1
            }
            //populate rankArray
            rankArray[playerHand.getCard(i).getRank()]++;
            //populate suitArray
            suitArray[playerHand.getCard(i).getSuit()]++;
        }
        //count pairs + two pairs + three of a kind + four of a kind
        for(int i = 2; i < rankArray.length;i++){
            if(rankArray[i] == 2){
                pairCount++;
                if(pairCount > 2){pairCount = 2;}
                System.out.println("PairCount is: " + pairCount + " " + userName);
            }
            if(rankArray[i] == 3){
                System.out.println("Three of a kind!! " + userName);
                threeOfAKind = true;
            }
            if(rankArray[i] == 4){
                System.out.println("Four of a kind!! " + userName);
                fourOfAKind = true;
            }
        }
        //find straight
        for(int i=0; i < rankArray.length;i++){
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
        //straight found!
        if(straightCount == 5) {
            System.out.println("AWESOME DUDE! STRAIGHT! " + userName);
            straight = true;
        }
        for(int i=0;i < suitArray.length;i++){
            if(suitArray[i] == 5){
                System.out.println("MATE YOU GOT FLUSH: " + userName);
                flush = true;
                //Hearts(2)
                if(i == 2){royal = true;}
            }
        }

        //mega hardcoded if statements...yeye
        if(royal){
            //royal straight flush
            handPoints += 4000000;
            handValue = CardValueEnum.Flush;
        }else if(flush && straight){
            //straight flush
            handPoints += 371000 * lowestStraight;
            handValue = CardValueEnum.StraightFlush;
        }else if(fourOfAKind){
            //find the fourofAKind
            //dont count ACE as one (i = 1)
            for(int i = 2; i < rankArray.length;i++){
                if(rankArray[i] == 4){
                    handPoints += i * 264500;
                    break;
                }
            }
            //get the highest single card.
            for(int i = rankArray.length-1; i > 1;i--){
                if(rankArray[i] == 1){
                    handPoints += i;
                }
            }
            handValue = CardValueEnum.FourOfAKind;
        }
        else if(threeOfAKind && pairCount > 0){
            //three of a kind
            //get three of a kind
            for(int i = rankArray.length-1; i > 1 ; i--){
                if(rankArray[i] == 3){
                    handPoints += 264000 * i;
                    break;
                }
            }
            //get highest pair
            for(int i = rankArray.length-1; i > 1 ; i--){
                if(rankArray[i] == 2){
                    handPoints += 10 * i;
                    break;
                }
            }
            handValue = CardValueEnum.FullHouse;
        }
        else if(flush){
            handPoints += 527000;
            int theSuit = 0;
            for(int i = 0;i < suitArray.length;i++){
                if(suitArray[i] == 5){
                    theSuit = i+1;
                    break;
                }
            }
            for(int countSingle = 0, i = 0; i < playerHand.getNoOfCards();i++){
                if(playerHand.getCard(i).getSuit() == theSuit){
                    handPoints += i;
                    countSingle++;
                    if(countSingle == 5){break;}
                }
            }
            //måste ta reda på vilka kort som finns i flush för att sedan addera dessa till handpoints
            handValue = CardValueEnum.Flush;
        }
        else if(straight){
            handPoints += 52600 * lowestStraight;
            handValue = CardValueEnum.Straight;
        }
        else if(threeOfAKind){
            //get three of a kind
            for(int i = rankArray.length-1; i > 1 ; i--){
                if(rankArray[i] == 3){
                    handPoints += 7500 * i;
                    break;
                }
            }
            //get the two highest single cards
            for(int countSingle=0, i = rankArray.length-1; i > 1 ; i--){
                if(rankArray[i] == 1){
                    handPoints += i;
                    countSingle++;
                    if(countSingle == 2){break;}
                }
            }
            handValue = CardValueEnum.ThreeOfAKind;
        }else if(pairCount >= 2){
            //mega if statement to get the highest pairs.
            for(int countSingle = 0,i = rankArray.length-1; i > 1 ; i--){
                if(rankArray[i] == 2){
                    if(countSingle == 0){
                        handPoints += 1000 * i;
                        countSingle++;
                    }else if(countSingle == 1){
                        handPoints += 10 * i;
                        break;
                    }
                }
            }
            //get the highest single card
            for(int i = rankArray.length-1; i > 1 ; i--){
                if(rankArray[i] == 1){
                    handPoints += i;
                    break;
                }
            }
            handValue = CardValueEnum.TwoPair;
        }else if(pairCount == 1){
            //get highest pair
            for(int i = rankArray.length-1; i > 1 ; i--){
                if(rankArray[i] == 2){
                    handPoints += 40*i;
                    break;
                }
            }
            //get three highest cards
            for(int countSingle = 0,i = rankArray.length-1; i > 1 ; i--){
                if(rankArray[i] == 1){
                    handPoints += i;
                    countSingle++;
                    if(countSingle == 3){
                        break;
                    }
                }
            }
            handValue = CardValueEnum.OnePair;
        }else{
            //get five highest cards
            for(int countSingle = 0,i = rankArray.length-1; i > 1 ; i--){
                if(rankArray[i] == 1){
                    handPoints += i;
                    countSingle++;
                    if(countSingle == 5){
                        break;
                    }
                }
            }
            handValue = CardValueEnum.None;
        }




    }
    public CardValueEnum getHandValue(){return handValue;}
    public void setHighestBid(boolean highestBidder){this.highestBid = highestBidder;}
    public boolean getHighestBid(){return highestBid;}
    public void setBigBlind(boolean bigBlind){
        this.isBigBlind = bigBlind;
    }
    public boolean getBigBlind(){
        return isBigBlind;
    }
    public String getName(){
        return this.userName;
    }
    public void setStillInGame(boolean stillInGame){this.stillInGame = stillInGame;}
    public boolean getStillInGame(){return this.stillInGame;}
    public void addCard(Card card){
        playerHand.addCard(card);
    }
    public void addCard(ArrayList<Card> card){
        playerHand.addCard(card);
    }
    public ArrayList<Card> getCards(){
        return playerHand.getAllCards();
    }
    public void sortCardsByRank(){
        playerHand.sortCardsByRank();
    }
    public double getRoundBet(){return roundBet;}
    public void addRoundBet(double roundBet){this.roundBet += roundBet;}
    public void resetRoundBet(){this.roundBet = 0;}
    public void addMoney(double amount){
        money.addFunds(amount);
    }
    public double withdrawMoney(double amount){
        return money.withdrawFunds(amount);
    }

    public void convertMoneyToChips(double amount){

    }

    abstract void playHand();

    public double getMoney(){
        return money.getMoney();
    }

    public double getChips(){
        return chips.getChipsValue();
    }


    public String toString(){
        return "Player " + userName + " has " +money.toString() + " amount of money." + " and the cards: " + playerHand.toString();
    }

}
