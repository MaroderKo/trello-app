package spd.trello.security;

import lombok.Getter;

public enum Permission {
    MEMBER_CREATE("member.create"),
    MEMBER_DELETE("member.delete"),
    MEMBER_EDIT("member.edit"),
    CREATE("create"),
    WRITE("write"),
    DELETE("delete"),
    READ("read");
    @Getter
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }


}
