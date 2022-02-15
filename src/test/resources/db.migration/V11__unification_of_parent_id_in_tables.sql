alter table attachment rename column card_id TO parent_id;
alter table card rename column cardlist_id TO parent_id;
alter table cardlist rename column board_id TO parent_id;
alter table checkable_item rename column checklist_id TO parent_id;
alter table comment rename column card_id TO parent_id;
alter table reminder rename column card_id TO parent_id;
alter table board rename column workspace_id TO parent_id;