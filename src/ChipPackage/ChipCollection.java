package ChipPackage;

import java.util.ArrayList;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 * ChipCollection class holds an arrayList of chips and methods for accessing them.
 */
public class ChipCollection {
    ArrayList<Chip> theChips;
    /**
     * Constructor
     */
    public ChipCollection(){
        theChips = new ArrayList<>();
    }
    /**
     * getChipsValue
     * @return the amount the chips are worth.
     */
    public int getChipsValue(){
        int result = 0;
        for(Chip s : theChips){
            result += s.getChipValue();
        }
        return result;
    }
    /**
     * addChips - add new chips to theChips
     * @param chip
     */
    //Inte klar... måste testas
    //kan bygga en addChips som tar en int som input och gör om det till chips i olika valörer.
    public void addChips(Chip chip){theChips.add(chip);}
    /**
     * removeChips - removes one chip from the collection.
     * @param id
     * @return
     */
    //INTE klar.. måste testas.
    public Chip removeChip(int id){
        if(id < 0 || id > theChips.size()){
            throw new ChipsOutOfRange("The chip you are trying to remove does not exist: " + id + ", the total size of arraylist is: " + theChips.size());
        }
        return theChips.remove(id);
    }
    /**
     * toString - prints out chips
     * @return
     */
    public String toString(){
        String result = new String();
        for(Chip s : theChips){
            result += s.toString() + "\n";
        }
        return result.trim();
    }

}
