package CardPackage;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public enum Rank_ {
    One(1),Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10), Knight(11), Queen(12), King(13), Ace(14);

    private int cardValue;
    /**
     * 
     * @param cardValue 
     */
    Rank_(int cardValue){
        this.cardValue = cardValue;
    }
    /**
     * 
     * @return 
     */
    public int getValue(){return this.cardValue;}
}
