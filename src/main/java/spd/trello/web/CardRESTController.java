package spd.trello.web;

import spd.trello.domain.Card;
import spd.trello.repository.CardRepository;
import spd.trello.service.AbstractService;

public class CardRESTController extends AbstractRESTController<Card, CardRepository>{
    public CardRESTController(AbstractService<Card, CardRepository> service) {
        super(service);
    }
}
