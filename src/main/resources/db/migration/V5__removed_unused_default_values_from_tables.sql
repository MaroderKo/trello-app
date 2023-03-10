ALTER TABLE workspace ALTER COLUMN updated_by DROP DEFAULT;
ALTER TABLE workspace ALTER COLUMN created_date DROP DEFAULT;
ALTER TABLE workspace ALTER COLUMN created_by DROP DEFAULT;
ALTER TABLE workspace ALTER COLUMN description DROP DEFAULT;
ALTER TABLE workspace ALTER COLUMN name DROP DEFAULT;

ALTER TABLE board ALTER COLUMN updated_by DROP DEFAULT;
ALTER TABLE board ALTER COLUMN created_date DROP DEFAULT;
ALTER TABLE board ALTER COLUMN created_by DROP DEFAULT;
ALTER TABLE board ALTER COLUMN description DROP DEFAULT;
ALTER TABLE board ALTER COLUMN name DROP DEFAULT;
ALTER TABLE board ALTER COLUMN archived DROP DEFAULT;
ALTER TABLE board ALTER COLUMN visibility DROP DEFAULT;

ALTER TABLE cardlist ALTER COLUMN updated_by DROP DEFAULT;
ALTER TABLE cardlist ALTER COLUMN created_date DROP DEFAULT;
ALTER TABLE cardlist ALTER COLUMN created_by DROP DEFAULT;
ALTER TABLE cardlist ALTER COLUMN name DROP DEFAULT;
ALTER TABLE cardlist ALTER COLUMN archived DROP DEFAULT;

ALTER TABLE card ALTER COLUMN updated_by DROP DEFAULT;
ALTER TABLE card ALTER COLUMN created_date DROP DEFAULT;
ALTER TABLE card ALTER COLUMN created_by DROP DEFAULT;
ALTER TABLE card ALTER COLUMN description DROP DEFAULT;
ALTER TABLE card ALTER COLUMN name DROP DEFAULT;
ALTER TABLE card ALTER COLUMN archived DROP DEFAULT;

ALTER TABLE comment ALTER COLUMN updated_by DROP DEFAULT;
ALTER TABLE comment ALTER COLUMN created_date DROP DEFAULT;
ALTER TABLE comment ALTER COLUMN created_by DROP DEFAULT;
ALTER TABLE comment ALTER COLUMN archived DROP DEFAULT;

ALTER TABLE label ALTER COLUMN name DROP DEFAULT;

ALTER TABLE checklist ALTER COLUMN updated_by DROP DEFAULT;
ALTER TABLE checklist ALTER COLUMN created_date DROP DEFAULT;
ALTER TABLE checklist ALTER COLUMN created_by DROP DEFAULT;
ALTER TABLE checklist ALTER COLUMN name DROP DEFAULT;

ALTER TABLE checkable_item ALTER COLUMN checked DROP DEFAULT;
ALTER TABLE checkable_item ALTER COLUMN name DROP DEFAULT;

ALTER TABLE attachment ALTER COLUMN updated_by DROP DEFAULT;
ALTER TABLE attachment ALTER COLUMN created_date DROP DEFAULT;
ALTER TABLE attachment ALTER COLUMN created_by DROP DEFAULT;
ALTER TABLE attachment ALTER COLUMN name DROP DEFAULT;

ALTER TABLE reminder ALTER COLUMN updated_by DROP DEFAULT;
ALTER TABLE reminder ALTER COLUMN created_date DROP DEFAULT;
ALTER TABLE reminder ALTER COLUMN created_by DROP DEFAULT;
ALTER TABLE reminder ALTER COLUMN active DROP DEFAULT;

ALTER TABLE member ALTER COLUMN role DROP DEFAULT;




