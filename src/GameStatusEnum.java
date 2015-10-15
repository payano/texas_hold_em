/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public enum GameStatusEnum {
    PreFlop(0), Flop(1), Turn(2), River(3);

    private int cardValue;

    GameStatusEnum(int cardValue){
        this.cardValue = cardValue;
    }
    public int getValue(){return this.cardValue;}
}
