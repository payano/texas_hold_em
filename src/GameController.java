

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

    public void callHandler(){
        model.call();
        updateRoundStatus();
        view.updatePlayer();
        view.updateCards();
    }

    public void foldHandler(){
        model.fold();
        updateRoundStatus();
        view.updatePlayer();
        view.updateCards();
    }

    public void betHandler(){
        model.bet(view.getBet());
        updateRoundStatus();
        view.updatePlayer();
        view.updateCards();
    }

    public void allInHandler(){
        model.bet(model.getCurrentPlayer().getMoney());
        updateRoundStatus();
        view.updatePlayer();
        view.updateCards();
    }


    public void startTheGame(){
        model.initGame();
        model.dealCards(2);
        model.smallAndBigBlind();
        view.updatePlayer();
    }

    private void updateRoundStatus(){
       switch (model.getRoundStatus()) {
           case PreFlop:
               if (model.roundComplete()) {
                   model.dealTable(3);
                   //view.updateTable();
                   view.updateCards();
                   model.setRoundStatus(GameStatusEnum.Flop);
                   System.out.println("\nPreFlop -> Flop\n");
               }
               break;
           case Flop:
               if (model.roundComplete()) {
                   model.dealTable(1);
                   //view.updateTable();
                   view.updateCards();
                   model.setRoundStatus(GameStatusEnum.Turn);
                   System.out.println("\nFlop -> Turn\n");
               }
               break;
           case Turn:
               if (model.roundComplete()) {
                   model.dealTable(1);
                   //view.updateTable();
                   view.updateCards();
                   model.setRoundStatus(GameStatusEnum.River);
                   System.out.println("\nTurn -> River\n");
               }
               break;
           case River:
               if (model.roundComplete()) {
                   System.out.println("\nRiver -> PreFlop\n");
                   view.updateCards();
                   for(Integer i: model.setWinner()){
                       System.out.println(model.getHandRank(i));
                   }

                   startTheGame();
                   model.setRoundStatus(GameStatusEnum.PreFlop);
               }
               break;
       }
        //johan for testing
        model.setNextPlayer();
    }
}
