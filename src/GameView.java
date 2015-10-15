import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * Created by Johan Svensson och Arvid Bodin on 2015-15-08.
 *
 */
public class GameView extends BorderPane{

    private final GameModel model;

    private TextArea temp;
    private Button callButton, betButton, foldButton, allInButton;
    private Label playerNameLabel, playerMoneyLabel;
    private Menu fileMenu;
    private MenuItem exitItem, restartItem, highScoreItem;

    public GameView(GameModel model){
        this.model = model;

        GameController controller = new GameController(model, this);
        initView();
        addEventHandlers(controller);
    }



    private void addEventHandlers(GameController controller) {
        restartItem.setOnAction(event -> controller.startTheGame());
        callButton.setOnAction(event -> updatePlayer());
    }

    public void updatePlayer(){
        playerNameLabel.setText(model.getCurrentPlayer().getName());
        playerMoneyLabel.setText(((Double) model.getCurrentPlayer().getMoney()).toString());
    }


    private void initView() {
        //Add the menu
        fileMenu = new Menu("File");
        exitItem = new MenuItem("Exit");
        restartItem = new MenuItem("Restart");
        highScoreItem = new MenuItem("Highscore");
        fileMenu.getItems().addAll(highScoreItem, restartItem, exitItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);
        this.setTop(menuBar);

        //Add the buttons
        callButton = new Button("Check/Call");
        betButton = new Button("Raise");
        allInButton = new Button("All in");
        foldButton = new Button("Fold");

        //Add Player name and moneystack
        playerMoneyLabel = new Label("- - -");
        playerNameLabel = new Label("Player");

        GridPane buttonBar = new GridPane();
        buttonBar.setHgap(20);
        buttonBar.addRow(0,
                playerNameLabel,
                playerMoneyLabel,
                callButton,
                betButton,
                allInButton,
                foldButton);
        this.setBottom(buttonBar);

        //temp center
        temp = new TextArea("TEST..");
        this.setCenter(temp);
    }
}
