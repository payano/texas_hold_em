import ModelPackage.GameModel;
import ViewPackage.GameView;
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
     * 
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * 
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Creat the gameModel and the view (this also creates the controller);
        GameModel model = new GameModel();
        GameView view = new GameView(model, primaryStage);


        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(view);
        primaryStage.setTitle("Texas Hold Em");

        Scene scene = new Scene(rootPane,700,500);
        primaryStage.setScene(scene);

        primaryStage.show();

    }
}