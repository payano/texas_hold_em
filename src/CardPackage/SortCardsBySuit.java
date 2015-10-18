package CardPackage;

import java.util.Comparator;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * SortCardsBySuit sorts the cards in an arraylist by suit
 */

public class SortCardsBySuit implements Comparator<Card> {
    /**
     * compare - compares two cards
     * @param a is an card
     * @param b is an card
     * @return an int
     */
    @Override
    public int compare(Card a, Card b){
        //sort the list, the highest card gets listed first
        if( ((Integer) a.getSuit()).compareTo(b.getSuit()) == -1 ){return 1;}
        else if( ((Integer) a.getSuit()).compareTo(b.getSuit()) == 1 ){return -1;}
        else {return 0;}
    }
}
