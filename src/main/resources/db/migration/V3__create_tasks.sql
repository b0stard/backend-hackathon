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