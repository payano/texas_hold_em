import javafx.application.Application;
import javafx.stage.Stage;

import java.applet.Applet;

/**
 * Created by arvidbodin on 14/09/15.
 *
 *
 */

public class Main extends Application{



    public static void main(String[] args) {
        launch(args);
        //Game game = new Game();
        //game.start();
        //Skapar och startar spelet.
        //Game game = new Game();
        //game.playGame();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameModel game = new GameModel();
        game.start();

    }
}