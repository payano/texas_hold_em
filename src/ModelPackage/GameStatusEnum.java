package ModelPackage;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * GameStatusEnum is an enum that knows the status of the game.
 */
public enum GameStatusEnum {
    PreFlop(0), Flop(1), Turn(2), River(3);

    private int gameStatusValue;
    /**
     * GameStatusEnum constructor creats and set the gameStatusValue.
     * @param gameStatusValue is an int representing the current gameStatus
     */
    GameStatusEnum(int gameStatusValue){
        this.gameStatusValue = gameStatusValue;
    }
    /**
     * getValue returns the value of the card combination
     * @return an int of the status of the game
     */
    public int getValue(){return this.gameStatusValue;}
}
