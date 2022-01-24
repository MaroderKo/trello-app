package spd.trello.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import spd.trello.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MemberRepository extends AbstractRepository<Member> {

    @Override
    public Member create(Member member) {
        jdbcTemplate.update("INSERT INTO member (id, role,\"user\") VALUES (?, ?, ?);",
                member.getId(),
                member.getRole().name(),
                member.getUser());
        return getById(member.getId());
    }

    @Override
    public Member update(Member Member) {
        jdbcTemplate.update("UPDATE member SET role = ? WHERE id = ?",
                Member.getRole().name(),
                Member.getId());
        return getById(Member.getId());
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update("DELETE FROM member WHERE id = ?", uuid);
    }

    @Override
    public Member getById(UUID uuid) {
        return jdbcTemplate.query("SELECT * FROM member WHERE id = ?", new BeanPropertyRowMapper<>(Member.class), uuid).stream().findFirst().orElse(null);

    }

    @Override
    public List<Member> getAll() {
        return jdbcTemplate.query("SELECT * FROM member", new BeanPropertyRowMapper<>(Member.class));
    }

    @Override
    public List<Member> getParent(UUID id) {
        return jdbcTemplate.query("SELECT * FROM member WHERE \"user\" = ?", new BeanPropertyRowMapper<>(Member.class), id);
    }
}
