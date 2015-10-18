package CardPackage;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * Rank_ class is used for ranking cards in the game.
 */
public enum Suit_ {
    Diamonds(0), Hearts(1), Spades(2),Clubs(3);

    private final int cardValue;
    /**
     * Suit_ constructor
     * @param cardValue is the value of card
     */
    Suit_(int cardValue){
        this.cardValue = cardValue;
    }
    /**
     * getValue
     * @return an int of the value of the card.
     */
    public int getValue(){return this.cardValue;}
}
