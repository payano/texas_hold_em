package CardPackage;
/**
 *
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * NoMatchingCardException is used for matching CardValueEnum with hands in deck.
 * For example if CardValueEnum is set to ThreeOfAKind and when searching through the hand and the
 * three of a kind is missing, this exception is thrown.
 */
public class NoMatchingCardException extends RuntimeException {
    /**
     * Constructor sends the message further on to the parent class constructor
     * @param msg sends message to parent class constructor
     */
    public NoMatchingCardException(String msg){
        super(msg);
    }
    /**
     * Constructor that calls the parent constructor
     */
    public NoMatchingCardException(){
        super();
    }
}
