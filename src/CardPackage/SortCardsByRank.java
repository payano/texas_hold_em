package CardPackage;

import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * Class that sorts the Book by Title
 */
public class SortCardsByRank implements Comparator<Card> {
    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    @Override
    public int compare(Card a, Card b){
        //sort the list, the highest card gets listed first
        if( ((Integer) a.getRank()).compareTo(b.getRank()) == -1 ){return 1;}
        else if( ((Integer) a.getRank()).compareTo(b.getRank()) == 1 ){return -1;}
        else {return 0;}
    }
}
