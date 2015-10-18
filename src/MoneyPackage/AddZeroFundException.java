package MoneyPackage;
/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class AddZeroFundException extends RuntimeException {
    /**
     * 
     * @param msg 
     */
    public AddZeroFundException(String msg){
        super(msg);
    }
    /**
     * 
     */
    public AddZeroFundException(){
        super();
    }
}
