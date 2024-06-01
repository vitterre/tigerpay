create table payment_account
(
  id           bigint,
  code         integer     not null,
  ledger       integer     not null,
  phone_number varchar(20) not null,
  profile_uuid uuid        not null,

  constraint payment_account_pk primary key (id)
);

comment on table payment_account is 'Represents the payment account';
comment on column payment_account.id is 'Represents ID of account that is being stored in TigerBeetle';
comment on column payment_account.code is 'Represents the category of payment account';
comment on column payment_account.ledger is 'Partition that this particular payment account belongs to and represents currency group';
comment on column payment_account.phone_number is 'Phone number; used to directly transfer money by phone number';
comment on column payment_account.profile_uuid is 'User profile UUID that owns this and probably other accounts';

create table counterparty_metadata
(
  id     bigint  not null,
  ledger integer not null
);

comment on table counterparty_metadata is 'Represents bank counterparty';
comment on column counterparty_metadata.id is 'Bank account ID in TigerBeetle';
comment on column counterparty_metadata.ledger is 'Bank ledger';