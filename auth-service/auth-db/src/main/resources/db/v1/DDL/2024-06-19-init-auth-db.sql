create table account_role
(
  uuid  uuid default gen_random_uuid() not null,
  key   varchar(20)                    not null,
  label varchar(255)                   not null,

  constraint account_role_pk primary key (uuid),
  constraint account_role_key_uq unique (key)
);

comment on table account_role is 'Represents role that account can have';
comment on column account_role.uuid is 'Role UUID';
comment on column account_role.key is 'Role key, must be unique and not null';
comment on column account_role.label is 'Role label, must not be null';

create table account
(
  uuid          uuid default gen_random_uuid() not null,
  first_name    varchar(50)                    not null,
  last_name     varchar(50)                    not null,
  middle_name   varchar(50),
  email_address varchar(200)                   not null,
  phone_number  varchar(20)                    not null,
  password      text                           not null,
  role_uuid     uuid                           not null,

  constraint account_pk primary key (uuid),
  constraint account_email_address_uq unique (email_address),
  constraint account_phone_number_uq unique (phone_number),
  constraint account_account_role_fk foreign key (role_uuid) references account_role (uuid)
);

comment on table account is 'Represents user account as a general entity in our payment system';
comment on column account.uuid is 'Account UUID';
comment on column account.first_name is 'First name, must not be null';
comment on column account.last_name is 'Last name, must not be null';
comment on column account.middle_name is 'Middle name, can be null';
comment on column account.email_address is 'Email address, must be unique and not null';
comment on column account.phone_number is 'Phone number, must be unique and not null';
comment on column account.password is 'Encrypted password';
comment on column account.role_uuid is 'Role UUID that belongs to this particular account';

create table refresh_token
(
  uuid         uuid default gen_random_uuid() not null,
  token        varchar(255)                   not null,
  expires_at   timestamp                      not null,
  account_uuid uuid                           not null,

  constraint refresh_token_pk primary key (uuid),
  constraint refresh_token_account_fk foreign key (account_uuid) references account (uuid) on delete cascade
);

comment on table refresh_token is 'Stores refresh token data';
comment on column refresh_token.uuid is 'Refresh token UUID';
comment on column refresh_token.token is 'Refresh token itself';
comment on column refresh_token.expires_at is 'Refresh token expiration date (timestamp)';
comment on column refresh_token.account_uuid is 'Account UUID that owns this particular refresh token';
