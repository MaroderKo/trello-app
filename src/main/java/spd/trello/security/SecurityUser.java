package spd.trello.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import spd.trello.domain.Role;
import spd.trello.domain.User;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@Configurable(autowire = Autowire.BY_TYPE)
public class SecurityUser implements UserDetails {


    private String login;
    private String password;
    private Set<SimpleGrantedAuthority> authorities;

    public SecurityUser(String login, String password, Set<SimpleGrantedAuthority> authorities) {
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
