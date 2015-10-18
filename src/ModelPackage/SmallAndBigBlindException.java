package ModelPackage;

/**
 *
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 * SmallAndBigBlindException is thrown when small and big blind cannot be withdrawn from players
 */
public class SmallAndBigBlindException extends RuntimeException {
    /**
     * Constructor sends the message further on to the parent class constructor
     * @param msg sends the message to the parent class
     */
    public SmallAndBigBlindException(String msg){
        super(msg);
    }
    /**
     * Constructor that calls the parent constructor
     */
    public SmallAndBigBlindException(){
        super();
    }
}
