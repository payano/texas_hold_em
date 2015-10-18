package ModelPackage;

/**
 *
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 */
public class NoSuchPlayerException extends RuntimeException {

    /**
     * 
     * @param msg 
     */
    public NoSuchPlayerException(String msg){
        super(msg);
    }
    /**
     * 
     */
    public NoSuchPlayerException(){
        super();
    }
}
