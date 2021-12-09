import spd.trello.domain.*;
public class Main {
    public static void main(String[] args) {
        Card card = new Card();
        card.setName("TestCard");
        System.out.println(card.getName());
    }
}
