package CardPackage;
/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * NoSuchCardException is used if the requesting card does not exist.
 */
public class NoSuchCardException extends java.lang.RuntimeException {
    /**
     * Constructor sends the message further on to the parent class constructor
     * @param msg sends message to parent class constructor
     */
    public NoSuchCardException(String msg){
        super(msg);
    }
    /**
     * Constructor that calls the parent constructor
     */
    public NoSuchCardException(){
        super();
    }
}
