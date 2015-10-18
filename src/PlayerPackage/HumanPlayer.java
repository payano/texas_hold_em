package PlayerPackage;

/**
 * Created by Johan Svensson och Arvid Bodin on 2015-10-08.
 *
 */
public class HumanPlayer extends Player {
    /**
     * 
     * @param username 
     */
    public HumanPlayer(String username){
        super(username);
    }
    /**
     * 
     * @param username
     * @param money 
     */
    public HumanPlayer(String username,double money){
        super(username,money);
    }
}
