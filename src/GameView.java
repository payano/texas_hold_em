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
    private Label playerNameLabel, playerMoneyLabel, slierAmountLabel;
    private Menu fileMenu;
    private MenuItem exitItem, restartItem, highScoreItem;
    private Slider slider;

    public GameView(GameModel model){
        this.model = model;

        GameController controller = new GameController(model, this);
        initView();
        addEventHandlers(controller);
    }

    private void addEventHandlers(GameController controller) {
        restartItem.setOnAction(event -> controller.startTheGame());
        betButton.setOnAction(event2 -> controller.betHandler());
        callButton.setOnAction(event -> controller.callHandler());
        foldButton.setOnAction(event1 -> controller.foldHandler());
        slider.setOnMouseDragged(event -> updateSlierAmountLabel());
    }

    public void updateSlierAmountLabel(){
        double d = slider.getValue();
        int value = (int) d;
        slierAmountLabel.setText(((Integer)value).toString());
    }

    public int getBet(){
        double d = slider.getValue();
        int value = (int) d;
        return value;
    }

    public void updatePlayer(){
        playerNameLabel.setText(model.getCurrentPlayer().getName());
        playerMoneyLabel.setText(((Double) model.getCurrentPlayer().getMoney()).toString());
        slider.setMin(model.getStake());
        slider.setMax(model.getCurrentPlayer().getMoney());
        slider.setValue(model.getStake());
        slider.setMajorTickUnit(model.getCurrentPlayer().getMoney()/2);
        slierAmountLabel.setText(((Integer) model.getStake()).toString());
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
        slierAmountLabel = new Label("0");


        //Add the buttom bar with buttons ans slider for beting.
        GridPane buttonBar = new GridPane();
        buttonBar.setPadding(new Insets(10, 10, 10, 10));
        buttonBar.setHgap(8);

        //Creat the slider
        slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(100);
        slider.setMinorTickCount(1);
        slider.setBlockIncrement(1);
        //bet button, slider and label.
        buttonBar.add(slider, 6, 0);
        buttonBar.add(betButton, 5, 0);
        buttonBar.add(slierAmountLabel, 7, 0);

        buttonBar.add(playerNameLabel, 0, 1);
        buttonBar.add(playerMoneyLabel, 1, 1);
        buttonBar.add(callButton, 4, 1);
        buttonBar.add(allInButton, 5, 1);
        buttonBar.add(foldButton, 6, 1);

        this.setBottom(buttonBar);

        //temp center
        temp = new TextArea("TEST..");
        this.setCenter(temp);
    }
}
