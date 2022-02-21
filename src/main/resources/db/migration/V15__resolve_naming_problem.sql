alter table reminder rename column remindon to remind_on;
alter table checklist rename to check_list;
alter table check_list rename column card_id to parent_id;