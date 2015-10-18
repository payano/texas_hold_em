import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

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

    public void cardPushedHandler(boolean showCard){
        view.turnCards(showCard);
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
        view.savePLayerCard();
        view.updatePlayer();
    }
    public void saveGame(){
        // https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        String userDirectoryString = System.getProperty("user.home") + "/Documents/";
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Saved Games", "*.gsv"));
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);
        File selectedFile = fileChooser.showSaveDialog(null);
        try {
            model.saveGame(selectedFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadGame(){
        // https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        String userDirectoryString = System.getProperty("user.home") + "/Documents/";
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Saved Games", "*.gsv"));
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);
        File selectedFile = fileChooser.showOpenDialog(null);
        try {
            model.loadGame(selectedFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        model.createNewHands();
        model.initGame();
        model.dealCards(2);
        view.updateCards();
        view.updatePlayer();
    }
    public void showHighScore(){
        System.out.println("highscore");

    }

    private void updateRoundStatus(){
        if(model.getPlayersInGame() == 1){
            System.out.println(model.winnerByFold());
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
                   for(Integer i: model.setWinner()){
                       System.out.println(model.getHandRank(i));
                   }

                   startTheGame();
                   model.setRoundStatus(GameStatusEnum.PreFlop);
               }
               break;
       }
    }
}
