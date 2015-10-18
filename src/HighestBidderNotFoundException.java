/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class HighestBidderNotFoundException extends RuntimeException {
    /**
     * 
     * @param msg 
     */
    public HighestBidderNotFoundException(String msg){
        super(msg);
    }
    /**
     * 
     */
    public HighestBidderNotFoundException(){
        super();
    }
}
