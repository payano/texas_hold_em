package ViewPackage;

import CardPackage.Card;
import ControllerPackage.GameController;
import ModelPackage.GameModel;
import PlayerPackage.HumanPlayer;
import PlayerPackage.TablePlayer;
import javafx.animation.AnimationTimer;
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
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 */
public class GameView extends BorderPane implements Observer{

    private final GameModel model;
    private final Stage stage;
    private final GameController controller;

    private CallListener callListener;
    private AnimationTimer dealPlayerCardTimer;

    private Canvas canvas;
    private Image image;
    private Image player1Card1, player1Card2, player2Card1, player2Card2;

    private TextField sliderAmountField;
    private Button callButton, betButton, foldButton, allInButton;
    private Label playerNameLabel, playerMoneyLabel, missingBetAmountLabel;
    private MenuItem exitItem, loadItem, saveItem;
    private Slider slider;

    private ArrayList<Point> cardFinalPositions = new ArrayList<>(), cardCurrentPositions = new ArrayList<>();
    private double player1X = 100, player1Y = 230;
    private double player2X = 300, player2Y = 230;
    private boolean showPlayer1Cards, showPlayer2Cards,showPlayerCards;

    private double mouseX, mouseY;

    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    ClassLoader cl = this.getClass().getClassLoader();

    /**
     * GameView constructor. Creates the GameView.
     * @param model the model to use.
     * @param stage the stage to use.
     */
    public GameView(GameModel model, Stage stage){
        this.model = model;
        this.stage = stage;


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
     * Adds the corresponding handler to all the items that needs it.
     * @param controller the controller to use.
     */
    private void addEventHandlers(GameController controller) {
        //EventHandlers assigned to the items.
        betButton.setOnAction(event2 -> {
            controller.betHandler();
        });
        foldButton.setOnAction(event1 -> controller.foldHandler());
        allInButton.setOnAction(event1 -> controller.allInHandler());
        slider.setOnMouseDragged(event -> updateSlierAmountText());
        sliderAmountField.setOnAction(event -> controller.betHandler());
        slider.setOnMouseReleased(event -> updateSlierAmountText());
        canvas.setOnMousePressed(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            controller.cardPushedHandler();

        });
        callButton.setOnAction(event -> {
            callListener.callPreformed();
            updatePlayer();
            updateCards();
        });

        saveItem.setOnAction(event -> controller.saveGame(stage));
        loadItem.setOnAction(event -> controller.loadGame(stage));
        exitItem.setOnAction(event -> System.exit(1));
    }

    /**
     * Turns all the players card face down, and resets the
     * parameters for the animation.
     */
    public void turnDownCards(){
        //Set all the players cards to face down.
        showPlayer1Cards = false;
        showPlayer2Cards = false;
        showPlayerCards = false;

        //Remove all the old animation poitions.
        for (int i = 0; i < 4; i++) {
            cardCurrentPositions.remove(0);
        }

        //Set all the current positions to start position at the deck.
        for (int i = 0; i < 4; i++) {
            cardCurrentPositions.add(new Point(24,40));
        }
    }

