package PlayerPackage;
import CardPackage.*;
import MoneyPackage.Money;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * Player class holds all methods that is common for the different players
 */

abstract public class Player implements Serializable {

    private String userName;
    private transient Hand playerHand;
    private Money money;

    /**
     * Player constructor
     * @param userName is set to the player, a new hand and money = 0 is created
     */
    public Player(String userName){
        this.userName = userName;
        this.playerHand = new Hand();
        this.money = new Money();
    }
    /**
     * Player constructor
     * @param userName is set to the player
     * @param money is set to the player
     */
    public Player(String userName,double money){
        this.userName = userName;
        this.playerHand = new Hand();
        this.money = new Money(money);
    }
    /**
     * removeAllCards removes all cards from the player hand.
     */
    public void removeAllCards(){
        playerHand.removeAllCards();
    }
    /**
     * createNewHand is used for creating a new hand to the player
     * This is used when the game is loaded, the player hand consists for cards
     * and they can not be serializable because they have images.
     * The player is created with a null hand and this creates the hand again.
     */
    public void createNewHand(){
        this.playerHand = new Hand();
    }
    /**
     * getHandPoints get the handValue of the current hand
     * @return an int of the value
     */
    public int getHandPoints(){return playerHand.getHandValue();}
    /**
     * setHandPoints set the hand value of the current hand
     * @param handPoints is the value of the handpoints the player has
     */
    public void setHandPoints(int handPoints){playerHand.setHandValue(handPoints);}
    /**
     * getPlayerHand gets the cards the player has
     * @return the hand of cards the player has
     */
    public Hand getPlayerHand(){return this.playerHand;}
    /**
     * getName gets the player name
     * @return a String that represents the playername
     */
    public String getName(){
        return this.userName;
    }
    /**
     * addCard adds a card to the hand arraylist
     * @param card is the card to be added to the player hand
     */
    public void addCard(Card card){playerHand.addCard(card);}
    /**
     * addCard adds cards to the hand arraylist
     * @param card is an arraylist of cards that will be added to the player hand
     */
    public void addCard(ArrayList<Card> card){playerHand.addCard(card);}
    /**
     * getCards returns an arraylist of cards from the player hand
     * @return an arraylist of cards from the player hand
     */
    public ArrayList<Card> getCards(){return playerHand.getAllCards();}
    /**
     * sortCardByRank sorts the cards in the hand by rank
     */
    public void sortCardsByRank(){playerHand.sortCardsByRank();}
    /**
     * addMoney adds money to the player
     * @param amount is the amount to be added to the player
     */
    public void addMoney(double amount){money.addFunds(amount);}
    /**
     * withDrawMoney remove money from the player
     * @param amount is the amount to be removed
     * @return the amount that was removed
     */
    public double withdrawMoney(double amount){return money.withdrawFunds(amount);}
    /**
     * getMoney returne the current amount of money the player has
     * @return the amount of money the player has
     */
    public double getMoney(){return money.getMoney();}
    /**
     * toString returns the object as a string
     * @return this object as a string
     */
    public String toString(){
        return "Player " + userName + " has " +money.toString() + " amount of money." + " and the cards: " + playerHand.toString();
    }
}
