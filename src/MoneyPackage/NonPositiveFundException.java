package MoneyPackage;
/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class NonPositiveFundException extends RuntimeException {

    public NonPositiveFundException(String msg){
        super(msg);
    }

    public NonPositiveFundException(){
        super();
    }
}
