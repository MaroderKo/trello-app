package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.TimeZone;
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends Domain{
    private String firstName;
    private String lastName;
    private String email;

}
