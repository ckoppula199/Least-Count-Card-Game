package chak.koppula.projects;

import java.util.ArrayList;
import java.util.Random;

public abstract class CardCollection {

    private String label;
    private ArrayList<Card> cards;

    //Each array of cards has an associated label such as 'deck' or 'hand'
    CardCollection(String label) {
        this.label = label;
        this.cards = new ArrayList<>();
    }

    //adds card to array
    public void addCard(Card card) {
        cards.add(card);
    }

    //removes specific card at specified index
    public Card removeCard(int index) {
        return cards.remove(index);
    }

    //returns size of current array of cards
    //this is whats known as a wrapper method
    public int size() {
        return cards.size();
    }

    //returns label of array of cards
    public String getLabel() {
        return label;
    }

    //checks to see if array of cards is empty
    public boolean empty() {
        return size() == 0;
    }

    //removes last card so no need to shift cards after it
    //can do this when pack is shuffled and card order is random
    public Card removeCard() {
        int index = size() - 1;
        return removeCard(index);
    }

    /*
    The deal method removes cards from the collection it is invoked on,
    and adds them to the collection it gets as a parameter. The second
    parameter is the number of cards to deal.
     */
    public void deal(CardCollection that, int noOfCards) {
        for(int i = 0; i < noOfCards; i++) {
            Card card = this.removeCard();
            that.addCard(card);
        }
    }

    //returns the cards in current hand
    public ArrayList<Card> getCards() {
        return cards;
    }

    //gets a card at a specific index but does not remove it
    public Card getCard(int i) {
        return cards.get(i);
    }

    //The last method gets the last card (but doesn’t remove it):
    public Card last() {
        int i = size() - 1;
        return cards.get(i);
    }

    //Swaps position of two cards in the array
    public void swapCards(int a, int b) {
        Card temp = getCard(a);
        cards.set(a, getCard(b));
        cards.set(b, temp);
    }

    //shuffles cards into a random order
    public void shuffle() {
        Random random = new Random();
        for(int index = (size() - 1); index > 0; index--) {
            int randomIndex = random.nextInt(index);
            swapCards(index, randomIndex);
        }
    }

    //returns string representation of array
    public String toString() {
        return label + ": " + cards.toString();
    }

    //deals all remaining cards
    public void dealAll(CardCollection that) {
        int size = size() - 1;
        deal(that, size);
    }
}
