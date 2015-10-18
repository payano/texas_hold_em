package MoneyPackage;
/**
 *
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * NegativeFundException is used if the player gets a negative value in money.
 */
public class NegativeFundException extends RuntimeException {
    /**
     * Constructor sends the message further on to the parent class constructor
     * @param msg sends the message to the parent class
     */
    public NegativeFundException(String msg){
        super(msg);
    }
    /**
     * Constructor that calls the parent constructor
     */
    public NegativeFundException(){
        super();
    }
}
