create table if not exists task_history (
                                            id bigserial primary key,
                                            task_id bigint not null references tasks(id) on delete cascade,
    field varchar(100) not null,
    old_value text,
    new_value text,
    changed_by_user_id bigint not null references users(id) on delete restrict,
    changed_at timestamp not null default now()
    );