package CardPackage;

import java.util.Comparator;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 * SortCardsByRank sorts the cards in an arraylist by rank
 */

public class SortCardsByRank implements Comparator<Card> {
    /**
     * compare - compares two cards
     * @param a is an card
     * @param b is an card
     * @return an int
     */
    @Override
    public int compare(Card a, Card b){
        //sort the list, the highest card gets listed first
        if( ((Integer) a.getRank()).compareTo(b.getRank()) == -1 ){return 1;}
        else if( ((Integer) a.getRank()).compareTo(b.getRank()) == 1 ){return -1;}
        else {return 0;}
    }
}
