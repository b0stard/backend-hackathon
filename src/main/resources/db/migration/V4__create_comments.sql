create table if not exists comments (
                                        id bigserial primary key,
                                        task_id bigint not null references tasks(id) on delete cascade,
    author_id bigint not null references users(id) on delete restrict,
    text text not null,
    created_at timestamp not null default now()
    );