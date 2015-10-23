package ControllerPackage;

import ModelPackage.GameModel;
import ModelPackage.GameStatusEnum;
import PlayerPackage.HumanPlayer;
import PlayerPackage.Player;
import ViewPackage.CallListener;
import ViewPackage.GameView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 */
public class GameController implements CallListener {

    private final GameModel model;
    private final GameView view;
    /**
     * The GameController constructor.
     * @param model the model to use.
     * @param view the view to use.
     */
    public GameController(GameModel model, GameView view){
        this.model = model;
        this.view = view;
    }

    /**
     * Handles what to do when the screen i klicked.
     */
    public void cardPushedHandler(){
        view.drawPlayerCard();
    }

    /**
     * Uses its own listener to communicate with the view
     * that the call button has been pushed.
     * Preforms the call in model, then its updates
     * the game status, player info and players cards
     * in the view.
     */
    @Override
    public void callPreformed() {
        model.call();
        updateRoundStatus();
    }

    /**
     * Handles when the fold button is pushed.
     * Preforms the fold in model, then its updates
     * the game status, player info and players cards
     * in the view.
     */
    public void foldHandler(){
        model.fold();
        updateRoundStatus();
        view.updatePlayer();
        view.updateCards();
    }

    /**
     * Handles when the bet button is pushed.
     * Preforms the bet in model, then its updates
     * the game status, player info and players cards
     * in the view.
     */
    public void betHandler(){
        model.bet(view.getBet());
        updateRoundStatus();
        view.updatePlayer();
        view.updateCards();
    }

    /**
     * Handles when the allIn button is pushed.
     * Preforms the bet(with all the players
     * moeny) in model, then its updates
     * the game status, player info and players cards
     * in the view.
     */
    public void allInHandler(){
        model.bet(view.allIn());
        updateRoundStatus();
        view.updatePlayer();
        view.updateCards();
    }

    /**
     * Starts the game and preforms the necessary actions
     * for the game to restart.
     * Initiates the game via model, dealCards, turns the players
     * cards face down, starts the deal animation, deals the blinds
     * , and lastly it shows the players info.
     */
    public void startTheGame(){
        model.initGame();
        for(Player onePlayer : model.getPlayers()){
            if(onePlayer.getMoney() == 0 && onePlayer instanceof HumanPlayer) {
                onePlayer.addMoney(10*model.getStake());
                view.showAlert("Player: " + onePlayer.getName() +
                        " is out of money, gave him 10 times the stake!",
                        "Out of money!",false);
            }
        }
        model.dealCards(2);
        view.turnDownCards();
        view.starDealPlayerCardTimer();
        model.smallAndBigBlind();
        view.savePLayerCard();
        view.updatePlayer();
    }

    /**
     * Save game saves the currentinfo about the players to a serial file
     * that can be loaded via loadGame.
     * @param stage the stage to save from-
     */
    public void saveGame(Stage stage){
        // https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        String userDirectoryString = System.getProperty("user.home") + "/Documents/";
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Saved Games", "*.gsv"));
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);
        File selectedFile = fileChooser.showSaveDialog(stage);
        if(selectedFile != null) {
            try {
                model.saveGame(selectedFile.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads the game for a file of your choice, and starts a new game with
     * that player info.
     * @param stage the stage to load in to.
     */
    public void loadGame(Stage stage){
        // https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        String userDirectoryString = System.getProperty("user.home") + "/Documents/";
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Saved Games", "*.gsv"));
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null) {
            try {
                model.loadGame(selectedFile.toString());
                model.createNewHands();
                model.initGame();
                model.dealCards(2);
                view.updateCards();
                view.updatePlayer();
            } catch (IOException e) {
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if the current status should be updated
     * using the updateRoundStatus in model.
     */
    private void updateRoundStatus(){
        if(model.getPlayersInGame() == 1){
            view.showAlert(model.getPlayer(model.winnerByFold()).getName() +
                    " is the winner by all the other players folding!!",
                    "Winner!!",true);
            model.setRoundStatus(GameStatusEnum.PreFlop);
        }

       switch (model.getRoundStatus()) {
           case PreFlop:
               if (model.roundComplete()) {
                   model.dealTable(3);
                   view.updateCards();
                   model.setRoundStatus(GameStatusEnum.Flop);
       }
               break;
           case Flop:
               if (model.roundComplete()) {
                   model.dealTable(1);
                   view.updateCards();
                   model.setRoundStatus(GameStatusEnum.Turn);
               }
               break;
           case Turn:
               if (model.roundComplete()) {
                   model.dealTable(1);
                   view.updateCards();
                   model.setRoundStatus(GameStatusEnum.River);
               }
               break;
           case River:
               if (model.roundComplete()) {
                   view.updateCards();
                   String wonWith = "";
                   String winners = "";

                   for(Integer i: model.setWinner()){
                      wonWith += model.getHandRank(i);
                       winners += model.getPlayer(i).getName();
                   }
                   view.showAlert("Player: " + winners +
                           " won with " + wonWith + "!!","Winner!!", true);

                   model.setRoundStatus(GameStatusEnum.PreFlop);
               }
               break;
       }
    }
}
