package ModelPackage;

/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class SmallAndBigBlindException extends RuntimeException {

    /**
     * 
     * @param msg 
     */
    public SmallAndBigBlindException(String msg){
        super(msg);
    }
    /**
     * 
     */
    public SmallAndBigBlindException(){
        super();
    }
}
