insert into departments (id, name, description)
values
    (1, 'Администрация', 'Системное управление'),
    (2, 'Операционный отдел', 'Внутренние процессы'),
    (3, 'Маркетинг', 'Продвижение и реклама'),
    (4, 'HR', 'Подбор и адаптация сотрудников')
    on conflict (id) do nothing;

insert into users (id, name, email, password, role, department_id, is_active, created_at)
values
    (1, 'Администратор', 'admin@company.ru', '123456', 'ADMIN', 1, true, now()),
    (2, 'Анна Смирнова', 'anna@company.ru', '123456', 'USER', 2, true, now()),
    (3, 'Дмитрий Козлов', 'dmitry@company.ru', '123456', 'USER', 3, true, now()),
    (4, 'Елена Орлова', 'elena@company.ru', '123456', 'USER', 4, true, now())
    on conflict (id) do nothing;

insert into tasks (
    id,
    title,
    description,
    status,
    priority,
    deadline,
    created_at,
    updated_at,
    author_id,
    assignee_id,
    department_id
)
values
    (
        101,
        'Подготовить отчет по продажам',
        'Собрать данные за март, проверить цифры и подготовить презентацию.',
        'IN_PROGRESS',
        'HIGH',
        now() + interval '2 day',
        now(),
        now(),
        1,
        2,
        2
    ),
    (
        102,
        'Обновить лендинг компании',
        'Добавить новый блок с акцией и передать на публикацию.',
        'NEW',
        'MEDIUM',
        now() + interval '5 day',
        now(),
        now(),
        1,
        3,
        3
    ),
    (
        103,
        'Подготовить welcome-план для нового сотрудника',
        'Сформировать план адаптации на первую неделю.',
        'BLOCKED',
        'CRITICAL',
        now() - interval '1 day',
        now(),
        now(),
        1,
        4,
        4
    )
    on conflict (id) do nothing;

insert into comments (id, task_id, author_id, text, created_at)
values
    (1, 101, 2, 'Начала собирать данные по отделам.', now()),
    (2, 101, 1, 'Добавь сравнение с прошлым месяцем.', now()),
    (3, 103, 4, 'Жду подтверждение даты выхода сотрудника.', now())
    on conflict (id) do nothing;

insert into task_history (
    id,
    task_id,
    field,
    old_value,
    new_value,
    changed_by_user_id,
    changed_at
)
values
    (1, 101, 'status', 'NEW', 'IN_PROGRESS', 2, now()),
    (2, 103, 'status', 'IN_PROGRESS', 'BLOCKED', 4, now())
    on conflict (id) do nothing;