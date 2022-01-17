package spd.trello.service;

import spd.trello.domain.Member;
import spd.trello.repository.AbstractRepository;

public class MemberService extends AbstractService<Member>{
    public MemberService(AbstractRepository<Member> repository) {
        super(repository);
    }

}
