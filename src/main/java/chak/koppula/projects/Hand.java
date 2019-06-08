package chak.koppula.projects;

//class is used not only to represent hands but also piles
public class Hand extends CardCollection {

    Hand(String label) {
        super(label);
    }

    //nice way of displaying game data regarding players hand
    public void display() {
        System.out.println(getLabel() + ": ");
        for(int index = 0; index < size(); index++) {
            System.out.println(getCard(index));
        }
    }
}
