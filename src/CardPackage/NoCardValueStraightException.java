package CardPackage;
/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class NoCardValueStraightException extends RuntimeException {
    /**
     * 
     * @param msg 
     */
    public NoCardValueStraightException(String msg){
        super(msg);
    }
    /**
     * 
     */
    public NoCardValueStraightException(){
        super();
    }
}
