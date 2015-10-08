package CardPackage;
/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class NoSuchCardException extends java.lang.RuntimeException {

    public NoSuchCardException(String msg){
        super(msg);
    }

    public NoSuchCardException(){
        super();
    }
}
