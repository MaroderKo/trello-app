package spd.trello.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.Card;
import spd.trello.service.CardService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CardControllerTest extends ParentBasedWebTest implements ControllerActionList {

    @MockBean
    CardService service;

    @Override
    public void getAll() throws Exception {
        MvcResult mvcResult = showAll("/cards");
    }

    Card card;

    @BeforeEach
    public void initBoard() {
        card = new Card();
        card.setName("firstCard");
        card.setDescription("12345");
        card.setArchived(false);
        card.setParentId(UUID.randomUUID());
    }

    @Test
    @Override
    public void createSuccess() throws Exception {
        when(service.create(card)).thenReturn(card);
        MvcResult result = create("/api/cards/create", card);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(UUID.fromString((String) getValue(result, "$.id")), card.getId()),
                () -> assertEquals(getValue(result, "$.createdBy"), card.getCreatedBy()),
                () -> assertEquals(getValue(result, "$.name"), card.getName()),
                () -> assertEquals(getValue(result, "$.description"), card.getDescription()),
                () -> assertEquals(getValue(result, "$.createdDate"), Date(card.getCreatedDate())),
                () -> assertEquals(getValue(result, "$.parentId"), card.getParentId().toString()),
                () -> assertEquals(getValue(result, "$.archived"), card.getArchived()),
                () -> assertEquals(getValue(result, "$.reminder"), card.getReminder())

        );

    }

    @Test
    @Override
    public void getOne() throws Exception {
        when(service.read(card.getId())).thenReturn(card);

        MvcResult result = read("/api/cards/id/" + card.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(getValue(result, "$.id"), card.getId().toString()),
                () -> assertEquals(getValue(result, "$.createdBy"), card.getCreatedBy()),
                () -> assertEquals(getValue(result, "$.name"), card.getName()),
                () -> assertEquals(getValue(result, "$.description"), card.getDescription()),
                () -> assertEquals(getValue(result, "$.createdDate"), Date(card.getCreatedDate())),
                () -> assertEquals(getValue(result, "$.parentId"), card.getParentId().toString()),
                () -> assertEquals(getValue(result, "$.archived"), card.getArchived()));

    }

    @Test
    @Override
    public void getOneNotExisted() throws Exception {
        MvcResult result = read("/api/cards/id/" + card.getId());

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus()),
                () -> assertEquals("Resource not found", result.getResponse().getErrorMessage())
        );
    }

    @Test
    @Override
    public void updateSuccess() throws Exception {
        when(service.read(card.getId())).thenReturn(card);
        MvcResult result = update("/api/cards/update", card);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @Override
    public void updateNotExisted() throws Exception {
        MvcResult result = update("/api/cards/update", card);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Resource not found", result.getResponse().getErrorMessage());

    }

    @Test
    @Override
    public void deleteSuccess() throws Exception {
        when(service.read(card.getId())).thenReturn(card).thenReturn(null);
        MvcResult result = delete("/api/cards/delete/" + card.getId());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @Override
    public void deleteNotExisted() throws Exception {
        MvcResult result = delete("/api/cards/delete/" + card.getId());
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Resource not found", result.getResponse().getErrorMessage());

    }
}
