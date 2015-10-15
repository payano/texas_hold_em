package PlayerPackage;

import CardPackage.*;
import ChipPackage.ChipCollection;
import MoneyPackage.Money;

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


    public Player(String userName){
        this.userName = userName;
        this.playerHand = new Hand();
        this.chips = new ChipCollection();
        this.money = new Money();
    }
    public Player(String userName,double money){
        this.userName = userName;
        this.playerHand = new Hand();
        this.chips = new ChipCollection();
        this.money = new Money(money);
    }




    public void removeAllCards(){
        playerHand.removeAllCards();
    }
    //public int getHandValue(){return this.handPoints;}
    //public CardValueEnum getHandValue(){return handValue;}
    //public void setHighestBetPlayerId(boolean highestBidder){this.highestBid = highestBidder;}
    //public boolean getHighestBetPlayerId(){return highestBid;}
    //public void setBigBlind(boolean bigBlind){this.isBigBlind = bigBlind;}
    //public boolean getBigBlind(){return isBigBlind;}
    public int getHandPoints(){return playerHand.getHandValue();}
    public void setHandPoints(int i){playerHand.setHandValue(i);}
    public Hand getPlayerHand(){return this.playerHand;}
    public String getName(){
        return this.userName;
    }
    //public void setStillInGame(boolean stillInGame){this.stillInGame = stillInGame;}
    //public boolean getStillInGame(){return this.stillInGame;}
    public void addCard(Card card){playerHand.addCard(card);}
    public void addCard(ArrayList<Card> card){playerHand.addCard(card);}
    public ArrayList<Card> getCards(){return playerHand.getAllCards();}
    public void sortCardsByRank(){playerHand.sortCardsByRank();}
    //public double getRoundBet(){return roundBet;}
    //public void addRoundBet(double roundBet){this.roundBet += roundBet;}
    //public void resetRoundBet(){this.roundBet = 0;}
    public void addMoney(double amount){money.addFunds(amount);}
    public double withdrawMoney(double amount){return money.withdrawFunds(amount);}
    //public void convertMoneyToChips(double amount){;}
    public double getMoney(){return money.getMoney();}
    //public double getChips(){return chips.getChipsValue();}
    public String toString(){
        return "Player " + userName + " has " +money.toString() + " amount of money." + " and the cards: " + playerHand.toString();
    }
}
