package CardPackage;

import javafx.scene.image.Image;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * Objects of this class represents cards in a deck (of cards). A card is
 * immutable, i.e. once created its rank or suit cannot be changed.
 */

public class Card {
    private final Rank_ rank;
    private final Suit_ suit;
    private final Image image;
    /**
     * 
     * @param rank is the card value
     * @param suit is the suit of the card
     * @param image is the image representing the card and the suit.
     * @throws java.lang.IllegalArgumentException 
     */
    public Card(Rank_ rank, Suit_ suit, Image image){
        this.rank = rank;
        this.suit = suit;
        this.image = image;
    }
    /**
     * getRank gets the current rank value of the card.
     * @return the value of the card
     */
    public int getRank() {
        return rank.getValue();
    }
    /**
     * getSuit returns the current suitvalue of the card
     * @return 
     */
    public int getSuit() {
        return suit.getValue();
    }
    /**
     * getImage returns the image representing the card.
     * @return the image of the created card.
     */
    public Image getImage(){
        return image;
    }
    /**
     * equals check if the current card is equal to the matching card.
     * @param other  is the other card
     * @return a boolean if they are the same.
     */
    public boolean equals(Card other) {
        return this.rank == other.rank && this.suit == other.suit;
    }
    /**
     * toString returns the string of the current card
     * @return a string of the rank and suit value
     */
    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }
}
