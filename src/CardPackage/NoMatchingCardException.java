package CardPackage;
/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class NoMatchingCardException extends RuntimeException {

    public NoMatchingCardException(String msg){
        super(msg);
    }

    public NoMatchingCardException(){
        super();
    }
}
