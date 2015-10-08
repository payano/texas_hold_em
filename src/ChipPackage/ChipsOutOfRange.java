package ChipPackage;
/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class ChipsOutOfRange extends RuntimeException {

    public ChipsOutOfRange(String msg){
        super(msg);
    }

    public ChipsOutOfRange(){
        super();
    }
}
