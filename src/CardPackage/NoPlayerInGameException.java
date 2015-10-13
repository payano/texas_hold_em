package CardPackage;

/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class NoPlayerInGameException extends RuntimeException {

    public NoPlayerInGameException(String msg){
        super(msg);
    }

    public NoPlayerInGameException(){
        super();
    }
}
