alter table attachment
    add constraint attachment_card_id_fkey
        foreign key (parent_id) references comment;

