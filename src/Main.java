import ControllerPackage.GameController;
import ModelPackage.GameModel;
import ViewPackage.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 *
 */

public class Main extends Application{

    /**
     * Main constructor
     * @param args is arguments from the user input, not used.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * start starts the GUI application
     * @param primaryStage is an argument
     * @throws Exception might throw exceptions
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create the gameModel and the view (this also creates the controller);
        GameModel model = new GameModel();
        GameView view = new GameView(model, primaryStage);

        //Set up the callListener
        GameController controller = new GameController(model, view);
        view.setCallListener(controller);

        //Set up and show the rootPane
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(view);
        primaryStage.setTitle("Texas Hold Em");
        Scene scene = new Scene(rootPane,700,600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();


    }
}