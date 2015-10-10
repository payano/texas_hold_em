package PlayerPackage;

import CardPackage.Hand;
import CardPackage.Card;
import ChipPackage.ChipCollection;
import MoneyPackage.Money;
/**
 * Created by Arvid Bodin och Johan Svensson on 07/10/15.
 *
 */

abstract public class Player {

    private String userName;
    private Hand playerHand;
    private ChipCollection chips;
    private Money money;
    //use highestBidder instead of isBigBlind and isSmallBlind.
    private boolean stillInGame, highestBid, isBigBlind, isSmallBlind;
    private double roundBet;

    public Player(String userName){
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

    //these are trattiga aswell..!!
    public void setHighestBid(boolean highestBidder){this.highestBid = highestBidder;}
    public boolean getHighestBid(){return highestBid;}
    public void setBigBlind(boolean bigBlind){
        this.isBigBlind = bigBlind;
    }
    public boolean getBigBlind(){
        return isBigBlind;
    }
    //THESE IS ABOUT TO BE REPLACED WITH THE TWO FUNCTIONS ABOVE!

    public void setSmallBlind(boolean smallBlind){
        System.out.println("setSmallBlind will be obsolete, use setHighestBidder instead...");
        this.isSmallBlind = smallBlind;
    }
    public boolean getSmallBlind(){
        System.out.println("getSmallBlind will be obsolete, use getHighestBidder instead...");
        return isSmallBlind;
    }
    //END //

    public String getName(){
        return this.userName;
    }
    public void setStillInGame(boolean stillInGame){this.stillInGame = stillInGame;}
    public boolean getStillInGame(){return this.stillInGame;}
    public void addCard(Card card){
        playerHand.addCard(card);
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
