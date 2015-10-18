package CardPackage;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public enum Suit_ {
    Diamonds(0), Hearts(1), Spades(2),Clubs(3);

    private int cardValue;
    /**
     * 
     * @param cardValue 
     */
    Suit_(int cardValue){
        this.cardValue = cardValue;
    }
    /**
     * 
     * @return 
     */
    public int getValue(){return this.cardValue;}
}
