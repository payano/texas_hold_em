package ChipPackage;
/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class ChipsOutOfRangeException extends RuntimeException {

    public ChipsOutOfRangeException(String msg){
        super(msg);
    }

    public ChipsOutOfRangeException(){
        super();
    }
}
