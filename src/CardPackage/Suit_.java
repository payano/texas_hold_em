package CardPackage;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public enum Suit_ {
    Clubs(0), Spades(1), Hearts(2), Diamonds(3);

    private int cardValue;

    Suit_(int cardValue){
        this.cardValue = cardValue;
    }
    public int getValue(){return this.cardValue;}
}
