package CardPackage;

import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * Created by arvidbodin on 14/09/15.
 *
 * Objects of this class represents cards in a deck (of cards). A card is
 * immutable, i.e. once created its rank or suit cannot be changed.
 */

public class Card {

    /**
     * @param rank 1 = Ace, 2 = 2, ...
     * @param suit 1 = spades, 2 = hearts, 3 = diamonds, 4 = clubs
     */
    private final Rank_ rank;
    private final Suit_ suit;
    private final Image image;

    /**
     * 
     * @param rank
     * @param suit
     * @param image
     * @throws java.lang.IllegalArgumentException 
     */
    public Card(Rank_ rank, Suit_ suit, Image image) throws java.lang.IllegalArgumentException{
        this.rank = rank;
        this.suit = suit;
        this.image = image;
    }
    /**
     * 
     * @return 
     */
    public int getRank() {
        return rank.getValue();
    }
    /**
     * 
     * @return 
     */
    public int getSuit() {
        return suit.getValue();
    }
    /**
     * 
     * @return 
     */
    public Image getImage(){
        return image;
    }
    /**
     * 
     * @param other
     * @return 
     */
    public boolean equals(Card other) {
        return this.rank == other.rank && this.suit == other.suit;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        String info = rank.toString() + " of " + suit.toString();
        return info;
    }
}
