package PlayerPackage;

import CardPackage.*;
import ChipPackage.ChipCollection;
import MoneyPackage.Money;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Arvid Bodin och Johan Svensson on 07/10/15.
 *
 */

abstract public class Player implements Serializable {

    private String userName;
    private transient Hand playerHand;
    private transient ChipCollection chips;
    private Money money;

    /**
     * 
     * @param userName 
     */
    public Player(String userName){
        this.userName = userName;
        this.playerHand = new Hand();
        this.chips = new ChipCollection();
        this.money = new Money();
    }
    /**
     * 
     * @param userName
     * @param money 
     */
    public Player(String userName,double money){
        this.userName = userName;
        this.playerHand = new Hand();
        this.chips = new ChipCollection();
        this.money = new Money(money);
    }
    /**
     * 
     */
    public void removeAllCards(){
        playerHand.removeAllCards();
    }
    /**
     * 
     */
    public void createNewHand(){
        this.playerHand = new Hand();
    }
    /**
     * 
     * @return 
     */
    public int getHandPoints(){return playerHand.getHandValue();}
    /**
     * 
     * @param i 
     */
    public void setHandPoints(int i){playerHand.setHandValue(i);}
    /**
     * 
     * @return 
     */
    public Hand getPlayerHand(){return this.playerHand;}
    /**
     * 
     * @return 
     */
    public String getName(){
        return this.userName;
    }
    //public void setStillInGame(boolean stillInGame){this.stillInGame = stillInGame;}
    //public boolean getStillInGame(){return this.stillInGame;}
    /**
     * 
     * @param card 
     */
    public void addCard(Card card){playerHand.addCard(card);}
    /**
     * 
     * @param card 
     */
    public void addCard(ArrayList<Card> card){playerHand.addCard(card);}
    /**
     * 
     * @return 
     */
    public ArrayList<Card> getCards(){return playerHand.getAllCards();}
    /**
     * 
     */
    public void sortCardsByRank(){playerHand.sortCardsByRank();}
    
    //public double getRoundBet(){return roundBet;}
    //public void addRoundBet(double roundBet){this.roundBet += roundBet;}
    //public void resetRoundBet(){this.roundBet = 0;}
    /**
     * 
     * @param amount 
     */
    public void addMoney(double amount){money.addFunds(amount);}
    /**
     * 
     * @param amount
     * @return 
     */
    public double withdrawMoney(double amount){return money.withdrawFunds(amount);}
    /**
     * 
     * @return 
     */
    //public void convertMoneyToChips(double amount){;}
    public double getMoney(){return money.getMoney();}
    //public double getChips(){return chips.getChipsValue();}
    /**
     * 
     * @return 
     */
    public String toString(){
        return "Player " + userName + " has " +money.toString() + " amount of money." + " and the cards: " + playerHand.toString();
    }
}
