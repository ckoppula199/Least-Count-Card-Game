package chak.koppula.projects;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LeastCount {

    private Card joker;
    private Computer computer;
    private User user;
    public Hand drawPile;
    public Hand discardPile;
    private Scanner in;
    public Boolean gameOver;
    private int score;
    private boolean resetRound = false;

    public LeastCount() {
        Deck deck = new Deck("Deck");
        deck.shuffle();

        gameOver = false;
        in = new Scanner(System.in);
        joker = deck.chooseJoker();

        int handSize = 5;
        user = new User(getName());
        deck.deal(user.getHand(), handSize);

        score = collectScore();

        computer = new Computer("Computer");
        deck.deal(computer.getHand(), handSize);

        discardPile = new Hand("Discard Pile");
        deck.deal(discardPile, 1);
        drawPile = new Hand("Draw Ple");
        deck.dealAll(drawPile);
    }

    //asks user for a name
    private String getName() {
        System.out.print("Enter your name: ");
        return in.nextLine();
    }

    //returns the score
    public int getScore() {
        return this.score;
    }

    //returns the joker card
    public Card getJoker() {
        return joker;
    }

    //reshuffles deck
    public void reshuffle() {
        Card topCard = discardPile.removeCard();
        discardPile.dealAll(drawPile);
        discardPile.addCard(topCard);
        drawPile.shuffle();
    }

    //provides information as to the current state of the game regarding the user
    private void displayState(Player player) {
        System.out.println();
        System.out.println("The joker is the " + joker);
        System.out.println("The top card is the " + discardPile.last());
        System.out.println();
        player.display();
        System.out.println();;
        System.out.println("Score: " + player.score(this));
        System.out.println("Draw Pile: " + drawPile.size());
        System.out.println();
    }

    //asks if player wants to call or keep playing
    private void playerChoice() {
        System.out.println("1) call");
        System.out.println("2) play turn");
        int choice = user.processChoice(2);
        if(choice == 1) {
            user.call(this, computer, user);
        } else {
            playerTurn();
        }
    }

    //handles users turn
    private void playerTurn() {
        System.out.println("Choose card(s) to play");
        for (int index = 1; index <= user.getHandSize(); index++) {
            System.out.println(index + ") " + user.getHand().getCard(index - 1));
        }
        int choice1 = user.processChoice(user.getHandSize());
        if (choice1 == -1) {
            Card top = discardPile.last();
            Card drawn = user.playerDraw(top, this);
            System.out.println("You drew the " + drawn);
            System.out.println();
            user.getHand().addCard(drawn);
        } else {
            Card chosen = user.getHand().getCard(choice1 - 1);
            ArrayList<Card> checkMultiple = user.findMatchingCards(chosen);
            if (checkMultiple.size() < 2) {
                user.playerSingleCard(choice1, chosen, this);
            } else {
                user.playerMultipleCards(checkMultiple, this);
            }
        }
    }

    //returns array with all cards in hand with same rank as parameter
    private ArrayList<Card> findAllWithThisRank(int rank, Player player) {
        ArrayList<Card> cards = new ArrayList<>();
        for(int index = 0; index < player.getHandSize(); index++) {
            if(player.getHand().getCard(index).getRank() == rank) {
                cards.add(player.getHand().getCard(index));
            }
        }
        return cards;
    }

    //handles computers turn
    private void computerTurn() {
        if (!computer.shouldCompCall(this)) {
            ArrayList<Integer> cardData = computer.findCompMultiples(this);
            ArrayList<Card> matchedCards = findAllWithThisRank(discardPile.last().getRank(), computer);
            if(!matchedCards.isEmpty()) {
                if(matchedCards.get(0).getCardValue(this) * matchedCards.size() + 5 * matchedCards.size() >= cardData.get(2) * cardData.get(1)) {
                    computer.discardMultiple(matchedCards, true, matchedCards.size(), this);
                }
            } else {
                Card top = discardPile.last();
                if (top.getRank() == cardData.get(0)) {
                    ArrayList<Card> cards = findAllWithThisRank(cardData.get(0), computer);
                    computer.discardMultiple(cards, true, cards.size(), this);
                } else {
                    ArrayList<Card> cards = findAllWithThisRank(cardData.get(0), computer);
                    computer.discardMultiple(cards, false, cards.size(), this);
                }
            }
        } else {
            computer.call(this, computer, user);
        }
    }

    private int collectScore() {
        try {
            System.out.println("To what score do you want to play to?");
            int score = in.nextInt();
            return score;
        } catch (InputMismatchException | NumberFormatException error) {
            in.nextLine();
            return collectScore();
        }
    }

    public void resetRound() {
        Deck deck = new Deck("Deck");
        deck.shuffle();

        gameOver = false;
        in = new Scanner(System.in);
        joker = deck.chooseJoker();

        int handSize = 5;
        user.getHand().getCards().clear();
        deck.deal(user.getHand(), handSize);
        computer.getHand().getCards().clear();
        deck.deal(computer.getHand(), handSize);

        discardPile = new Hand("Discard Pile");
        deck.deal(discardPile, 1);
        drawPile = new Hand("Draw Ple");
        deck.dealAll(drawPile);
        resetRound = true;
    }

    //main game flow
    private void playGame() {
        while (!gameOver) {
            displayState(user);
            playerChoice();
            if(gameOver) break;
            if(resetRound) {
                resetRound = false;
                continue;
            }
            computerTurn();
            if(gameOver) break;
            if(resetRound) {
                resetRound = false;
                continue;
            }
            System.out.println("Computer has played its turn");
            System.out.println("Computer has " + computer.getHandSize() + " cards in its hand");
            System.out.println();
        }
        System.out.println("Thank you for playing!!!");
    }

    public static void main(String[] args) {
        LeastCount game = new LeastCount();
        game.playGame();
    }
}
