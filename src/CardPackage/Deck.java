package CardPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by arvidbodin on 14/09/15.
 *
 *
 */
public class Deck {
    private List<Card> theCards = new ArrayList<>();
    private int suits = 4, ranks = 13;

    public Deck (){
        createDeck();
    }

    //Blandar leken.
    public void shuffleCards(){
        Collections.shuffle(theCards);
    }

    //Fyller leken med NYA kort.
    public void fillDeck(){
        theCards.removeAll(theCards);
        createDeck();
    }

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
    public int getNoOfCards() {
        return theCards.size();
    }

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
    private void createDeck(){
        for (RankEnum r : RankEnum.values()){
            for (SuitEnum s : SuitEnum.values()){
                theCards.add(r,s);
            }
        }
    }

}





