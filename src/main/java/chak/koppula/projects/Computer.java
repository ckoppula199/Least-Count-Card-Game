package chak.koppula.projects;

import java.util.ArrayList;

public class Computer extends Player {

    Computer(String name) {
        super(name);
        computer = true;
    }

    //checks to see if top card is low and if sodraws top card instead of from draw pile
    private Card compDraw(Card top, LeastCount lc) {
        if(top.getCardValue(lc) < 5) {
            int index = lc.discardPile.getCards().indexOf(top);
            return lc.discardPile.removeCard(index);
        } else {
            return draw(lc);
        }
    }

    //removes required cards from hand and draws card if needed
    public void discardMultiple(ArrayList<Card> cards, Boolean matched, int numOfCards, LeastCount lc) {
        Card top = lc.discardPile.last();
        removeCards(lc, numOfCards, cards);
        if (!matched) {
            Card drawn = compDraw(top, lc);
            getHand().addCard(drawn);
        }
    }

    //checks to see if computer should call
    public boolean shouldCompCall(LeastCount lc) {
        if(score(lc) < 20) {
            return true;
        }
        return false;
    }

    /*
    searches the hand for occurrences of double, triple of quad cards like a 5 of diamonds and 5 of clubs
    and sees if their total is greater than the largest card of multiple of cards already seen. returns array
    where first element is the cards rank, second element is the number of cards, and third is the cards actual value
     */
    public ArrayList<Integer> findCompMultiples(LeastCount lc) {
        ArrayList<Integer> cardData = new ArrayList<>();
        int total, highestRank = -1;
        int tempCount, accValue = 0, cardValue, cardCount = 0;
        for(int index = 0; index < getHandSize(); index++) {
            cardValue = getHand().getCard(index).getCardValue(lc);
            total = cardValue;
            tempCount = 1;
            for(int innerIndex = 0; innerIndex < getHandSize(); innerIndex++) {
                if(index == innerIndex) continue;
                if(getHand().getCard(index).getRank() == getHand().getCard(innerIndex).getRank()) {
                    total += cardValue;
                    tempCount++;
                }
            }
            if(total > highestRank) {
                highestRank = getHand().getCard(index).getRank();
                cardCount = tempCount;
                accValue = cardValue;
            }
        }
        cardData.add(highestRank);
        cardData.add(cardCount);
        cardData.add(accValue);
        return cardData;
    }
}
