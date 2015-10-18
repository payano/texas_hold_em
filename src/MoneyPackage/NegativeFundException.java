package MoneyPackage;
/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class NegativeFundException extends RuntimeException {
    /**
     * 
     * @param msg 
     */
    public NegativeFundException(String msg){
        super(msg);
    }
    /**
     * 
     */
    public NegativeFundException(){
        super();
    }
}
