alter table attachment alter column parent_id set not null;
alter table card alter column parent_id set not null;
alter table cardlist alter column parent_id set not null;
alter table checkable_item alter column parent_id set not null;
alter table comment alter column parent_id set not null;
alter table reminder alter column parent_id set not null;
alter table board alter column parent_id set not null;