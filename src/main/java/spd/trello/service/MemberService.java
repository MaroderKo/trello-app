package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Member;
import spd.trello.domain.Role;
import spd.trello.repository.MemberRepository;
import spd.trello.security.SecurityConfig;

@Service
public class MemberService extends AbstractParentBasedService<Member, MemberRepository>{

    @Autowired
    UserService userService;

    public MemberService(MemberRepository repository) {
        super(repository);
    }

    public Member ownerMember()
    {
        Member member = new Member();
        member.setRole(Role.OWNER);
        member.setParentId(userService.getByLogin(SecurityConfig.getUserName()).getId());
        return repository.save(member);
    }

}
