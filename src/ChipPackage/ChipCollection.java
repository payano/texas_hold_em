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
    public ChipCollection(ArrayList newChips){this.theChips.addAll(newChips);}
    public ChipCollection(Chip newChips){this.theChips.add(newChips);}
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
    private void addChips(ArrayList<Chip> chips){
        if(chips.size() <= 0){
            throw new EmptyChipsException("There is no chips in this arraylist, size is: " + chips.size());
        }
        //add all chips.
        theChips.addAll(chips);
    }
    //Inte klar... måste testas
    public void addChips(Chip chip){theChips.add(chip);}
    /**
     * removeChips - removes one chip from the collection.
     * @param id
     * @return the chip that got removed
     */
    //INTE klar.. måste testas.
    public Chip removeChip(int id){
        if(id < 0 || id > theChips.size()){
            throw new ChipsOutOfRangeException("The chip you are trying to remove does not exist: " + id + ", the total size of arraylist is: " + theChips.size());
        }
        return theChips.remove(id);
    }
    /**
     * toString - prints out chips
     * @return a string
     */
    public String toString(){
        String result = new String();
        for(Chip s : theChips){
            result += s.toString() + "\n";
        }
        return result.trim();
    }

}
