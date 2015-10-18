package PlayerPackage;

/**
 * Created by johan on 2015-10-08.
 *
 */
public class ComputerPlayer extends Player {
    /**
     * 
     * @param username 
     */
    public ComputerPlayer(String username){
        super(username);
    }
    /**
     * 
     * @param username
     * @param money 
     */
    public ComputerPlayer(String username,double money){
        super(username,money);
    }
}
