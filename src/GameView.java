import CardPackage.Card;
import PlayerPackage.HumanPlayer;
import PlayerPackage.Player;
import PlayerPackage.TablePlayer;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.awt.*;

/**
 * Created by Johan Svensson och Arvid Bodin on 2015-15-08.
 *
 */
public class GameView extends BorderPane{

    private final GameModel model;
    private final GameController controller;
    private Canvas canvas;
    private Image image;
    private Image player1Card1, player1Card2, player2Card1, player2Card2;

    private TextField sliderAmountField;
    private Button callButton, betButton, foldButton, allInButton;
    private Label playerNameLabel, playerMoneyLabel,
            currentPlayerLabel, currentPLayerMoneyLabel, missingBetAmountLabel;
    private Menu fileMenu;
    private MenuItem exitItem, restartItem, highScoreItem, loadItem, saveItem;
    private Slider slider;

    private double player1X = 100, player1Y = 230;
    private double player2X = 300, player2Y = 230;

    private double mouseX, mouseY;
    /**
     * 
     * @param model 
     */
    public GameView(GameModel model){
        this.model = model;

        //Creat the controller and the model.
        controller = new GameController(model, this);

        //Draw the scene.
        initView();

        //Add the handerls to all of the items.
        addEventHandlers(controller);

        //Start the game
        controller.startTheGame();

        //Draw the playercards
        updateCards();
    }

