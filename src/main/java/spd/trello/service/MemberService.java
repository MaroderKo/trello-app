package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Member;
import spd.trello.repository.AbstractRepository;

@Service
public class MemberService extends AbstractService<Member>{
    public MemberService(AbstractRepository<Member> repository) {
        super(repository);
    }

}
