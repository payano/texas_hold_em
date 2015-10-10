/**
 *
 * Created by arvidbodin on 28/09/15.
 *
 */
public class HighestBidderNotFoundException extends RuntimeException {

    public HighestBidderNotFoundException(String msg){
        super(msg);
    }

    public HighestBidderNotFoundException(){
        super();
    }
}