    /**
     * Updates the whole canvas with the current cards for players
     * and the table. And the information about the players on the
     * table.
     */
    public void updateCards(){
        //Paint the whole screen black and reset the colors.
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(37, 38, 40));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.rgb(152, 158, 168));
        gc.setFill(Color.rgb(152, 158, 168));

        //Update the labels with the players name and remaining money.
        gc.fillText("Player: " + model.getPlayer(1).getName(), player1X, player1Y + 110);
        gc.fillText("Money: " + ((Double) model.getPlayer(1).getMoney()).toString(), player1X, player1Y + 125);
        gc.fillText("Player: " + model.getPlayer(2).getName(), player2X, player2Y + 110);
        gc.fillText("Money: " + ((Double) model.getPlayer(2).getMoney()).toString(), player2X, player2Y + 125);

        //Draw the players cards
        drawPlayerCard();

        //Draw the tables cards it got so far.
        if(model.getPlayer(model.findTable()) instanceof TablePlayer){
            double tableX = 65;
            gc.fillText("Pot: " + ((Double) model.getPlayer(0).getMoney()).toString(), 145, 150);
            for (Card c : model.getPlayer(0).getCards()) {
                image = c.getImage();
                tableX += 80;
                gc.drawImage(image, tableX, 40);
            }
        }
    }

    /**
     * Saves the players cards for later use in drawPlayerCards.
     */
    public void savePLayerCard(){
        player1Card1 = model.getPlayer(1).getCards().get(0).getImage();
        player1Card2 = model.getPlayer(1).getCards().get(1).getImage();
        player2Card1 = model.getPlayer(2).getCards().get(0).getImage();
        player2Card2 = model.getPlayer(2).getCards().get(1).getImage();
    }

    /**
     * Draws the players cards. Face side up or face side down.
     * It toggles what side is up for a player if the mose was
     * with in the players cards when klicked.
     */
    public void drawPlayerCard(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if(showPlayerCards) {

            //If the mouse was over one of the players cards, toggle the side to show.
            if (mouseY > 230 && mouseY < 330 && mouseX > 100 && mouseX < 222) showPlayer1Cards = !showPlayer1Cards;
            if (mouseY > 230 && mouseY < 330 && mouseX > 300 && mouseX < 422) showPlayer2Cards = !showPlayer2Cards;

            //Show eather the front or the back of the players cards.
            if (showPlayer1Cards) {
                gc.drawImage(player1Card1, player1X, player1Y);
                gc.drawImage(player1Card2, player1X + 50, player1Y);
            } else {
                Image image  = new Image(cl.getResourceAsStream("resources/cards/b1fv.png"));
                gc.drawImage(image, player1X, player1Y);
                gc.drawImage(image, player1X + 50, player1Y);
            }
            if (showPlayer2Cards) {
                gc.drawImage(player2Card1, player2X, player2Y);
                gc.drawImage(player2Card2, player2X + 50, player2Y);
            } else {
                image = new Image(cl.getResourceAsStream("resources/cards/b1fv.png"));
                gc.drawImage(image, player2X, player1Y);
                gc.drawImage(image, player2X + 50, player1Y);
            }
            mouseX = 0;
            mouseY = 0;
        }

        //Draw the "deck"
        image = new Image(cl.getResourceAsStream("resources/cards/b1fv.png"));
        gc.drawImage(image, 20, 40);
        gc.drawImage(image, 20+2, 40);
        gc.drawImage(image, 20+4, 40);
    }

    /**
     * Updates the information in the TextField next to the
     * slider with the current slider value.
     */
    public void updateSlierAmountText(){
        double d = slider.getValue();
        int value = (int) d;
        sliderAmountField.setText(((Integer) value).toString());
    }

    /**
     * Get the value from the text field to bet with.
     * @return the bet amount
     */
    public int getBet(){
        int bet;
        //Get the bet from the current poition of the slier.
        bet = Integer.parseInt(sliderAmountField.getText());
        return bet;
    }

    /**
     * Get the value from the text field to bet with.
     *
     * @return  the is the bet amount.
     */
    public int allIn(){
        int bet;
        //Get the amount the bet is from the slider.
        bet = (int) slider.getMax();
        return bet;
    }

    /**
     * Updates all the information about the Current player in
     * the bottom menu. It sets the labels and makes it so that
     * the current player cant place a bet that is to large.
     * Disables or enables the buttons when it should not be
     * used.
     */
    public void updatePlayer(){

        playerNameLabel.setText(model.getCurrentPlayer().getName());
        playerMoneyLabel.setText(((Double) model.getCurrentPlayer().getMoney()).toString());
        slider.setMin(model.getStake());
        missingBetAmountLabel.setText("Call amount: " + model.getMissingBetAmount(model.getCurrentPlayerId()));

        //Update the slider if tha player has more than one money.
        if (model.getCurrentPlayer().getMoney() > 0){

            ArrayList<Integer> maxBet = new ArrayList<>();

            //Find how much the a player could bet by there reminding money
            //and adding the amount they have already bet that round.
            for (int i = 0; i < model.getPlayers().size(); i++) {
                if (model.getPlayer(i) instanceof HumanPlayer){
                    maxBet.add((int) model.getPlayer(i).getMoney() - (int) model.getRoundBet(model.findTable()) + (int) model.getRoundBet(i));
                }
            }
            Collections.sort(maxBet);

            //If the amount you cen bet is 0, disable the bet options.
            if(maxBet.get(0) == 0) {
                allInButton.setDisable(true);
                betButton.setDisable(true);
                sliderAmountField.setDisable(true);
                slider.setDisable(true);
                slider.setMax(0);

            }
            else {
                slider.setMax(maxBet.get(0));
                slider.setMajorTickUnit(maxBet.get(0) / 2);
            }
        }
        else {
            slider.setMax(100);
            slider.setMajorTickUnit(10);
        }
        //Update the slider
        slider.setValue(model.getStake());
        sliderAmountField.setText(((Integer) model.getStake()).toString());

        //Disable allIn, bet and the slider if it should not be used.
        if(model.getCurrentPlayer().getMoney() == 0 ||
                model.getCurrentPlayer().getMoney() == model.getRoundBet(model.findTable()) ||
                model.getLastPlayer().getMoney() == 0 ||
                model.getRoundBet(model.findTable()) >= model.getCurrentPlayer().getMoney()){
            allInButton.setDisable(true);
            betButton.setDisable(true);
            sliderAmountField.setDisable(true);
            slider.setDisable(true);
        }else {
            allInButton.setDisable(false);
            sliderAmountField.setDisable(false);
            slider.setDisable(false);
            betButton.setDisable(false);
        }

        //Disable the fold botton.
        if(model.getCurrentPlayer().getMoney() == 0){
            foldButton.setDisable(true);
        }else foldButton.setDisable(false);
    }

    /**
     * Initiates the view with all the items and coordinates.
     */
    private void initView() {
        //Add the menu
        Menu fileMenu = new Menu("File");
        exitItem = new MenuItem("Exit");
        saveItem = new MenuItem("Save Game");
        loadItem = new MenuItem("Load Game");
        fileMenu.getItems().addAll(loadItem,saveItem,exitItem);
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

        Label currentPlayerLabel = new Label("Current Player:");
        Label currentPLayerMoneyLabel = new Label("Money:");
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
        buttonBar.add(sliderAmountField, 5+1, 1);
        this.setBottom(buttonBar);

        //Set up the canvas.
        canvas = new Canvas(700,500);
        this.setCenter(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        // paint the background
        gc.setFill(Color.rgb(37, 38, 40));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Set up the animation timer and animation
        dealPlayerCardTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                movePlayerCards();

            }
        };

        //Set up the final poition for each card.
        cardFinalPositions.add(new Point((int)player1X,(int)player1Y));
        cardFinalPositions.add(new Point((int)player1X+50,(int)player1Y));
        cardFinalPositions.add(new Point((int)player2X,(int)player2Y));
        cardFinalPositions.add(new Point((int)player2X+50,(int)player2Y));

        //Set all the current positions to start position at the deck.
        for (int i = 0; i < 6; i++) {
            cardCurrentPositions.add(new Point(24,40));
        }

        model.addObserver(this);
    }

    /**
     * Used to move the players 4 cards from the deck to
     * the players hands 3/5 pixels each call by the
     * AnimationTimer.
     */
    public void movePlayerCards(){
        boolean cardsInPlace = true;
        updateCards();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        image = new Image(cl.getResourceAsStream("resources/cards/b1fv.png"));

        //Loop thru all the cards an move the towards there final position.
        for (int i = 0; i < 4; i++) {
            double x = cardCurrentPositions.get(i).getX();
            double y = cardCurrentPositions.get(i).getY();

            if(cardCurrentPositions.get(i).getX() < cardFinalPositions.get(i).getX()){
                cardCurrentPositions.get(i).setLocation(x+=4,y);
                cardsInPlace = false;
            }

            if(cardCurrentPositions.get(i).getY() < cardFinalPositions.get(i).getY()){
                cardCurrentPositions.get(i).setLocation(x,y+=2);
                cardsInPlace = false;
            }
            gc.drawImage(image,x,y);
        }

        //If all the cards are in place stop the AnimationTimer.
        if(cardsInPlace){
            showPlayerCards = true;
            updateCards();
            updatePlayer();
            dealPlayerCardTimer.stop();
        }

    }

    /**
     * Starts the AnimationTimer to animate the dealing
     * of the players cards.
     */
    public void starDealPlayerCardTimer(){
        //Start the animation timer.
        dealPlayerCardTimer.start();
    }

    /**
     * Show an alart with the message in the middle and title
     * at the top. The close botton can be set with
     * enableStartGame to choose if the button should start
     * the game when klicked.
     * @param message the message to be show in the main body
     * @param title the titel on the top of the window.
     */
    public void showAlert(String message, String title, boolean enableStartGame) {
        alert.setHeaderText("");
        alert.setTitle(title);
        alert.setContentText(message);
        //If true the button will restart the game or not.
        if(enableStartGame) alert.setOnCloseRequest(event -> controller.startTheGame());
        alert.show();
    }

    /**
     * Sets up the callListener.
     * @param callListener the listener to use.
     */
    public void setCallListener(CallListener callListener) {
        this.callListener = callListener;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }
}


