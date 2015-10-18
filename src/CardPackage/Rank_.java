package CardPackage;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * Rank_ class is used for ranking cards in the game.
 */
public enum Rank_ {
    //there is only two - ace or one to king
    //not one to ace (ace is both 1 and 14)
    //ace can be represented as two values, these is included in the Rank_ class

    One(1),Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10), Knight(11), Queen(12), King(13), Ace(14);

    private final int cardValue;
    /**
     * Rank_ constructor
     * @param cardValue is the value of card
     */
    Rank_(int cardValue){
        this.cardValue = cardValue;
    }
    /**
     * getValue
     * @return an int of the value of the card.
     */
    public int getValue(){return this.cardValue;}
}
