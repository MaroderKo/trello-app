package spd.trello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import spd.trello.domain.*;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.service.MemberService;
import spd.trello.service.UserService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest extends BaseTest {
    @Autowired
    MemberService memberService;
    @Autowired
    UserService userService;
    static Member testMember;
    static User testUser;

    @BeforeEach
    protected void MemberInit() {
        User user = new User();
        user.setFirstName("Albert");
        user.setLastName("Second");
        user.setEmail("aaa@aa.a");
        testUser = user;
        userService.create(testUser);

        
        Member member = new Member();
        member.setRole(Role.ADMIN);
        member.setParentId(testUser.getId());
        testMember = member;
    }

    public void regenerateUser()
    {
        testUser = new User();
        testUser.setFirstName("Georg");
        testUser.setLastName("Third");
        testUser.setEmail("bbb@bb.b");
    }

    public void regenerateMember()
    {
        testMember = new Member();
        testMember.setParentId(testUser.getId());
        testMember.setRole(Role.GUEST);
    }

    @Test
    public void create() {
        Member returned = memberService.create(testMember);
        assertEquals(testMember, returned);
        assertAll(
                () -> assertEquals(Role.ADMIN, returned.getRole()),
                () -> assertEquals(testUser.getId(), returned.getParentId())
        );

    }

    @Test
    public void readNotExisted()
    {
        assertThrows(ObjectNotFoundException.class,() -> memberService.read(UUID.randomUUID()));
    }

    @Test
    public void update()
    {
        memberService.create(testMember);
        testMember.setRole(Role.MEMBER);
        memberService.update(testMember);
        Member newMember = memberService.read(testMember.getId());
        assertEquals(testMember, newMember);
        assertAll(
                () -> assertEquals(testMember.getRole(), newMember.getRole()),
                () -> assertEquals(testMember.getParentId(), newMember.getParentId()),
                () -> assertEquals(testMember.getId(), newMember.getId())
        );
    }

    @Test
    public void delete()
    {
        assertThrows(ObjectNotFoundException.class,() -> memberService.read(testMember.getId()));
        memberService.create(testMember);
        assertNotNull(memberService.read(testMember.getId()));
        memberService.delete(testMember.getId());
        assertThrows(ObjectNotFoundException.class,() -> memberService.read(testMember.getId()));
    }

    @Test
    public void getAll()
    {
        List<Member> inMemory = memberService.getAll();
        inMemory.add(testMember);
        memberService.create(testMember);
        regenerateMember();
        inMemory.add(testMember);
        memberService.create(testMember);
        assertEquals(inMemory, memberService.getAll());
    }

    @Test
    public void getParent() {
        User user1 = testUser;
        UUID first = user1.getId();
        Member member1 = testMember;
        memberService.create(member1);
        regenerateUser();
        regenerateMember();
        userService.create(testUser);
        User user2 = testUser;
        UUID second = user2.getId();
        Member member2 = testMember;
        memberService.create(member2);

        assertAll(
                () -> assertEquals(List.of(member1),memberService.getParent(first)),
                () -> assertEquals(List.of(member2),memberService.getParent(second))
        );
    }

}
