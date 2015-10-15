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
        //betButton.setOnAction(event -> betHandler());
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
        callButton.setMinWidth(30);
        betButton = new Button("Raise");
        betButton.setMinWidth(30);
        allInButton = new Button("All in");
        allInButton.setMinWidth(30);
        foldButton = new Button("Fold");
        foldButton.setMinWidth(30);

        //Add Player name and moneystack
        playerMoneyLabel = new Label("- - -");
        playerNameLabel = new Label("Player");

        GridPane buttonBar = new GridPane();
        buttonBar.setPadding(new Insets(10, 10, 10, 10));
        buttonBar.setHgap(8);

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(40);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        buttonBar.add(slider, 6, 0);

        buttonBar.add(playerNameLabel, 0, 1);
        buttonBar.add(playerMoneyLabel, 1, 1);
        buttonBar.add(callButton, 4, 1);
        buttonBar.add(betButton, 5, 0);
        buttonBar.add(allInButton, 5, 1);
        buttonBar.add(foldButton, 6, 1);

        this.setBottom(buttonBar);

        //temp center
        temp = new TextArea("TEST..");
        this.setCenter(temp);
    }
}
