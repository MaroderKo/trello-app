import spd.trello.domain.*;
import spd.trello.service.CardService;

public class Main {
    public static void main(String[] args) {
        /*Card card = new Card();
        card.setName("TestCard");
        System.out.println(card.getName());
        System.out.println(card);*/
        CardService service = new CardService();
        Card card = service.create();
        service.print(card);
    }
}
