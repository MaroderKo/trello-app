alter table card add column reminder_id uuid constraint reminderfk references reminder (id);
alter table reminder drop constraint reminder_card_id_fkey;