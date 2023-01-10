alter table INVITATION
    alter column CREATOR BIGINT not null;
alter table COMMENT
    alter column COMMENTATOR BIGINT not null;