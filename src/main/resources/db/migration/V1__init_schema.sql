create table if not exists departments (
                                           id bigserial primary key,
                                           name varchar(255) not null unique,
    description text
    );

create table if not exists users (
                                     id bigserial primary key,
                                     name varchar(255) not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    role varchar(20) not null check (role in ('ADMIN', 'USER')),
    department_id bigint references departments(id) on delete set null,
    is_active boolean not null default true,
    created_at timestamp not null default now()
    );

create table if not exists tasks (
                                     id bigserial primary key,
                                     title varchar(255) not null,
    description text,
    status varchar(20) not null check (status in ('NEW', 'IN_PROGRESS', 'BLOCKED', 'DONE')),
    priority varchar(20) not null check (priority in ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),
    deadline timestamp not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    author_id bigint not null references users(id) on delete restrict,
    assignee_id bigint not null references users(id) on delete restrict,
    department_id bigint not null references departments(id) on delete restrict
    );

create table if not exists comments (
                                        id bigserial primary key,
                                        task_id bigint not null references tasks(id) on delete cascade,
    author_id bigint not null references users(id) on delete restrict,
    text text not null,
    created_at timestamp not null default now()
    );

create table if not exists task_history (
                                            id bigserial primary key,
                                            task_id bigint not null references tasks(id) on delete cascade,
    field varchar(100) not null,
    old_value text,
    new_value text,
    changed_by_user_id bigint not null references users(id) on delete restrict,
    changed_at timestamp not null default now()
    );

create index if not exists idx_users_department_id on users(department_id);
create index if not exists idx_tasks_author_id on tasks(author_id);
create index if not exists idx_tasks_assignee_id on tasks(assignee_id);
create index if not exists idx_tasks_department_id on tasks(department_id);
create index if not exists idx_tasks_status on tasks(status);
create index if not exists idx_tasks_priority on tasks(priority);
create index if not exists idx_tasks_deadline on tasks(deadline);
create index if not exists idx_comments_task_id on comments(task_id);
create index if not exists idx_task_history_task_id on task_history(task_id);