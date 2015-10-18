package ChipPackage;

import java.io.Serializable;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 * HEJ
 */
public class Chip implements Serializable {
    private final ChipEnum chipValue;
    Chip(ChipEnum chipValue){
        //constructor
        this.chipValue = chipValue;
    }
    public int getChipValue(){
        return this.chipValue.getChip();
    }
    public String toString(){
        return chipValue.toString();
    }
}
