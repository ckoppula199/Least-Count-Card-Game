package chak.koppula.projects;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Player {

    private String name;
    private Hand hand;
    public boolean computer;
    private int score;

    Player(String name) {
        this.name = name;
        this.hand = new Hand(name);
        this.score = 0;
    }

    //returns name
    public String getName() {
        return name;
    }

    //returns hand
    public Hand getHand() {
        return hand;
    }

    //returns hand size
    public int getHandSize() {
        return hand.size();
    }

    //finds cards in hand that match the argument card
    public ArrayList<Card> findMatchingCards(Card top) {
        ArrayList<Card> cards;
        cards = addMatchingCards(top);
        return cards;
    }

    //adds cards to an array if the ranks of the card and parameter card match
    private ArrayList<Card> addMatchingCards(Card top) {
        ArrayList<Card> cards = new ArrayList<>();
        for(int index = 0; index < hand.size(); index++) {
            Card card = hand.getCard(index);
            if(cardMatches(card, top)) {
                cards.add(card);
            }
        }
        return cards;
    }

    //draws a card from the draw pile
    public Card draw(LeastCount lc) {
        if(lc.drawPile.empty()) {
            System.out.println("reshuffling discard pile to refill draw pile");
            lc.reshuffle();
        }
        return  lc.drawPile.removeCard();
    }

    //helper function for call
    private void callDataDisplay(Player user, Player computer) {
        System.out.println("Your hand");
        System.out.println();
        user.display();
        System.out.println();
        System.out.println("Computers hand");
        System.out.println();
        computer.display();
        System.out.println();
    }

    //ends the game when a player calls and displays hands, scores and results
    public void call(LeastCount lc, Player computer, Player user) { //computer needs to be opposite of caller
        callDataDisplay(user, computer);
        if(user.score(lc) >= computer.score(lc)) {
            System.out.println(computer.getName() + " wins the round");
            if(this == user) {
                user.score += 40;
            } else {
                user.score += user.score(lc);
            }
        } else {
            System.out.println(user.getName() + " wins the round");
            if(this == computer) {
                computer.score += 40;
            } else {
                computer.score += computer.score(lc);
            }
        }
        System.out.println("Your score is : " + user.score);
        System.out.println("Computers Score is : " + computer.score);
        System.out.println();
        checkGameOver(user, computer, lc);
    }

    private void checkGameOver(Player user, Player computer, LeastCount lc) {
        if(user.score >= lc.getScore() || computer.score >= lc.getScore()) {
            System.out.println();
            System.out.println("GAME OVER");
            System.out.println("FINAL SCORES");
            System.out.println(user.name + ": " + user.score);
            System.out.println(computer.name + ": " + computer.score);
            if(user.score(lc) >= lc.getScore()) {
                System.out.println("Computer wins the game!!!");
            } else {
                System.out.println(user.name + " wins the game!!!");
            }
            lc.gameOver = true;
        } else {
            lc.resetRound();
        }
    }

    //used to determine user input
    public int processChoice(int n) {
        if(n == 0) return -1;
        Scanner in = new Scanner(System.in);
        ArrayList<Integer> choices = new ArrayList<>();
        for(int index = 1; index <= n; index++) {
            choices.add(index);
        }
        int choice;
        try {
            choice = Integer.parseInt(in.next());
            while (!choices.contains(choice)) {
                System.out.println("Invalid choice");
                choice = Integer.parseInt(in.next());
            }
        } catch (NumberFormatException nfe) {
            System.out.println("invalid choice");
            return processChoice(n);
        }
        return choice;
    }

    //handles removal of cards from a players hand
    public void removeCards(LeastCount lc, int numOfCards, ArrayList<Card> cards) {
        for (int index = 0; index < numOfCards; index++) {
            lc.discardPile.addCard(cards.get(index));
            int removeIndex = getHand().getCards().indexOf(cards.get(index));
            getHand().removeCard(removeIndex);
        }
    }

    //checks if two cards have the same rank
    public boolean cardMatches(Card one, Card two) {
        return one.getRank() == two.getRank();
    }

    //works out score of players current hand
    public int score(LeastCount lc) {
        int sum = 0;
        for(int index = 0; index < hand.size(); index++) {
            Card card = hand.getCard(index);
            int rank = card.getRank();
            if(rank == lc.getJoker().getRank()) {
                sum += 0;
            } else if(rank > 10) {
                sum += 10;
            } else {
                sum += rank;
            }
        }
        return sum;
    }

    //displays hand
    public void display() {
        hand.display();
    }

    public abstract void discardMultiple(ArrayList<Card> cards, Boolean matched, int numOfCards, LeastCount lc);
}