    /**
     * Adds handlers to all the objects that needs it.
     * @param controller
     */
    private void addEventHandlers(GameController controller) {
        //restartItem.setOnAction(event -> controller.startTheGame());
        betButton.setOnAction(event2 -> controller.betHandler());
        callButton.setOnAction(event -> controller.callHandler());
        foldButton.setOnAction(event1 -> controller.foldHandler());
        allInButton.setOnAction(event1 -> controller.allInHandler());
        slider.setOnMouseDragged(event -> updateSlierAmountText());
        sliderAmountField.setOnAction(event -> controller.betHandler());
        slider.setOnMouseReleased(event -> updateSlierAmountText());
        canvas.setOnMousePressed(event -> {
            mouseX = event.getX();
            mouseY =  event.getY();
            controller.cardPushedHandler(true);

        });
        canvas.setOnMouseReleased(event -> controller.cardPushedHandler(false));
        saveItem.setOnAction(event -> controller.saveGame());
        loadItem.setOnAction(event -> controller.loadGame());
        highScoreItem.setOnAction(event -> controller.showHighScore());
    }
    /**
     * 
     * @param turnCard 
     */
    public void turnCards(boolean turnCard){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawPlayerCard(gc, turnCard);
    }
    /**
     * 
     */
    public void updateCards(){
        //Paint the whole screen black and reset the colors.
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(37, 38, 40));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.rgb(152, 158, 168));
        gc.setFill(Color.rgb(152, 158, 168));

        gc.fillText("Player: " + model.getPlayer(1).getName(), player1X, player1Y + 110);
        gc.fillText("Money: " + ((Double) model.getPlayer(1).getMoney()).toString(), player1X, player1Y + 125);
        gc.fillText("Player: " + model.getPlayer(2).getName(), player2X, player2Y + 110);
        gc.fillText("Money: " + ((Double) model.getPlayer(2).getMoney()).toString(), player2X, player2Y + 125);

        drawPlayerCard(gc, false);

        if(model.getPlayer(model.findTable()) instanceof TablePlayer){
            double tableX = 65, tableY;
            gc.fillText("Pot: " + ((Double) model.getPlayer(0).getMoney()).toString(), 145, 150);
            for (Card c : model.getPlayer(0).getCards()) {
                image = c.getImage();
                tableX += 80;
                tableY = 40;
                gc.drawImage(image, tableX, tableY);
            }
        }

        /*for (Player onePLayer : model.getPlayers()) {
            if(onePLayer instanceof HumanPlayer) {

                gc.fillText("Player: " + onePLayer.getName(), player1X + 50, player1Y + 110);
                gc.fillText("Money: " + ((Double) onePLayer.getMoney()).toString(), player1X + 50, player1Y + 125);

                drawPlayerCard(gc , false);

            }else {
                    gc.fillText("Pot: " + ((Double) onePLayer.getMoney()).toString(), 145, 150);
                for (Card c : onePLayer.getCards()) {
                    image = c.getImage();
                    tableX += 80;
                    tableY = 40;
                    gc.drawImage(image, tableX, tableY);
                }
            }
        }*/
    }
    /**
     * 
     */
    public void savePLayerCard(){
        player1Card1 = model.getPlayer(1).getCards().get(0).getImage();
        player1Card2 = model.getPlayer(1).getCards().get(1).getImage();
        player2Card1 = model.getPlayer(2).getCards().get(0).getImage();
        player2Card2 = model.getPlayer(2).getCards().get(1).getImage();
    }
    /**
     * 
     * @param gc
     * @param faceSideUp 
     */
    public void drawPlayerCard(GraphicsContext gc, boolean faceSideUp){
        if (faceSideUp){
            if(mouseY > 230 && mouseY < 330 && mouseX > 100 && mouseX < 222) {
                gc.drawImage(player1Card1, player1X, player1Y);
                gc.drawImage(player1Card2, player1X + 50, player1Y);
            }
            else if(mouseY > 230 && mouseY < 330 && mouseX > 200 && mouseX < 422) {

                gc.drawImage(player2Card1, player2X, player2Y);
                gc.drawImage(player2Card2, player2X + 50, player2Y);
            }

        }
        else{
            image = new Image(this.getClass().getResource("/resources/cards/b1fv.png").toString());
            gc.drawImage(image, player1X, player1Y);
            gc.drawImage(image, player1X+50, player1Y);
            gc.drawImage(image, player2X, player1Y);
            gc.drawImage(image, player2X+50, player1Y);
        }
    }


    /**
     * Updates the information in the TextField ned to the
     * slider with the current slider value.
     */
    public void updateSlierAmountText(){
        double d = slider.getValue();
        int value = (int) d;
        sliderAmountField.setText(((Integer) value).toString());
    }

    /**
     * Get the value from the text field to bet with.
     *
     * @return betAmount
     */
    public int getBet(){
        return Integer.parseInt(sliderAmountField.getText());
    }

    /**
     * Updates all the items that needs updates.
     */
    public void updatePlayer(){
        playerNameLabel.setText(model.getCurrentPlayer().getName());
        playerMoneyLabel.setText(((Double) model.getCurrentPlayer().getMoney()).toString());
        slider.setMin(model.getStake());
        missingBetAmountLabel.setText("Call amount: " + model.getMissingBetAmount(model.getCurrentPlayerId()));

        //Update the slider if tha player has more than one money.
        if (model.getCurrentPlayer().getMoney() > 0){
            slider.setMax(model.getCurrentPlayer().getMoney());
            slider.setMajorTickUnit(model.getCurrentPlayer().getMoney()/2);
        }
        else {
            slider.setMax(100);
            slider.setMajorTickUnit(10);
        }
        //Update the slider
        slider.setValue(model.getStake());
        sliderAmountField.setText(((Integer) model.getStake()).toString());

        //If the player have to litte money to call or bet, disable the buttons.
        if(model.getCurrentPlayer().getMoney() < model.getRoundBet(model.findTable())){
            betButton.setDisable(true);
            callButton.setDisable(true);
            sliderAmountField.setDisable(true);
            slider.setDisable(true);
        }else{
            betButton.setDisable(false);
            callButton.setDisable(false);
            sliderAmountField.setDisable(false);
            slider.setDisable(false);
        }

    }
    /**
     * Initiates the view.
     */
    private void initView() {
        //Add the menu
        fileMenu = new Menu("File");
        exitItem = new MenuItem("Exit");
        restartItem = new MenuItem("Restart");
        saveItem = new MenuItem("Save Game");
        loadItem = new MenuItem("Load Game");
        highScoreItem = new MenuItem("Highscore");
        fileMenu.getItems().addAll(highScoreItem, loadItem,saveItem,restartItem, exitItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);
        this.setTop(menuBar);

        //Create the items
        callButton = new Button("Check/Call");
        callButton.setMinWidth(70);
        betButton = new Button("Raise");
        betButton.setMinWidth(70);
        allInButton = new Button("All in");
        allInButton.setMinWidth(70);
        foldButton = new Button("Fold");
        foldButton.setMinWidth(70);

        currentPlayerLabel = new Label("Current Player:");
        currentPLayerMoneyLabel = new Label("Money:");
        playerMoneyLabel = new Label("- - -");
        playerNameLabel = new Label("Player");
        missingBetAmountLabel = new Label("Call amount: ");
        missingBetAmountLabel.setTextFill(Color.rgb(152, 158, 168));
        playerMoneyLabel.setTextFill(Color.rgb(152, 158, 168));
        playerNameLabel.setTextFill(Color.rgb(152, 158, 168));
        currentPLayerMoneyLabel.setTextFill(Color.rgb(152, 158, 168));
        currentPlayerLabel.setTextFill(Color.rgb(152, 158, 168));

        sliderAmountField = new TextField("0");
        sliderAmountField.setMaxWidth(100);

        //Creat the slider
        slider = new Slider();
        slider.setMinWidth(200);
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(100);
        slider.setMinorTickCount(1);
        slider.setBlockIncrement(1);

        //Add the buttom bar with buttons ans slider for beting.
        GridPane buttonBar = new GridPane();
        buttonBar.setStyle("-fx-background-color: #252628;");
        buttonBar.setPadding(new Insets(10, 10, 10, 10));
        buttonBar.setHgap(8);

        buttonBar.add(currentPlayerLabel, 0, 0);
        buttonBar.add(playerNameLabel, 1, 0);
        buttonBar.add(currentPLayerMoneyLabel, 2, 0);
        buttonBar.add(playerMoneyLabel, 3, 0);
        buttonBar.add(missingBetAmountLabel, 4, 0);
        buttonBar.add(callButton, 0, 1);
        buttonBar.add(allInButton, 1, 1);
        buttonBar.add(foldButton, 2, 1);
        buttonBar.add(betButton, 3, 1);
        buttonBar.add(slider, 4, 1);
        buttonBar.add(sliderAmountField, 5, 1);
        this.setBottom(buttonBar);

        //Image tests

        canvas = new Canvas(700,400);
        this.setCenter(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();



        // paint the background
        gc.setFill(Color.rgb(37, 38, 40));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

    }
}
