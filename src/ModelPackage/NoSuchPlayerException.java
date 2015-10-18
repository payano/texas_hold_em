package ModelPackage;

/**
 *
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 * NoSuchPlayerException is thrown when the playerid does not exist in the arraylist
 */
public class NoSuchPlayerException extends RuntimeException {
    /**
     * Constructor sends the message further on to the parent class constructor
     * @param msg sends the message to the parent class
     */
    public NoSuchPlayerException(String msg){
        super(msg);
    }
    /**
     * Constructor that calls the parent constructor
     */
    public NoSuchPlayerException(){
        super();
    }
}
