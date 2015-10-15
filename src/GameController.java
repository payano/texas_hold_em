
/**
 * Created by Johan Svensson och Arvid Bodin on 2015-15-08.
 *
 */
public class GameController {

    private final GameModel model;
    private final GameView view;

    public GameController(GameModel model, GameView view){
        this.model = model;
        this.view = view;
    }


    public void startTheGame(){
        model.start();
    }
}
