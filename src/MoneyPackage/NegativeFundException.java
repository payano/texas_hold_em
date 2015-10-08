package MoneyPackage;
/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class NegativeFundException extends RuntimeException {

    public NegativeFundException(String msg){
        super(msg);
    }

    public NegativeFundException(){
        super();
    }
}
