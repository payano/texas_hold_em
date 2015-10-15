/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class NoSuchPlayerException extends RuntimeException {

    public NoSuchPlayerException(String msg){
        super(msg);
    }

    public NoSuchPlayerException(){
        super();
    }
}
