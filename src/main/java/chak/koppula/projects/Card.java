package chak.koppula.projects;

public class Card {

    private static final String[] SUIT = {"Clubs", "Diamonds", "Hearts", "Spades"};
    private static final String[] RANK = {"placeholder", "placeholder", "2", "3", "4", "5", "6",
                                          "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

    private final int suit;
    private final int rank;

    Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    //returns cards rank
    public int getRank() {
        return this.rank;
    }

    //returns cards in game value
    public int getCardValue(LeastCount lc) {
        if(this.rank == lc.getJoker().getRank()) {
            return 0;
        }else if(this.rank > 10) {
            return 10;
        } else {
            return this.rank;
        }
    }

    //returns string representation of card
    public String toString() {
        return RANK[this.rank] + " of " + SUIT[this.suit];
    }
}
