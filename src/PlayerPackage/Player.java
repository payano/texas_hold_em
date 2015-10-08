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
    private boolean stillInGame;

    public Player(String userName){
        this.userName = userName;
        playerHand = new Hand();
        chips = new ChipCollection();
        money = new Money();
    }
    public String getName(){
        return this.userName;
    }
    public void setStillInGame(boolean stillInGame){this.stillInGame = stillInGame;}
    public boolean getStillInGame(){return this.stillInGame;};
    public void addCard(Card card){
        playerHand.addCard(card);
    }

    public void addMoney(double amount){
        money.addFunds(amount);
    }
    public double withdrawMoney(double amount){
        return money.withdrawFunds(amount);
    }

    public void convertMoneyToChips(double amount){

    }

    public double getMoney(){
        return money.getMoney();
    }

    public double getChips(){
        return chips.getChipsValue();
    }


    public String toString(){
        return "Player " + userName + " has " +money.getMoney() + " amount of money." + "\n Has the cards: " + playerHand.toString();
    }

}
