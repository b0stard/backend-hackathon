create index if not exists idx_users_department_id
    on users(department_id);

create index if not exists idx_users_email
    on users(email);

create index if not exists idx_tasks_author_id
    on tasks(author_id);

create index if not exists idx_tasks_assignee_id
    on tasks(assignee_id);

create index if not exists idx_tasks_department_id
    on tasks(department_id);

create index if not exists idx_tasks_status
    on tasks(status);

create index if not exists idx_tasks_priority
    on tasks(priority);

create index if not exists idx_tasks_deadline
    on tasks(deadline);

create index if not exists idx_tasks_created_at
    on tasks(created_at);

create index if not exists idx_comments_task_id
    on comments(task_id);

create index if not exists idx_comments_author_id
    on comments(author_id);

create index if not exists idx_task_history_task_id
    on task_history(task_id);

create index if not exists idx_task_history_changed_by_user_id
    on task_history(changed_by_user_id);

create index if not exists idx_task_history_changed_at
    on task_history(changed_at);