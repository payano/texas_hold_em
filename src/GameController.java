
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

        //startTheGame();
    }

    public void callHandler(){
        model.call();
        updateRoundStatus();
        view.updatePlayer();
    }

    public void foldHandler(){
        model.fold();
        updateRoundStatus();
        view.updatePlayer();
    }

    public void betHandler(){
        model.bet(view.getBet());
        updateRoundStatus();
        view.updatePlayer();
    }

    public void allInHandler(){
        model.bet(model.getCurrentPlayer().getMoney());
        updateRoundStatus();
        view.updatePlayer();
    }


    public void startTheGame(){
        model.initGame();
        model.dealCards(2);
        model.smallAndBigBlind();
        view.updatePlayer();
    }

    private void updateRoundStatus(){
    /*    switch (getRoundStatus){
            case :
                handPoints += 230000000;
                //TBD
                break;
            case StraightFlush:*/
    }
}
