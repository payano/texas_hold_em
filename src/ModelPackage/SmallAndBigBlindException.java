package ModelPackage;

/**
 *
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 */
public class SmallAndBigBlindException extends RuntimeException {

    /**
     * 
     * @param msg 
     */
    public SmallAndBigBlindException(String msg){
        super(msg);
    }
    /**
     * 
     */
    public SmallAndBigBlindException(){
        super();
    }
}
