-- Migrations will appear here as you chat with AI

create table projects (
  id bigint primary key generated always as identity,
  created_at timestamp with time zone default now(),
  updated_at timestamp with time zone default now(),
  name text not null,
  description text,
  is_active boolean default true
);

create table tasks (
  id bigint primary key generated always as identity,
  name text not null,
  description text,
  project_id bigint references projects (id) on delete cascade
);

create table technologies (
  id bigint primary key generated always as identity,
  name text not null,
  version text
);

create table sysmodules (
  id bigint primary key generated always as identity,
  name text not null
);

create table features (
  id bigint primary key generated always as identity,
  local_package_manager text,
  technology_id bigint references technologies (id) on delete cascade,
  project_id bigint references projects (id) on delete cascade,
  sysmodule_id bigint references sysmodules (id) on delete cascade
);

create table typetechnologies (
  id bigint primary key generated always as identity,
  name text not null
);

alter table technologies
add column typetechnology_id bigint references typetechnologies (id) on delete cascade;
