package CardPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 * The Deck class represents all playable cards (52) in the game.
 */
public class Deck {
    private List<Card> theCards = new ArrayList<>();
    ClassLoader cl = this.getClass().getClassLoader();
    /**
     * Deck constructor creates a new deck with 52 cards.
     */
    public Deck (){
        createDeck();
    }
    /**
     * shuffleCards shuffles the cards in the arraylist
     */
    public void shuffleCards(){
        Collections.shuffle(theCards);
    }
    /**
     * fillDeck removes all cards and creates a new deck of cards.
     */
    public void fillDeck(){
        theCards.removeAll(theCards);
        createDeck();
    }
    /**
     * dealCard takes a card from the deck and returns it
     * @return a card
     * @throws NoSuchCardException 
     */
    public Card dealCard() throws NoSuchCardException {
        if(theCards.isEmpty()){
            throw new NoSuchCardException("No cards left to deal!!");
        }
        Card temp = theCards.get(0);
        theCards.remove(0);
        return temp;
    }
    /**
     * getNoOfCards returns the remain cards
     * @return an int of remaining cards
     */
    public int getNoOfCards() {
        return theCards.size();
    }
    /**
     * toString creates a string of all available cards and returns it.
     * @return a string of remaining cards
     */
    public String toString(){
        String info = new String();
        if(theCards.isEmpty()) {
            info = "Finns inga kort kvar.";
            return info;
        }
        for(int i = 0; i < theCards.size(); i++) {
            info = info + theCards.get(i).toString()+"\n";
        }
        return info;
    }
    /**
     * createDeck creates a new Deck of cards.
     */
    private void createDeck(){
        int imgNr = 52; //used for getting the image to card.
        for (Rank_ r : Rank_.values()){
            if(r == Rank_.One){continue;} //if dont do anything
            for (Suit_ s : Suit_.values()){
                Image tmpBild  = new Image(cl.getResourceAsStream("resources/cards/" + imgNr + ".png"));
                theCards.add(new Card(r,s,tmpBild));
                imgNr--;
            }
        }
    }
}





