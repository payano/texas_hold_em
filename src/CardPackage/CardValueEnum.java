package CardPackage;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * This enum class represents what the player has in the hand.
 *  */
public enum CardValueEnum {
    None(0), OnePair(1), TwoPair(2), ThreeOfAKind(3), Straight(4), Flush(5), FullHouse(6), FourOfAKind(7), StraightFlush(8), RoyalFlush(9);

    private int cardValue;
    /**
     * CardValueEnum constructor creats and set the cardValue.
     * @param cardValue sets the cardValue variable
     */
    CardValueEnum(int cardValue){
        this.cardValue = cardValue;
    }
    /**
     * getValue returns the value of the card combination
     * @return an int of the card combination
     */
    public int getValue(){return this.cardValue;}
}
