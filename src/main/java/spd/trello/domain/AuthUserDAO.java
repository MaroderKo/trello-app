package spd.trello.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthUserDAO {
    @NotBlank
    String login;
    @NotBlank
    String password;
}
