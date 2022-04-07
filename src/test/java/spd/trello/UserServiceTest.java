package spd.trello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import spd.trello.domain.User;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.service.UserService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends BaseTest {
    @Autowired
    UserService userService;
    static User testUser;

    @BeforeEach
    protected void UserInit() {
        User user = new User();
        user.setFirstName("Albert");
        user.setLastName("Second");
        user.setEmail("aaa@aa.a");
        testUser = user;
    }

    @Test
    public void create() {
        User returned = userService.create(testUser);
        assertEquals(testUser, returned);
        assertAll(
                () -> assertEquals("Albert", returned.getFirstName()),
                () -> assertEquals("Second", returned.getLastName().intern()),
                () -> assertEquals("aaa@aa.a", returned.getEmail())
        );

    }

    @Test
    public void readNotExisted()
    {
        assertThrows(ObjectNotFoundException.class,() -> userService.read(UUID.randomUUID()));
    }

    @Test
    public void update()
    {
        userService.create(testUser);
        testUser.setFirstName("Updated");
        testUser.setLastName("Updated");
        testUser.setEmail("Updated");
        userService.update(testUser);
        User newUser = userService.read(testUser.getId());
        assertEquals(testUser, newUser);
        assertAll(
                () -> assertEquals("Updated", newUser.getFirstName()),
                () -> assertEquals("Updated", newUser.getLastName()),
                () -> assertEquals("Updated", newUser.getEmail())
        );
    }

    @Test
    public void delete()
    {
        userService.create(testUser);
        userService.delete(testUser.getId());
        assertThrows(ObjectNotFoundException.class,() -> userService.read(testUser.getId()));
    }

    @Test
    public void getAll()
    {
        List<User> inMemory = userService.getAll();
        inMemory.add(testUser);
        userService.create(testUser);
        UserInit();
        inMemory.add(testUser);
        userService.create(testUser);
        assertEquals(inMemory, userService.getAll());
    }

}
