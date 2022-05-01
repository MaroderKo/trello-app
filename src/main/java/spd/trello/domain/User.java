package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class User extends Domain{
    @Column
    @NotBlank
    private String firstName;
    @Column
    @NotBlank
    private String lastName;
    @Column
    @Email
    private String email;
    @Column
    @NotBlank
    private String login;
    @Column
    @NotBlank
    private String password;

}
