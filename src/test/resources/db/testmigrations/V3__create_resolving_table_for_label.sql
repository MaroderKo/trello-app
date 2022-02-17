CREATE TABLE card_label(
                            card_id uuid not null  constraint card_label_id_fk references card(id),
                            label_id uuid not null  constraint label_card_id_fk references label(id),
                            constraint card_label_pk primary key (card_id, label_id)
);

ALTER TABLE label DROP COLUMN card_id;