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
     */s
    private final RankEnum rank;
    private final SuitEnum suit;

    public Card(RankEnum rank, SuitEnum suit) throws java.lang.IllegalArgumentException{
            this.rank = rank;
            this.suit = suit;
            System.out.println("Card added.");
    }
    public int getRank() {
        return rank.getValue();
    }

    public int getSuit() {
        return suit.getValue();
    }

    public boolean equals(Card other) {
        return this.rank == other.rank && this.suit == other.suit;
    }

    @Override
    public String toString() {
        String info = rank.toString() + " of " + suit.toString();
        return info;
    }
}
