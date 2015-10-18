package CardPackage;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * NoPlayerInGameException is used if there is no player in game this exception is thrown,
 * at least two players is needed for playing texas hold em
 */
public class NoPlayerInGameException extends RuntimeException {
    /**
     * Constructor sends the message further on to the parent class constructor
     * @param msg sends message to parent class constructor
     */
    public NoPlayerInGameException(String msg){
        super(msg);
    }
    /**
     * Constructor that calls the parent constructor
     */
    public NoPlayerInGameException(){
        super();
    }
}
