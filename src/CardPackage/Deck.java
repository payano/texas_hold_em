package CardPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;

/**
 * Created by arvidbodin on 14/09/15.
 *
 *
 */
public class Deck {
    private List<Card> theCards = new ArrayList<>();
    Image image;
    /**
     * 
     */
    public Deck (){
        createDeck();
    }
    /**
     * 
     */
    //Blandar leken.
    public void shuffleCards(){
        Collections.shuffle(theCards);
    }
    /**
     * 
     */
    //Fyller leken med NYA kort.
    public void fillDeck(){
        theCards.removeAll(theCards);
        createDeck();
    }
    /**
     * 
     * @return
     * @throws NoSuchCardException 
     */
    //Returnerar det "översta" kortet.
    public Card dealCard() throws NoSuchCardException {
        if(theCards.isEmpty()){
            throw new NoSuchCardException("No cards left to deal!!");
        }
        Card temp = theCards.get(0);
        theCards.remove(0);
        return temp;
    }
    //Returnerar antaler kort som är kvar i deck.
    /**
     * 
     * @return 
     */
    public int getNoOfCards() {
        return theCards.size();
    }
    /**
     * 
     * @return 
     */
    public String toString(){
        String info = new String();
        if(theCards.isEmpty()) {
            info = "Finns inga kort kvar.";
            return info;
        }
        for(int i = 0; i < theCards.size(); i++) {
            info = info + theCards.get(i).toString()+"\n";
        }
        return info;
    }

    //Skapar decket utav kort.
    /**
     * 
     */
    private void createDeck(){
        int imgNr = 52;

        for (int i = Rank_.values().length-1; i >= 0 ; i--) {
            for (int j = Suit_.values().length-1; j >= 0 ; j--) {
                //System.out.println("i: " + i + " j: " + j);
                //theCards.add(new Card(i,j,new Image(this.getClass().getResource("../resources/cards/" + imgNr + ".png").toString())));
                //imgNr++;
            }

        }
        for (Rank_ r : Rank_.values()){
            if(r == Rank_.One){continue;}
            for (Suit_ s : Suit_.values()){
                theCards.add(new Card(r,s,new Image(this.getClass().getResource("../resources/cards/" + imgNr + ".png").toString())));
                imgNr--;
            }
        }
    }

}





