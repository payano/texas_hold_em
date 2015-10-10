package CardPackage;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Arvid on 2015-09-14.
 *
 */
public class Hand {
    private ArrayList<Card> theHand;

    public Hand(){
        theHand  = new ArrayList<>();
    }


    //Returnerar storleken (antalet kort).
    public int getNoOfCards(){
        return theHand.size();
    }

    //Lägger till kortet som skickas till metodet.
    public void addCard(Card card){
        theHand.add(card);
    }
    //add a list of cards.
    public void addCard(ArrayList<Card> card){
        theHand.addAll(card);
    }
    public ArrayList<Card> getAllCards(){
        return theHand;
    }
    public void sortCardsByRank(){
        Collections.sort(theHand, new SortCardsByRank());
    }
    //Returnerar ett kort, om det inte finns något reurneras null.
    public Card getCard(int cardNr){
        if(cardNr >= theHand.size() || cardNr < 0){
            throw new NoSuchCardException("wrong index: " + cardNr);
        }else{
            return theHand.get(cardNr);
        }
    }

    //Tar bort ett kort och tittar så att antalet har minskat.
    public Card removeCard(int cardNr){
        if(cardNr >= theHand.size() || cardNr < 0){
            throw new NoSuchCardException("wrong index" + cardNr);
        }else{
            return theHand.remove(cardNr);
        }
    }

    //Returnerar alla kort i en hand med [] i början och slut.
    public String toString(){
        String info = new String();
        if(theHand.isEmpty()) {
            info = "Finns inga kort.";
            return info;
        }
        for(int i = 0; i < theHand.size(); i++) {
            info += theHand.get(i).toString();
            if(i < theHand.size()-1) info += ", ";
        }
        return "[" + info + "]";
    }
}
