Alter TABLE card DROP COLUMN text;
ALTER TABLE comment ADD COLUMN text varchar(250) not null default '';