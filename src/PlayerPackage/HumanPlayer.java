package PlayerPackage;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * Class ComputerPlayer is a player in the game that is completly controlled by the computer
 */

public class HumanPlayer extends Player {
    /**
     * Human constructor
     * @param username is sent to the parent class constructor
     */
    public HumanPlayer(String username){
        super(username);
    }
    /**
     * Human constructor
     * @param username is sent to the parent class constructor
     * @param money is sent to the parent class constructor
     */
    public HumanPlayer(String username,double money){
        super(username,money);
    }
}
