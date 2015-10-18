import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Created by arvidbodin on 14/09/15.
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
        GameView view = new GameView(model);


        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(view);
        primaryStage.setTitle("Texas Hold Em");

        Scene scene = new Scene(rootPane,700,500);
        primaryStage.setScene(scene);

        primaryStage.show();

    }
}