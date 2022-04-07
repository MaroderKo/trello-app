alter table attachment drop column file;
alter table attachment add column link varchar(250);
CREATE TABLE attachment_data
(
    id   UUID NOT NULL,
    file bytea,
    CONSTRAINT pk_attachmentdata PRIMARY KEY (id)
);
alter table attachment add column data_id uuid constraint attachmentdatafk references attachment_data (id);