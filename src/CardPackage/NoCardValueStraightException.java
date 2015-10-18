package CardPackage;
/**
 *
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 * If ModelPackage.GameModel found a straight and later on did not find it.
 */
public class NoCardValueStraightException extends RuntimeException {
    /**
     * Constructor sends the message further on to the parent class constructor
     * @param msg sends message to parent class constructor
     */
    public NoCardValueStraightException(String msg){
        super(msg);
    }
    /**
     * Constructor that calls the parent constructor
     */
    public NoCardValueStraightException(){
        super();
    }
}
