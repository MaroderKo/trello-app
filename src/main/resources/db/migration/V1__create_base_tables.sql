CREATE TABLE workspace
(
    id           UUID PRIMARY KEY NOT NULL,
    updated_by   VARCHAR(25)               DEFAULT '',
    created_by   VARCHAR(25)      NOT NULL DEFAULT '',
    created_date TIMESTAMP        NOT NULL DEFAULT now(),
    updated_date TIMESTAMP,
    name         VARCHAR(20)      NOT NULL DEFAULT '',
    description  VARCHAR(50)      NOT NULL DEFAULT ''
);

CREATE TABLE board
(
    id           UUID PRIMARY KEY               NOT NULL,
    workspace_id UUID REFERENCES workspace (id) NOT NULL,
    updated_by   VARCHAR(25)                             DEFAULT '',
    created_by   VARCHAR(25)                    NOT NULL DEFAULT '',
    created_date TIMESTAMP                      NOT NULL DEFAULT now(),
    updated_date TIMESTAMP,
    name         VARCHAR(20)                    NOT NULL DEFAULT '',
    description  VARCHAR(50)                    NOT NULL DEFAULT '',
    archived     boolean                        NOT NULL DEFAULT FALSE,
    visibility   VARCHAR(10)                    NOT NULL DEFAULT 'WORKSPACE'
);

CREATE TABLE cardlist
(
    id           UUID PRIMARY KEY           NOT NULL,
    board_id     UUID REFERENCES board (id) NOT NULL,
    updated_by   VARCHAR(25)                         DEFAULT '',
    created_by   VARCHAR(25)                NOT NULL DEFAULT '',
    created_date TIMESTAMP                  NOT NULL DEFAULT now(),
    updated_date TIMESTAMP,
    name         VARCHAR(20)                NOT NULL DEFAULT '',
    archived     boolean                    NOT NULL DEFAULT FALSE

);

CREATE TABLE card
(
    id           UUID PRIMARY KEY              NOT NULL,
    cardlist_id  UUID REFERENCES cardlist (id) NOT NULL,
    updated_by   VARCHAR(25)                            DEFAULT '',
    created_by   VARCHAR(25)                   NOT NULL DEFAULT '',
    created_date TIMESTAMP                     NOT NULL DEFAULT now(),
    updated_date TIMESTAMP,
    name         VARCHAR(20)                   NOT NULL DEFAULT '',
    description  VARCHAR(50)                   NOT NULL DEFAULT '',
    archived     boolean                       NOT NULL DEFAULT FALSE

);

CREATE TABLE comment
(
    id           UUID PRIMARY KEY,
    card_id      UUID REFERENCES card (id),
    updated_by   VARCHAR(25)          DEFAULT '',
    created_by   VARCHAR(25) NOT NULL DEFAULT '',
    created_date TIMESTAMP   NOT NULL DEFAULT now(),
    updated_date TIMESTAMP,
    name         VARCHAR(25) NOT NULL,
    archived     boolean     NOT NULL DEFAULT FALSE

);

CREATE TABLE label
(
    id      UUID PRIMARY KEY NOT NULL,
    card_id UUID REFERENCES card (id),
    name    VARCHAR(20)      NOT NULL DEFAULT '',
    color   VARCHAR(20)

);

CREATE TABLE checklist
(
    id           UUID PRIMARY KEY NOT NULL,
    card_id      UUID REFERENCES card (id),
    updated_by   VARCHAR(25)               DEFAULT '',
    created_by   VARCHAR(25)      NOT NULL DEFAULT '',
    created_date TIMESTAMP        NOT NULL DEFAULT now(),
    updated_date TIMESTAMP,
    name         VARCHAR(20)      NOT NULL DEFAULT ''

);

CREATE TABLE checkable_item
(
    id           UUID PRIMARY KEY               NOT NULL,
    checklist_id UUID REFERENCES checklist (id) NOT NULL,
    name         VARCHAR(20)                    NOT NULL DEFAULT '',
    checked      bool                                    DEFAULT FALSE
);

CREATE TABLE attachment
(
    id           UUID PRIMARY KEY NOT NULL,
    card_id      UUID REFERENCES card (id),
    updated_by   VARCHAR(25)               DEFAULT '',
    created_by   VARCHAR(25)      NOT NULL DEFAULT '',
    created_date TIMESTAMP        NOT NULL DEFAULT now(),
    updated_date TIMESTAMP,
    name         VARCHAR(20)      NOT NULL DEFAULT '',
    file         VARCHAR(5000)    NOT NULL DEFAULT ''

);

create table reminder
(

    id           UUID        NOT NULL PRIMARY KEY,
    card_id      UUID        NOT NULL REFERENCES card (id),
    updated_by   VARCHAR(25)          DEFAULT '',
    created_by   VARCHAR(25) NOT NULL DEFAULT '',
    created_date TIMESTAMP   NOT NULL DEFAULT now(),
    updated_date TIMESTAMP,
    start        TIMESTAMP,
    "end"        TIMESTAMP,
    remindOn     TIMESTAMP,
    active       BOOLEAN     NOT NULL DEFAULT TRUE

);

CREATE TABLE "user"
(
    id        UUID PRIMARY KEY,
    timezone  TIMESTAMPtz NOT NULL,
    firstname VARCHAR(25) NOT NULL,
    lastname  VARCHAR(25) NOT NULL,
    email     VARCHAR(25) NOT NULL
);

CREATE TABLE member
(
    id     UUID PRIMARY KEY NOT NULL,
    role   VARCHAR(15)      NOT NULL DEFAULT 'GUEST',
    "user" UUID REFERENCES "user" (id)
)