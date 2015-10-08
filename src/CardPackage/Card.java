package CardPackage;

/**
 * Created by arvidbodin on 14/09/15.
 *
 * Objects of this class represents cards in a deck (of cards). A card is
 * immutable, i.e. once created its rank or suit cannot be changed.
 */

public class Card{

    /**
     * @param rank 1 = Ace, 2 = 2, ...
     * @param suit 1 = spades, 2 = hearts, 3 = diamonds, 4 = clubs
     */

    public Card(int rank, int suit) throws java.lang.IllegalArgumentException{
        if(rankTab.length >= rank && suitTab.length >= suit) {
            this.rank = rank;
            this.suit = suit;
            System.out.println("Card added.");
        }
       else throw new java.lang.IllegalArgumentException( " Suit: " + suit + "Rank: " + rank);
    }



    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public boolean equals(Card other) {
        return this.rank == other.rank && this.suit == other.suit;
    }

    @Override
    public String toString() {
        String info = rankTab[rank - 1] + " of " + suitTab[suit - 1];
        return info;
    }

    private final int rank, suit;

    // Tables for converting rank & suit to text (why static?)
    private static final String[] rankTab = {
            "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "Jack", "Queen", "King"
    };

    private static final String[] suitTab = {
            "spades", "hearts", "clubs", "diamonds"
    };
}
