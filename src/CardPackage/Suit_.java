package CardPackage;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public enum Suit_ {
    Spades(0), Hearts(1), Clubs(2), Diamonds(3);

    private int cardValue;

    Suit_(int cardValue){
        this.cardValue = cardValue;
    }
    public int getValue(){return this.cardValue;}
}