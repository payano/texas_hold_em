package CardPackage;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public enum CardValueEnum {
    None(0), OnePair(1), TwoPair(2), ThreeOfAKind(3), Straight(4), Flush(5), FullHouse(6), FourOfAKind(7), StraightFlush(8), RoyalFlush(9);

    private int cardValue;

    CardValueEnum(int cardValue){
        this.cardValue = cardValue;
    }
    public int getValue(){return this.cardValue;}
}
