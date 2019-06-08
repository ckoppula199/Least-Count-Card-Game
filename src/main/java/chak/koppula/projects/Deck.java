package chak.koppula.projects;

import java.util.Random;

public class Deck extends CardCollection {

    Deck(String label) {
        super(label);
        populate();
    }

    //creates a deck of cards
    private void populate() {
        for(int suit = 0; suit < 4; suit++) {
            for(int rank = 2; rank < 15; rank++) {
                addCard(new Card(suit, rank));
            }
        }
    }

    //randomly chooses a card to act as a joker for a round
    public Card chooseJoker() {
        Random random = new Random();
        int index = random.nextInt(size() - 1);
        return removeCard(index);
    }
}
