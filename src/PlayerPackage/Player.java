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
        /*
         1 = Ace
         2 = 2
         ....
         14 = Ace
         */
        //sort the hand
        //playerHand.sortCardsByRank();

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
            suitArray[playerHand.getCard(i).getSuit()-1]++;
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
                if(straightCount == 5){break;}
            }else{
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
        if(royal){handValue = CardValueEnum.Flush;}
        else if(flush && straight){handValue = CardValueEnum.StraightFlush;}
        else if(fourOfAKind){handValue = CardValueEnum.FourOfAKind;}
        else if(threeOfAKind && pairCount > 0){handValue = CardValueEnum.FullHouse;}
        else if(flush){handValue = CardValueEnum.Flush;}
        else if(straight){handValue = CardValueEnum.Straight;}
        else if(threeOfAKind){handValue = CardValueEnum.ThreeOfAKind;}
        else if(pairCount >= 2){handValue = CardValueEnum.TwoPair;}
        else if(pairCount == 1){handValue = CardValueEnum.OnePair;}
        else { handValue = CardValueEnum.None;}


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
