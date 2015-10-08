package ChipPackage;
/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class EmptyChipsException extends RuntimeException {

    public EmptyChipsException(String msg){
        super(msg);
    }

    public EmptyChipsException(){
        super();
    }
}
