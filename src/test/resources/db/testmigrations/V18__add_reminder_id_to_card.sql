alter table card add column reminder_id uuid constraint reminderfk references reminder (id);