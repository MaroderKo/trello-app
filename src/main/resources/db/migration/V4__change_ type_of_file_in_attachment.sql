ALTER TABLE attachment ALTER COLUMN file DROP DEFAULT;
ALTER TABLE attachment ALTER COLUMN file TYPE bytea using file::bytea;