package ModelPackage;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public enum GameStatusEnum {
    PreFlop(0), Flop(1), Turn(2), River(3);

    private int cardValue;
    /**
     * 
     * @param cardValue 
     */
    GameStatusEnum(int cardValue){
        this.cardValue = cardValue;
    }
    /**
     * 
     * @return 
     */
    public int getValue(){return this.cardValue;}
}
