package CardPackage;

/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class NoBigBlindPersonException extends RuntimeException {

    public NoBigBlindPersonException(String msg){
        super(msg);
    }

    public NoBigBlindPersonException(){
        super();
    }
}
