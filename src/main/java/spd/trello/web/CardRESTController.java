package spd.trello.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.Card;
import spd.trello.repository.CardRepository;
import spd.trello.service.AbstractParentBasedService;
import spd.trello.service.AbstractService;

@RestController
@RequestMapping("/api/cards")
public class CardRESTController extends ParentBasedController<Card, CardRepository>{
    public CardRESTController(AbstractService<Card, CardRepository> service, AbstractParentBasedService<Card, CardRepository> service1) {
        super(service, service1);
    }
}
