package ModelPackage;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
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
