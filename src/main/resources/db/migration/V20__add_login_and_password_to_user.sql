alter table users add column login varchar(20) UNIQUE NOT NULL;
alter table users add column password varchar(72) NOT NULL;