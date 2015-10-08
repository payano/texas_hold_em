package ChipPackage;

import java.util.ArrayList;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public class ChipCollection {
    ArrayList<Chip> theChips;

    public ChipCollection(){
        theChips = new ArrayList<>();
    }
    public int getChipsValue(){
        int result = 0;
        for(Chip s : theChips){
            result += s.getChipValue();
        }
        return result;
    }
    //Inte klar... måste testas
    public void addChips(Chip chip){theChips.add(chip);}
    //INTE klar.. måste testas.
    public Chip removeChip(int id){
        if(id < 0 || id > theChips.size()){
            throw new ChipsOutOfRange("The chip you are trying to remove does not exist: " + id + ", the total size of arraylist is: " + theChips.size());
        }
        return theChips.remove(id);
    }
    public String toString(){
        String result = new String();
        for(Chip s : theChips){
            result += s.toString() + "\n";
        }
        return result.trim();
    }

}
