package CardPackage;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public enum SuitEnum {
    Spades(1), Hearts(2), Clubs(3), Diamonds(4);

    private int cardValue;

    SuitEnum(int cardValue){
        this.cardValue = cardValue;
    }
    public int getValue(){return this.cardValue;}
}
