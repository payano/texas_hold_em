package ControllerPackage;

import ModelPackage.GameModel;
import ModelPackage.GameStatusEnum;
import ViewPackage.GameView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 */
public class GameController implements Observer{

    private final GameModel model;
    private final GameView view;
    /**
     * 
     * @param model
     * @param view 
     */
    public GameController(GameModel model, GameView view){
        this.model = model;
        this.view = view;
    }
    /**
     * 
     * @param
     */
    public void cardPushedHandler(){
        view.drawPlayerCard();
    }
    /**
     * 
     */
    public void callHandler(){
        model.call();
        updateRoundStatus();
        view.updatePlayer();
        view.updateCards();
    }
    /**
     * 
     */
    public void foldHandler(){
        model.fold();
        updateRoundStatus();
        view.updatePlayer();
        view.updateCards();
    }
    /**
     * 
     */
    public void betHandler(){
        model.bet(view.getBet());
        updateRoundStatus();
        view.updatePlayer();
        view.updateCards();
    }
    /**
     * 
     */
    public void allInHandler(){
        model.bet(model.getCurrentPlayer().getMoney());
        updateRoundStatus();
        view.updatePlayer();
        view.updateCards();
    }
    /**
     * 
     */
    public void startTheGame(){
        model.initGame();
        model.dealCards(2);
        model.smallAndBigBlind();
        view.savePLayerCard();
        view.updatePlayer();
    }
    /**
     * 
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
     * 
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
     * 
     */
    public void showHighScore(){
        System.out.println("highscore");

    }
    /**
     * 
     */
    private void updateRoundStatus(){
        if(model.getPlayersInGame() == 1){
            view.showAlert(model.getPlayer(model.winnerByFold()).getName() +
                    " is the winnder by all the other players folding!!", "Winner!!");
            startTheGame();
            model.setRoundStatus(GameStatusEnum.PreFlop);
        }

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
                   String wonWith = new String();
                   String winners = new String();

                   for(Integer i: model.setWinner()){
                      wonWith += model.getHandRank(i);
                       winners = model.getPlayer(i).getName();
                   }
                   view.showAlert("Player: " + winners + " won with " + wonWith + "!!","Winner!!");

                   startTheGame();
                   model.setRoundStatus(GameStatusEnum.PreFlop);
               }
               break;
       }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
