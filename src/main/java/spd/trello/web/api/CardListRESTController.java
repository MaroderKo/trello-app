package spd.trello.web.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.CardList;
import spd.trello.repository.CardListRepository;
import spd.trello.service.AbstractService;
import spd.trello.service.CardListService;
import spd.trello.web.ParentBasedController;

@RestController
@RequestMapping("/api/cardlists")
public class CardListRESTController extends ParentBasedController<CardList, CardListRepository> {
    public CardListRESTController(AbstractService<CardList, CardListRepository> service, CardListService service1) {
        super(service, service1);
    }
}
