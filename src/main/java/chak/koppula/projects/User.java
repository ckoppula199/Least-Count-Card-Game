package chak.koppula.projects;

import java.util.ArrayList;

public class User extends Player {

    User(String name) {
        super(name);
        computer = false;
    }

    //asks user if they want to draw card from top of discard pile of from the draw pile
    public Card playerDraw(Card top, LeastCount lc) {
        System.out.println("Do you want to draw a card from the draw pile or take the top card of discard pile??");
        System.out.println("1) Take from draw pile");
        System.out.println("2) Take top card from discard pile");
        int choice = processChoice(2);
        if (choice == 1) {
            return draw(lc);
        } else {
            int index = lc.discardPile.getCards().indexOf(top);
            lc.discardPile.removeCard(index);
            return top;
        }
    }

    //handles play when no multiples of a rank are present in the hand
    public void playerSingleCard(int choice, Card chosen, LeastCount lc) {
        getHand().removeCard(choice - 1);
        Card top = lc.discardPile.last();
        if (cardMatches(chosen, top)) {
            lc.discardPile.addCard(chosen);
        } else {
            Card topCard = lc.discardPile.last();
            lc.discardPile.addCard(chosen);
            Card drawn = playerDraw(topCard, lc);
            System.out.println("You drew the " + drawn);
            System.out.println();
            getHand().addCard(drawn);
        }
    }

    //handles play when multiples of a rank(s) are present
    public void playerMultipleCards(ArrayList<Card> checkMultiple, LeastCount lc) {
        System.out.println("You can play multiple cards of this rank");
        System.out.println("How many cards of this rank do you wish to play?");
        for (int index = 1; index <= checkMultiple.size(); index++) {
            System.out.println(index + ") " + index);
        }
        int choice = processChoice(checkMultiple.size());
        if (cardMatches(checkMultiple.get(0), lc.discardPile.last())) {
            discardMultiple(checkMultiple, true, choice, lc);
        } else {
            discardMultiple(checkMultiple, false, choice, lc);
        }
    }

    //handles the removal of multiple cards from players hand
    public void discardMultiple(ArrayList<Card> cards, Boolean matched, int numOfCards, LeastCount lc) {
        Card top = lc.discardPile.last();
        removeCards(lc, numOfCards, cards);
        if(!matched) {
            Card drawn = playerDraw(top, lc);
            getHand().addCard(drawn);
            System.out.println("You drew the " + drawn);
            System.out.println();
        }
    }
}
