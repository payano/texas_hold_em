package PlayerPackage;

import CardPackage.*;
/**
 * Created by arvidbodin on 08/10/15.
 *
 */
public class testMainPlayer {

    public static void main(String[] args) {

        Player p1,p2, computer;
        Deck theDeck = new Deck();
        theDeck.shuffleCards();

        p1 = new Player("Arvid");
        p2 = new Player("Johan");
        computer = new Player("SuperAI");



        System.out.println("TEST");


        p1.addMoney(200);
        p1.addCard(theDeck.dealCard());
        System.out.println(p1.toString());

    }

}
