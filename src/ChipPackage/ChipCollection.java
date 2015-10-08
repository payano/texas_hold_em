package ChipPackage;

import java.util.ArrayList;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public class ChipCollection {
    ArrayList<Chip> theChips;

    public ChipCollection(ArrayList<Chip> theChips){
        this.theChips = theChips;
    }


    public int getChipsValue(){
        int result = 0;
        for(Chip s : theChips){
            result += s.getChipValue();
        }
        return result;
    }
    public String toString(){
        String result = new String();
        for(Chip s : theChips){
            result += s.toString() + "\n";
        }
        return result.trim();
    }

}
