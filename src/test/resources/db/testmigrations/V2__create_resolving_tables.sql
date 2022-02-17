CREATE TABLE workspace_member(
    workspace_id uuid not null  constraint workspace_member_id_fk references workspace(id),
    member_id uuid not null  constraint member_workspace_id_fk references member(id),
    constraint workspace_member_pk primary key (workspace_id, member_id)
);

CREATE TABLE board_member(
                                 board_id uuid not null  constraint board_member_id_fk references board(id),
                                 member_id uuid not null  constraint member_board_id_fk references member(id),
                                 constraint board_member_pk primary key (board_id, member_id)
);

CREATE TABLE card_member(
                                 card_id uuid not null  constraint card_member_id_fk references card(id),
                                 member_id uuid not null  constraint member_card_id_fk references member(id),
                                 constraint card_member_pk primary key (card_id, member_id)
);