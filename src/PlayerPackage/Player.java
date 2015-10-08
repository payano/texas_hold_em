package PlayerPackage;

import CardPackage.Hand;
import ChipPackage.ChipCollection;
//package CardPackage.;

import CardPackage.Hand;
import MoneyPackage.Money;

import java.util.ArrayList;

/**
 * Created by Arvid Bodin och Johan Svensson on 07/10/15.
 *
 */

abstract class Player {

    private String userName;
    private Hand playerHand;
    private ChipCollection chips;
    private Money money;
    //private Chip

    public Player(String userName){
        this.userName = userName;
        playerHand = new Hand();
        chips = new ChipCollection();
        money = new Money();
    }
    public String getName(){
        return this.userName;
    }


}
