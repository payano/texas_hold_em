import CardPackage.Deck;
import CardPackage.Hand;
import CardPackage.NoSuchCardException;
import PlayerPackage.*;

import java.util.Scanner;

/**
 * Created by Arvid on 2015-09-15.
 *
 */
public class Game {
    private Deck theDeck;
    private Hand playerOne, playerComputer;
    private Scanner scan = new Scanner(System.in);
    private String playing;
    //skapa spelare
   /* HumanPlayer humanus = new HumanPlayer("Nahoj");

    //Skapar kortleken samt två händer.
    public Game(){
        theDeck = new Deck();
        playerOne = new Hand("playerOne", true);
        playerComputer = new Hand("playerComputer", false);
        playing = "y";


    }

    //Spelet.
    public void playGame(){
        //JOHAN IS TEH KING!
        humanus.getName();
        //HAHA JOHAN
    }

    private void tryToDealACard(Hand player){
        try {
            player.addCard(theDeck.dealCard());
        }catch (NoSuchCardException e){
            System.out.println(player.getName() + " tried to take a card but failed.(The Deck is out of cards, filling it up with a new cards.)");
            theDeck = new Deck();
            player.addCard(theDeck.dealCard());
        }
    }

    //Räknar ut värdet på en spelares hand. (klätt = 10)
    public int countHand(Hand playerHand){
        int cardsInHand = playerHand.getNoOfCards();
        int total = 0;

        try {
            for (int i = 0; i < cardsInHand; i++) {
                if (playerHand.getCard(i).getRank() > 10 || playerHand.getCard(i).getRank() == 1)
                    total += 10; //Kl�dda kort �r v�rda 10 och Ace = 11
                else total += playerHand.getCard(i).getRank();
            }
            return total;
        }catch(NoSuchCardException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    //Printar en spelares hand.
    public String  toString(Hand playerHand){
        return  playerHand.toString();

    }*/
}
