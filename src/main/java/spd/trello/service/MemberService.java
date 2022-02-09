package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Member;
import spd.trello.repository.MemberRepository;

@Service
public class MemberService extends AbstractService<Member, MemberRepository>{
    public MemberService(MemberRepository repository) {
        super(repository);
    }

}
