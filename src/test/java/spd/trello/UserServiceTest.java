package spd.trello;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.User;
import spd.trello.repository.UserRepository;
import spd.trello.service.AbstractService;
import spd.trello.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends BaseTest {
    static AbstractService<User> UserService = new UserService(new UserRepository());
    static User testUser;

    @BeforeEach
    protected void UserInit() {
        User user = new User();
        user.setFirstName("Albert");
        user.setLastName("Second");
        user.setEmail("aaa@aa.a");
        testUser = user;
    }

    @AfterEach
    public void cleaner() throws SQLException {
        try(Connection connection = ConnectionPool.get().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM \"user\"")) {
            ps.execute();
        }
    }

    @Test
    public void create() {
        User returned = UserService.create(null, testUser);
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
        assertNull(UserService.read(UUID.randomUUID()));
    }

    @Test
    public void update()
    {
        UserService.create(null, testUser);
        testUser.setFirstName("Updated");
        testUser.setLastName("Updated");
        testUser.setEmail("Updated");
        UserService.update(testUser);
        User newUser = UserService.read(testUser.getId());
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
        UserService.create(null,testUser);
        UserService.delete(testUser.getId());
        assertNull(UserService.read(testUser.getId()));
    }

    @Test
    public void getAll()
    {
        List<User> inMemory = new ArrayList<>();
        inMemory.add(testUser);
        UserService.create(null, testUser);
        UserInit();
        inMemory.add(testUser);
        UserService.create(null, testUser);
        assertEquals(inMemory, UserService.getAll());
    }

    @Test
    public void getParent() {
        assertNull(UserService.getParent(UUID.randomUUID()));
    }

}
