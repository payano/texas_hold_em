package CardPackage;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 * the Hand Class has a list of cards that a player can use as a collection.
 */
public class Hand {
    private ArrayList<Card> theHand;
    private int handValue;

    /**
     * Hand constructor creates an arraylist of cards
     */
    public Hand(){
        theHand  = new ArrayList<>();
    }
    /**
     * setHandValue sets the value of the current hand
     * @param value is an integer representing the value of the hand
     */
    public void setHandValue(int value){this.handValue = value;}
    /**
     * getHandValue gets the value of the current hand
     * @return an integer of the hand value
     */
    public int getHandValue(){return this.handValue;}
    /**
     * getNoOfCards returns the number of cards in the hand
     * @return an integer of the number of cards in the hand
     */
    public int getNoOfCards(){
        return theHand.size();
    }
    /**
     * addCards adds a new card to the hand
     * @param card is the card to be added to the hand.
     */
    public void addCard(Card card){
        theHand.add(card);
    }
    /**
     * addCard adds new cards to the hand
     * @param card is an arraylist of cards that will be added to the hand.
     */
    //add a list of cards.
    public void addCard(ArrayList<Card> card){
        theHand.addAll(card);
    }
    /**
     * getAllCards returns the hand of cards
     * @return an arraylist of Cards
     */
    public ArrayList<Card> getAllCards(){
        return theHand;
    }
    /**
     * sortCardsBy rank sorts the cards by rank
     */
    public void sortCardsByRank(){
        Collections.sort(theHand, new SortCardsByRank());
    }
    /**
     * sortCardsBySuit sorts the cards by suit
     */
    public void sortCardsBySuit(){
        Collections.sort(theHand, new SortCardsBySuit());
    }
    /**
     * getCard returns a card
     * @param cardNr an integer of the card index in the arraylist of cards
     * @return the card that was requested
     */
    public Card getCard(int cardNr){
        if(cardNr >= theHand.size() || cardNr < 0){
            throw new NoSuchCardException("wrong index: " + cardNr);
        }else{
            return theHand.get(cardNr);
        }
    }
    /**
     * removeCard removes a card from the arraylist of cards
     * @param cardNr an integer of the card index in the arraylist of cards
     * @return the card that was removed
     */
    //Tar bort ett kort och tittar så att antalet har minskat.
    public Card removeCard(int cardNr){
        if(cardNr >= theHand.size() || cardNr < 0){
            throw new NoSuchCardException("wrong index" + cardNr);
        }else{
            return theHand.remove(cardNr);
        }
    }
    /**
     * removeAllCards remove all cards in the hand
     */
    public void removeAllCards(){
        theHand.removeAll(theHand);
    }
    /**
     * toString returns a string of all cards in the hand
     * @return a string of all cards in the hand.
     */
    public String toString(){
        String info = new String();
        if(theHand.isEmpty()) {
            info = "There is no cards in the hand.";
            return info;
        }
        for(int i = 0; i < theHand.size(); i++) {
            info += theHand.get(i).toString();
            if(i < theHand.size()-1) info += ", ";
        }
        return "[" + info + "]";
    }
}
