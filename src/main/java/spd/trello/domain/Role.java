package spd.trello.domain;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import spd.trello.security.Permission;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    GUEST(Set.of(Permission.READ)),
    MEMBER(Set.of(Permission.READ, Permission.DELETE, Permission.WRITE, Permission.CREATE)),
    ADMIN(Set.of(Permission.READ, Permission.DELETE, Permission.WRITE, Permission.CREATE, Permission.MEMBER_CREATE, Permission.MEMBER_DELETE, Permission.MEMBER_EDIT)),
    OWNER(Set.of(Permission.READ, Permission.DELETE, Permission.WRITE, Permission.CREATE, Permission.MEMBER_CREATE, Permission.MEMBER_DELETE, Permission.MEMBER_EDIT)),
    ACCESS_DENIED(Set.of());

    @Getter
    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities()
    {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
