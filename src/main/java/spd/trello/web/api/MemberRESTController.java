package spd.trello.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.Card;
import spd.trello.domain.Member;
import spd.trello.repository.CardRepository;
import spd.trello.repository.MemberRepository;
import spd.trello.service.AbstractParentBasedService;
import spd.trello.service.AbstractService;
import spd.trello.web.ParentBasedController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberRESTController extends ParentBasedController<Member, MemberRepository> {
    public MemberRESTController(AbstractService<Member, MemberRepository> service, AbstractParentBasedService<Member, MemberRepository> service1) {
        super(service, service1);
    }

    @Override
    @PreAuthorize("hasAuthority('member.create')")
    public ResponseEntity<Member> Create(Member member) {
        return super.Create(member);
    }

    @Override
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<Member> Read(String id) {
        return super.Read(id);
    }

    @Override
    @PreAuthorize("hasAuthority('member.edit')")
    public ResponseEntity<Member> Update(Member member) {
        return super.Update(member);
    }

    @Override
    @PreAuthorize("hasAuthority('member.delete')")
    public HttpStatus Delete(String id) {
        return super.Delete(id);
    }
}
