package ChipPackage;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public enum ChipEnum {
    White(1), Red(20), Blue(50), Green(100), Black(200);

    private int cardValue;
    /**
     * 
     * @param cardValue 
     */
    ChipEnum(int cardValue){
        this.cardValue = cardValue;
    }
    /**
     * 
     * @return 
     */
    public int getChip(){return this.cardValue;}
}
