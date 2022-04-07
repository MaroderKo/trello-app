package spd.trello.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.CardList;
import spd.trello.service.CardListService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CardListControllerTest extends ParentBasedWebTest implements ControllerActionList {

    @MockBean
    CardListService service;

    @Override
    public void getAll() throws Exception {
        MvcResult mvcResult = showAll("/cardlists");
    }

    CardList cardList;

    @BeforeEach
    public void initCardList() {
        cardList = new CardList();
        cardList.setName("firstCardList");
        cardList.setArchived(false);
        cardList.setParentId(UUID.randomUUID());
    }

    @Test
    @Override
    public void createSuccess() throws Exception {
        when(service.create(cardList)).thenReturn(cardList);
        MvcResult result = create("/api/cardlists/create", cardList);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(UUID.fromString((String) getValue(result, "$.id")), cardList.getId()),
                () -> assertEquals(getValue(result, "$.createdBy"), cardList.getCreatedBy()),
                () -> assertEquals(getValue(result, "$.name"), cardList.getName()),
                () -> assertEquals(getValue(result, "$.createdDate"), Date(cardList.getCreatedDate())),
                () -> assertEquals(getValue(result, "$.parentId"), cardList.getParentId().toString()),
                () -> assertEquals(getValue(result, "$.archived"), cardList.getArchived())

        );

    }

    @Test
    @Override
    public void getOne() throws Exception {
        when(service.read(cardList.getId())).thenReturn(cardList);

        MvcResult result = read("/api/cardlists/id/" + cardList.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(getValue(result, "$.id"), cardList.getId().toString()),
                () -> assertEquals(getValue(result, "$.createdBy"), cardList.getCreatedBy()),
                () -> assertEquals(getValue(result, "$.name"), cardList.getName()),
                () -> assertEquals(getValue(result, "$.createdDate"), Date(cardList.getCreatedDate())),
                () -> assertEquals(getValue(result, "$.parentId"), cardList.getParentId().toString()),
                () -> assertEquals(getValue(result, "$.archived"), cardList.getArchived()));

    }

    @Test
    @Override
    public void getOneNotExisted() throws Exception {
        MvcResult result = read("/api/cardlists/id/" + cardList.getId());

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus()),
                () -> assertEquals("Resource not found", result.getResponse().getErrorMessage())
        );
    }

    @Test
    @Override
    public void updateSuccess() throws Exception {
        when(service.read(cardList.getId())).thenReturn(cardList);
        MvcResult result = update("/api/cardlists/update", cardList);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @Override
    public void updateNotExisted() throws Exception {
        MvcResult result = update("/api/cardlists/update", cardList);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Resource not found", result.getResponse().getErrorMessage());

    }

    @Test
    @Override
    public void deleteSuccess() throws Exception {
        when(service.read(cardList.getId())).thenReturn(cardList).thenReturn(null);
        MvcResult result = delete("/api/cardlists/delete/" + cardList.getId());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @Override
    public void deleteNotExisted() throws Exception {
        MvcResult result = delete("/api/cardlists/delete/" + cardList.getId());
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Resource not found", result.getResponse().getErrorMessage());

    }
}
