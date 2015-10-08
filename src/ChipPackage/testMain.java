package ChipPackage;

import java.util.ArrayList;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public class testMain {
    public static void main(String[] args) {
        //create some chips
        ArrayList<Chip> someChips = new ArrayList<Chip>();
        someChips.add(new Chip(ChipEnum.Black));
        someChips.add(new Chip(ChipEnum.Black));
        someChips.add(new Chip(ChipEnum.Black));
        someChips.add(new Chip(ChipEnum.Blue));

        //Test chips

        ChipCollection playerChips = new ChipCollection(someChips);

        System.out.println(playerChips.toString());
        System.out.println(playerChips.getChipsValue());

    }
}
