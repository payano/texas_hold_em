package PlayerPackage;

import CardPackage.Hand;
import ChipPackage.ChipPlayerCollection;
//package CardPackage.;

import CardPackage.Hand;

import java.util.ArrayList;

/**
 * Created by Arvid Bodin och Johan Svensson on 07/10/15.
 *
 */

abstract class Player {

    private String userName;
    private Hand playerHand;
    private ChipPlayerCollection chips;
    //private Chip

    public Player(String userName){
        this.userName = userName;
        playerHand = new Hand();
        //chips = new ChipPlayerCollection();

        //Hej Johan
        

    }
}
